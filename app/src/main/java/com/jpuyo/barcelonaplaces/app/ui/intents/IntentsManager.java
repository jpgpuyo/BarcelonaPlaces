package com.jpuyo.barcelonaplaces.app.ui.intents;

import android.content.Intent;
import android.net.Uri;

import com.jpuyo.barcelonaplaces.app.ui.errors.exceptions.EmailNotFound;
import com.jpuyo.barcelonaplaces.app.ui.errors.exceptions.PhoneNotFound;

/**
 * Class used to manage external intents:
 * put the location on a map, make a phone call, send email, etc
 */
public class IntentsManager {

    private static IntentsManager instance;

    private IntentsManager() {
    }

    public static synchronized IntentsManager getInstance() {
        if (instance == null) {
            instance = new IntentsManager();
        }
        return instance;
    }

    /**
     * Creates a maps intent for put location on a map, and draw marker
     * @param markerTitleAndCoordinates Has the following format:<markerTitle>;<lat>,<long>;
     * @return
     */
    public Intent createMapsIntent(String markerTitleAndCoordinates) {
        String[] infoForGoogleMaps = markerTitleAndCoordinates.split(";");
        String title = infoForGoogleMaps[0];
        String coordinates = infoForGoogleMaps[1];
        Uri geoLocation = Uri.parse("geo:"+coordinates+"?q="+coordinates+"("+title+")");
        Intent mapsIntent = new Intent(Intent.ACTION_VIEW);
        mapsIntent.setData(geoLocation);
        return mapsIntent;
    }

    public Intent createEmailIntent(String email) throws EmailNotFound{

        if(email == null){
            throw new EmailNotFound();
        }

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        return emailIntent;
    }

    public Intent createPhoneIntent(String phoneNumber) throws PhoneNotFound{

        if(phoneNumber == null){
            throw new PhoneNotFound();
        }

        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:"+phoneNumber));
        return phoneIntent;
    }
}
