/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jpuyo.barcelonaplaces.app.database;


import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.ApiProvider;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.PlaceDetails;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.PlaceLocation;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.PlaceLocationsList;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.T21ServicesApi;

import static com.jpuyo.barcelonaplaces.app.database.DatabaseContract.PlaceDetailsEntry;

public class TestRetrofit extends AndroidTestCase {

    public static final String LOG_TAG = TestRetrofit.class.getSimpleName();
    private final ApiProvider apiProvider = new ApiProvider();

    public void testGetPlaceLocationsList(){
        T21ServicesApi t21ServicesApi = apiProvider.getT21ServicesApi();
        PlaceLocationsList placeLocationsList = t21ServicesApi.getPlacesList();
        PlaceLocation firstPlaceLocation = placeLocationsList.getList().get(0);
        assertEquals("ERROR: Error to read id", firstPlaceLocation.getId(), 1);
        assertEquals("ERROR: Error to read title", firstPlaceLocation.getTitle(),"Casa Batlló");
    }

    public void testGetPlaceDetails(){
        T21ServicesApi t21ServicesApi = apiProvider.getT21ServicesApi();
        PlaceDetails placeDetails = t21ServicesApi.getPlaceDetails(1);
        assertEquals("ERROR: Error to read id", placeDetails.getId(), 1);
        assertEquals("ERROR: Error to read title", placeDetails.getTitle(),"Casa Batlló");
        assertEquals("ERROR: Error to read address", placeDetails.getAddress(), "Paseo de Gracia, 43, 08007 Barcelona");
        assertEquals("ERROR: Error to read transport", placeDetails.getTransport(),"Underground:Passeig de Gràcia -L3");
        assertEquals("ERROR: Error to read email", placeDetails.getEmail(),"http://www.casabatllo.es/en/");
        assertEquals("ERROR: Error to read coordinates", placeDetails.getGeoCoordinates(),"41.391926,2.165208");
        assertEquals("ERROR: Error to read description", placeDetails.getDescription(),"Casa Batlló is a key feature in the architecture of modernist Barcelona. It was built by Antoni Gaudí between 1904 and 1906 having been commissioned by the textile industrialist Josep Batlló. Nowadays, the spectacular facade is an iconic landmark in the city. The \"Manzana de la Discordia\", or Block of Discord, is a series of buildings in Passeig de Gràcia. It is home to a collection of works by the most renowned architects, amongst which is Casa Batlló. The house, now a museum, is open to the public, both for cultural visits and for celebrating events in its splendid modernist function rooms.");
        assertEquals("ERROR: Error to read phone", placeDetails.getPhone(),"info@casabatllo.cat");
    }

    public void testLoadPlaceDetailsIntoDatabase(){
        T21ServicesApi t21ServicesApi = apiProvider.getT21ServicesApi();
        PlaceLocationsList placeLocationsList = t21ServicesApi.getPlacesList();

        for(PlaceLocation placeLocation:placeLocationsList.getList()){
            int placeId = placeLocation.getId();
            PlaceDetails placeDetails = t21ServicesApi.getPlaceDetails(placeId);
            ContentValues contentValues = placeDetails.toContentValues();
            getContext().getContentResolver().insert(PlaceDetailsEntry.CONTENT_URI, contentValues);

        }
    }
}
