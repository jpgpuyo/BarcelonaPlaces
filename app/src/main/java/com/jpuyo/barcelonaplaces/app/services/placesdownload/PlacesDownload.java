package com.jpuyo.barcelonaplaces.app.services.placesdownload;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.jpuyo.barcelonaplaces.app.BarcelonaPlacesApp;
import com.jpuyo.barcelonaplaces.app.R;
import com.jpuyo.barcelonaplaces.app.database.managers.PlaceDetailManager;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.ApiProvider;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.PlaceDetails;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.PlaceLocation;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.PlaceLocationsList;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.T21ServicesApi;

import static com.jpuyo.barcelonaplaces.app.database.DatabaseContract.PlaceDetailsEntry;

/**
 * This class has the responsability of store on database
 * the data fetched from the online server.
 * We download data from server every 12 hours (we can configure)
 */
public class PlacesDownload extends AbstractThreadedSyncAdapter {
    private final String LOG_TAG = PlacesDownload.class.getSimpleName();
    // Interval at which to sync with the server, in seconds.
    // 60 seconds (1 minute) * 720 = 12 hours
    private static final int SYNC_INTERVAL = 60 * 720;
    private static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

    private final T21ServicesApi t21ServicesApi;

    public PlacesDownload(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        t21ServicesApi= new ApiProvider().getT21ServicesApi();
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    private static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        PlacesDownload.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        downloadFromServer(context);
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    private static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    /**
     * Helper method to download data from server immediately
     * @param context The context used to access the account service
     */
    public static void downloadFromServer(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to download data from server when database is empty
     * @param context
     */
    public static void downloadIfDatabaseIsEmpty(Context context) {

        PlaceDetailManager placesDetailManager = ((BarcelonaPlacesApp)context.getApplicationContext()).getPlacesDetailManager();

        if(placesDetailManager.tableIsEmpty()){
            downloadFromServer(context);
        }
    }

    /**
     * We remove all places from database and insert all places every time we download
     * data from server
     * @param account
     * @param extras
     * @param authority
     * @param provider
     * @param syncResult
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting download");
        removeAllPlaceDetailsFromDatabase();
        loadPlacesDetailIntoDatabase();
        Log.d(LOG_TAG, "Download finished");
    }

    private void removeAllPlaceDetailsFromDatabase() {
        getContext().getContentResolver().delete(PlaceDetailsEntry.CONTENT_URI, null, null);
    }

    private void loadPlacesDetailIntoDatabase() {

        int countInserted = 0;

        PlaceLocationsList placeLocationsList = t21ServicesApi.getPlacesList();

        for(PlaceLocation placeLocation:placeLocationsList.getList()){
            int placeId = placeLocation.getId();
            Uri recordInserted = insertPlaceDetailRecord(placeId);
            if (recordInserted != null){
                countInserted++;
            }
        }

        Log.d(LOG_TAG, "Download Place Details Complete. " + countInserted + " Inserted");
    }

    private Uri insertPlaceDetailRecord(int placeId) {
        PlaceDetails placeDetails = t21ServicesApi.getPlaceDetails(placeId);
        ContentValues contentValues = placeDetails.toContentValues();
        return getContext().getContentResolver().insert(PlaceDetailsEntry.CONTENT_URI, contentValues);
    }
}