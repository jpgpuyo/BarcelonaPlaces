/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jpuyo.barcelonaplaces.app.database;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.jpuyo.barcelonaplaces.app.database.table.TableTest;
import com.jpuyo.barcelonaplaces.app.database.table.TableTestFactory;

import static com.jpuyo.barcelonaplaces.app.database.DatabaseContract.CONTENT_AUTHORITY;
import static com.jpuyo.barcelonaplaces.app.database.DatabaseContract.PlaceDetailsEntry;

public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    private void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    private void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                PlaceDetailsEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                PlaceDetailsEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Player table during delete", 0, cursor.getCount());
        cursor.close();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // ToiesProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                DatabaseProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: ToiesProvider registered with authority: " + providerInfo.authority +
                    " instead of authority: " + CONTENT_AUTHORITY,
                    providerInfo.authority, CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: ToiesProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    /*
            This test doesn't touch the database.  It verifies that the ContentProvider returns
            the correct type for each type of URI that it can handle.
            Students: Uncomment this test to verify that your implementation of GetType is
            functioning correctly.
         */
    public void testGetType() {
        // content://com.example.android.sunshine.app/weather/
        String type = mContext.getContentResolver().getType(PlaceDetailsEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.example.android.sunshine.app/weather
        assertEquals("Error: the PlayerEntry CONTENT_URI should return Player.CONTENT_TYPE",
                PlaceDetailsEntry.CONTENT_TYPE, type);
    }


    /*
        This test uses the database directly to insert and then uses the ContentProvider to
        read out the data.  Uncomment this test to see if the basic weather query functionality
        given in the ContentProvider is working correctly.
     */
    public void testBasicPlacesQuery() {
        // insert our test records into the database
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        TableTestFactory tableTestFactory = new TableTestFactory();
        TableTest playerTableTest = tableTestFactory.getPlacesTable();
        ContentValues playerValues = playerTableTest.createValuesForSingleInsert();

        long playerRowId = db.insert(PlaceDetailsEntry.TABLE_NAME, null, playerValues);
        assertTrue("Unable to Insert PlayerEntry into the Database", playerRowId != -1);

        db.close();

        // Test the basic content provider query
        Cursor playerCursor = mContext.getContentResolver().query(
                PlaceDetailsEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        // Make sure we get the correct cursor out of the database
        TestUtilities.validateCursor("testBasicPlayerQuery", playerCursor, playerValues);
    }

    public void testInsertReadProvider() {

        TableTestFactory tableTestFactory = new TableTestFactory();
        TableTest playerTableTest = tableTestFactory.getPlacesTable();
        ContentValues playerValues = playerTableTest.createValuesForSingleInsert();

        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(PlaceDetailsEntry.CONTENT_URI, true, tco);

        Uri playerInsertUri = mContext.getContentResolver().insert(PlaceDetailsEntry.CONTENT_URI, playerValues);
        assertTrue(playerInsertUri != null);

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        Cursor playerCursor = mContext.getContentResolver().query(
                PlaceDetailsEntry.CONTENT_URI,  // TableTest to Query
                null, // leaving "sqliteColumns" null just returns all the sqliteColumns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null // sqliteColumns to group by
        );

        TestUtilities.validateCursor("testInsertReadProvider. Error validating PlaceDetailsEntry insert.",
                playerCursor, playerValues);
    }

    public void testDeleteRecords() {
        testInsertReadProvider();


        TestUtilities.TestContentObserver playerObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(PlaceDetailsEntry.CONTENT_URI, true, playerObserver);

        deleteAllRecordsFromProvider();

        playerObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(playerObserver);
    }

    public void testBulkInsert() {
        TableTestFactory tableTestFactory = new TableTestFactory();
        TableTest playerTableTest = tableTestFactory.getPlacesTable();
        ContentValues[] bulkInsertContentValues = playerTableTest.createValuesForBulkInsert();

        TestUtilities.TestContentObserver weatherObserver = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(PlaceDetailsEntry.CONTENT_URI, true, weatherObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(PlaceDetailsEntry.CONTENT_URI, bulkInsertContentValues);

        weatherObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(weatherObserver);

        assertEquals(insertCount, playerTableTest.getBulkInsertRecordsToInsert());

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                PlaceDetailsEntry.CONTENT_URI,
                null, // leaving "sqliteColumns" null just returns all the sqliteColumns.
                null, // cols for "where" clause
                null, // values for "where" clause
                PlaceDetailsEntry.COLUMN_PLACE_ID + " ASC"  // sort order == by DATE ASCENDING
        );

        // we should have as many records in the database as we've inserted
        assertEquals(cursor.getCount(), playerTableTest.getBulkInsertRecordsToInsert());

        // and let's make sure they match the ones we created
        cursor.moveToFirst();
        for ( int i = 0; i < playerTableTest.getBulkInsertRecordsToInsert(); i++, cursor.moveToNext() ) {
            TestUtilities.validateCurrentRecord("testBulkInsert.  Error validating WeatherEntry " + i,
                    cursor, bulkInsertContentValues[i]);
        }
        cursor.close();
    }

    public void testGetRandomRecord(){
        testBulkInsert();
        Cursor cursor = mContext.getContentResolver().query(
                PlaceDetailsEntry.CONTENT_URI,
                null,
                null,
                null,
                "RANDOM() LIMIT 1"
        );
        int numberOfRows = cursor.getCount();
        assertTrue(numberOfRows == 1);
    }
}
