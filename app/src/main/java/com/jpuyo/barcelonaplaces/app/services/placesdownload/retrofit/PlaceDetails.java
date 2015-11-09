package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit;

import android.content.ContentValues;

import com.jpuyo.barcelonaplaces.app.database.DatabaseContract;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Email;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Fax;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Phone;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Transport;

/**
 * Class to hold information of each element of
 * http://t21services.herokuapp.com/points/{id}
 */
public class PlaceDetails {
    private int id;
    private String title;
    private String address;
    private String transport;
    private String email;
    private String geocoordinates;
    private String description;
    private String phone;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getTransport() {
        return transport;
    }

    public String getEmail() {
        return email;
    }

    public String getGeoCoordinates() {
        return geocoordinates;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * Return ContentValues associated to the retrofit object
     * @return
     */
    public ContentValues toContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PLACE_ID, id);
        contentValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TITLE, title);
        contentValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_ADDRESS, address);
        contentValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TRANSPORT, new Transport().format(transport));
        contentValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_GEOCOORDINATES, geocoordinates);
        contentValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_DESCRIPTION, description);
        contentValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PHONE, new Phone().format(phone));
        contentValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_FAX,  new Fax().format(phone));
        contentValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_EMAIL,  new Email().format(email));
        return contentValues;
    }
}
