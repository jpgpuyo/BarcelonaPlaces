package com.jpuyo.barcelonaplaces.app.services.placesdownload;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Service used for download place details from server
 */
public class PlacesDownloadService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static PlacesDownload sPlacesDownload = null;

    @Override
    public void onCreate() {
        Log.d("PlacesDownloadService", "onCreate - PlacesDownloadService");
        synchronized (sSyncAdapterLock) {
            if (sPlacesDownload == null) {
                sPlacesDownload = new PlacesDownload(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sPlacesDownload.getSyncAdapterBinder();
    }
}