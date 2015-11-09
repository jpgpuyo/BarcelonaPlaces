package com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit;

import retrofit.RestAdapter;

/**
 * Class used to manage the creation of RestAdapters
 * from Retrofit
 */
public class ApiProvider {

    public ApiProvider(){}

    public T21ServicesApi getT21ServicesApi(){
        return new RestAdapter.Builder()
                .setEndpoint("http://t21services.herokuapp.com")
                .build()
                .create(T21ServicesApi.class);
    }
}
