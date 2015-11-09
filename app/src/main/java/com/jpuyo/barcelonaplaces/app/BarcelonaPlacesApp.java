package com.jpuyo.barcelonaplaces.app;

import android.app.Application;

import com.jpuyo.barcelonaplaces.app.database.managers.PlaceDetailManager;
import com.jpuyo.barcelonaplaces.app.managers.ManagersFactory;
import com.jpuyo.barcelonaplaces.app.ui.errors.ErrorManager;
import com.jpuyo.barcelonaplaces.app.ui.intents.IntentsManager;

public class BarcelonaPlacesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ManagersFactory.initInstance(getApplicationContext());
    }

    public PlaceDetailManager getPlacesDetailManager() {
        return ManagersFactory.getPlaceDetailManager();
    }

    public IntentsManager getIntentsManager() {
        return ManagersFactory.getIntentsManager();
    }

    public ErrorManager getErrorManager() {
        return ManagersFactory.getErrorManager();
    }
}