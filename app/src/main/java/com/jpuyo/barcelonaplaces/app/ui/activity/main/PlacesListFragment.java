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
package com.jpuyo.barcelonaplaces.app.ui.activity.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.jpuyo.barcelonaplaces.app.BarcelonaPlacesApp;
import com.jpuyo.barcelonaplaces.app.R;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.PlacesDownload;
import com.jpuyo.barcelonaplaces.app.ui.errors.exceptions.EmailNotFound;
import com.jpuyo.barcelonaplaces.app.ui.errors.exceptions.PhoneNotFound;
import com.jpuyo.barcelonaplaces.app.ui.intents.IntentsManager;
import com.jpuyo.barcelonaplaces.app.util.Network;

import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Fragment with 2 parts:
 * -A places list
 * -A Search view where user can filter data
 */
public class PlacesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, SearchView.OnQueryTextListener, View.OnClickListener {

    public static final String LOG_TAG = PlacesListFragment.class.getSimpleName();

    private BarcelonaPlacesApp mApp;
    private PlacesCursorCardAdapter mPlacesAdapter;
    private String mCurFilter;

    private static final int PLACES_LIST_LOADER = 0;

    public PlacesListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.placeslist_fragment, container, false);

        mApp = (BarcelonaPlacesApp)getActivity().getApplicationContext();

        mPlacesAdapter = new PlacesCursorCardAdapter(getActivity(), this);
        mPlacesAdapter.setPlaceDetailManager(mApp.getPlacesDetailManager());

        CardListView mListView = (CardListView) rootView.findViewById(R.id.cardlistview_placeslist_fragment);
        mListView.setAdapter(mPlacesAdapter);

        SearchView mSearchView = (SearchView) rootView.findViewById(R.id.searchview_place);
        mSearchView.setFocusable(true);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.placeslist_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            downloadDataFromServer();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * If we have internet connection, download data from server
     * If we don't have internet connection, we notify to the user
     */
    private void downloadDataFromServer() {
        if(Network.isAvailable(getActivity())) {
            PlacesDownload.downloadFromServer(getActivity());
        }else{
            mApp.getErrorManager().showError(R.string.error_action_refresh);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(PLACES_LIST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Method called when we start a query on database.
     * If current filter is empty, we show all places
     * If current filter is not empty, we filter data
     * @param i
     * @param bundle
     * @return
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        if (mCurFilter != null) {
            return mApp.getPlacesDetailManager().getPlacesListOrderedAndFilteredByName(mCurFilter);
        }
        return mApp.getPlacesDetailManager().getPlacesListOrderedByName();
    }

    /**
     * Method called when data is loaded.
     * We swap cursor.
     * If database is empty, we notify to the user, because he needs activate internet connection
     * @param loader
     * @param cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mPlacesAdapter.swapCursor(cursor);
        notifyUserIfDatabaseIsEmpty();
    }

    /**
     * If current filter is empty, and the places list is empty, we need a internet connection
     * for download data from server.
     * If we don't have internet connection, we notify to the user
     */
    private void notifyUserIfDatabaseIsEmpty() {
        if ((mCurFilter == null)&& (mPlacesAdapter.isEmpty())&&(!Network.isAvailable(getActivity()))){
            mApp.getErrorManager().showError(R.string.error_action_refresh);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPlacesAdapter.swapCursor(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * Method called when user change filter
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
        if (mCurFilter == null && newFilter == null) {
            return true;
        }
        if (mCurFilter != null && mCurFilter.equals(newFilter)) {
            return true;
        }
        mCurFilter = newFilter;
        getLoaderManager().restartLoader(0, null, this);
        return true;
    }

    /**
     * Method to manage the user events
     * @param v
     */
    @Override
    public void onClick(View v) {

        IntentsManager intentsManager = mApp.getIntentsManager();
        Intent intent;
        String paramForIntent =  (String) v.getTag();

        switch (v.getId()) {
            case R.id.action_maps:
                try{
                    intent = intentsManager.createMapsIntent(paramForIntent);
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    mApp.getErrorManager().showError(R.string.error_action_maps_activity_not_found);
                }
                break;
            case R.id.action_phone:
                try {
                    intent = intentsManager.createPhoneIntent(paramForIntent);
                    startActivity(intent);
                }catch(PhoneNotFound e){
                    mApp.getErrorManager().showError(R.string.error_action_phone_phone_not_found);
                }catch(ActivityNotFoundException e){
                    mApp.getErrorManager().showError(R.string.error_action_phone_activity_not_found);
                }
                break;
            case R.id.action_email:
                try {
                    intent = intentsManager.createEmailIntent(paramForIntent);
                    startActivity(intent);
                }catch(EmailNotFound e){
                    mApp.getErrorManager().showError(R.string.error_action_email_email_not_found);
                }catch(ActivityNotFoundException e){
                    mApp.getErrorManager().showError(R.string.error_action_email_activity_not_found);
                }
                break;

            default:
                break;
        }
    }
}