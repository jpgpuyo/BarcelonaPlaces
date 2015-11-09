package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit;

/**
 * Class to hold information of each element of
 * http://t21services.herokuapp.com/points
 */
public class PlaceLocation {
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
