package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit;

import android.content.ContentValues;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jpuyo.barcelonaplaces.app.database.DatabaseContract;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Email;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Fax;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Phone;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.database.fieldFormatters.Transport;

/**
 * Class to hold information of each element of
 * http://t21services.herokuapp.com/points/{id}
 */
@DatabaseTable(tableName = "place_details")
public class PlaceDetails {

    @DatabaseField(generatedId  = true)
    private int id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String address;

    @DatabaseField
    private String transport;

    @DatabaseField
    private String email;

    @DatabaseField
    private String geocoordinates;

    @DatabaseField
    private String description;

    @DatabaseField
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeocoordinates() {
        return geocoordinates;
    }

    public void setGeocoordinates(String geocoordinates) {
        this.geocoordinates = geocoordinates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
