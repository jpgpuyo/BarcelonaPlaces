package com.jpuyo.barcelonaplaces.app.database.managers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import static com.jpuyo.barcelonaplaces.app.database.DatabaseContract.PlaceDetailsEntry;

/**
 * Manager to access the class place_detail
 */
public class PlaceDetailManager extends TableManager {

    private static Context context;

    private static final String[] PLACES_LIST_COLUMNS = {
            PlaceDetailsEntry._ID,
            PlaceDetailsEntry.COLUMN_PLACE_ID,
            PlaceDetailsEntry.COLUMN_TITLE,
            PlaceDetailsEntry.COLUMN_ADDRESS,
            PlaceDetailsEntry.COLUMN_TRANSPORT,
            PlaceDetailsEntry.COLUMN_EMAIL,
            PlaceDetailsEntry.COLUMN_DESCRIPTION,
            PlaceDetailsEntry.COLUMN_PHONE,
            PlaceDetailsEntry.COLUMN_FAX,
            PlaceDetailsEntry.COLUMN_GEOCOORDINATES
    };

    public PlaceDetailManager(Context context) {
        PlaceDetailManager.context = context;
    }

    public Loader<Cursor> getPlacesListOrderedByName(){
        return new CursorLoader(context,
                PlaceDetailsEntry.CONTENT_URI,
                PLACES_LIST_COLUMNS,
                null,
                null,
                PlaceDetailsEntry.COLUMN_TITLE + " ASC");
    }

    public Loader<Cursor> getPlacesListOrderedAndFilteredByName(String placeName){
        return new CursorLoader(context,
                PlaceDetailsEntry.CONTENT_URI,
                PLACES_LIST_COLUMNS,
                PlaceDetailsEntry.COLUMN_TITLE + " LIKE ?",
                new String[]{"%" + placeName + "%"},
                PlaceDetailsEntry.COLUMN_TITLE + " ASC");
    }

    public boolean tableIsEmpty() {

        boolean tableEmpty = true;
        String [] COUNT = {"count(*)"};

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(PlaceDetailsEntry.CONTENT_URI, COUNT,null,null,null);

        if (cursor != null){
            tableEmpty = cursor.moveToFirst() && cursor.getInt(0) == 0;
            cursor.close();
        }
        return tableEmpty;
    }

    public long getPlaceRowId(Cursor cursor){
        return getLong(cursor, PlaceDetailsEntry._ID);
    }

    public String getPlaceTitle(Cursor cursor){
        return getString(cursor, PlaceDetailsEntry.COLUMN_TITLE);
    }

    public String getPlaceAddress(Cursor cursor){
        return getString(cursor, PlaceDetailsEntry.COLUMN_ADDRESS);
    }

    public String getPlaceTransport(Cursor cursor){
        return getString(cursor, PlaceDetailsEntry.COLUMN_TRANSPORT);
    }

    public String getPlaceEmail(Cursor cursor){
        return getString(cursor, PlaceDetailsEntry.COLUMN_EMAIL);
    }

    public String getPlacePhone(Cursor cursor) {
        return getString(cursor, PlaceDetailsEntry.COLUMN_PHONE);
    }

    public String getPlaceFax(Cursor cursor) {
        return getString(cursor, PlaceDetailsEntry.COLUMN_FAX);
    }

    public String getPlaceDescription(Cursor cursor) {
        return getString(cursor, PlaceDetailsEntry.COLUMN_DESCRIPTION);
    }

    public String getGeoCoordinates(Cursor cursor) {
        return getString(cursor, PlaceDetailsEntry.COLUMN_GEOCOORDINATES);
    }
}
