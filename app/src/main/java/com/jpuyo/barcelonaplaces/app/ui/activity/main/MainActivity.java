package com.jpuyo.barcelonaplaces.app.ui.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jpuyo.barcelonaplaces.app.R;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.PlacesDownload;

public class MainActivity extends AppCompatActivity {

    /**
     * Create the content view and download data from server if database is empty
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        PlacesDownload.initializeSyncAdapter(this);
        PlacesDownload.downloadIfDatabaseIsEmpty(this);
    }
}
