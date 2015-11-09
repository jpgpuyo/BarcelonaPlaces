package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Contains all endpoints entries of the t21services api
 */
public interface T21ServicesApi {

    /**
     * Returns a list of places with their location
     * @return
     */
    @GET("/points")
    PlaceLocationsList getPlacesList();

    /**
     * Return details of a concrete place
     * @return
     */
    @GET("/points/{id}")
    PlaceDetails getPlaceDetails(@Path("id") int placeId);
}
