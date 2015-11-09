package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit;

import java.util.List;

/**
 * Class to hold "list" object of
 * http://t21services.herokuapp.com/points
 */
public class PlaceLocationsList {
    private List<PlaceLocation> list;

    @Override
    public String toString() {
        return "PlaceLocationsList{" +
                "list=" + list +
                '}';
    }

    public List<PlaceLocation> getList() {
        return list;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
