package com.jpuyo.barcelonaplaces.app.services.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * The service which allows the PlacesDownload framework to access the authenticator.
 */
public class AuthenticatorService extends Service {

    private Authenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
