package com.jpuyo.barcelonaplaces.app.database.table;

import android.content.ContentValues;

import com.jpuyo.barcelonaplaces.app.database.DatabaseContract;

import java.util.HashSet;

public class PlaceDetailTableTest implements TableTest {

    @Override
    public String getTableName() {
        return DatabaseContract.PlaceDetailsEntry.TABLE_NAME;
    }

    @Override
    public HashSet<String> getTableColumnsSet() {
        final HashSet<String> columnsHashSet = new HashSet<>();
        columnsHashSet.add(DatabaseContract.PlaceDetailsEntry.COLUMN_PLACE_ID);
        columnsHashSet.add(DatabaseContract.PlaceDetailsEntry.COLUMN_TITLE);
        columnsHashSet.add(DatabaseContract.PlaceDetailsEntry.COLUMN_ADDRESS);
        columnsHashSet.add(DatabaseContract.PlaceDetailsEntry.COLUMN_TRANSPORT);
        columnsHashSet.add(DatabaseContract.PlaceDetailsEntry.COLUMN_EMAIL);
        columnsHashSet.add(DatabaseContract.PlaceDetailsEntry.COLUMN_GEOCOORDINATES);
        columnsHashSet.add(DatabaseContract.PlaceDetailsEntry.COLUMN_DESCRIPTION);
        columnsHashSet.add(DatabaseContract.PlaceDetailsEntry.COLUMN_PHONE);
        columnsHashSet.add(DatabaseContract.PlaceDetailsEntry.COLUMN_FAX);
        return columnsHashSet;
    }

    @Override
    public ContentValues createValuesForSingleInsert() {
        ContentValues playerValues = new ContentValues();
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PLACE_ID, 1);
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TITLE, "Casa Batlló");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_ADDRESS, "Paseo de Gracia, 43, 08007 Barcelona");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TRANSPORT, "Underground:Passeig de Gràcia -L3");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_EMAIL, "http://www.casabatllo.es/en/");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_GEOCOORDINATES,"41.398703,2.16877");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_DESCRIPTION,"Casa Batlló is a key feature in the architecture of modernist Barcelona. It was built by Antoni Gaudí between 1904 and 1906 having been commissioned by the textile industrialist Josep Batlló. Nowadays, the spectacular facade is an iconic landmark in the city. The 'Manzana de la Discordia', or Block of Discord, is a series of buildings in Passeig de Gràcia. It is home to a collection of works by the most renowned architects, amongst which is Casa Batlló. The house, now a museum, is open to the public, both for cultural visits and for celebrating events in its splendid modernist function rooms.");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PHONE,612123123);
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_FAX,612123123);
        return playerValues;
    }

    static private final int BULK_INSERT_RECORDS_TO_INSERT = 3;
    @Override
    public ContentValues[] createValuesForBulkInsert() {
        ContentValues[] valuesArray = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];
        ContentValues playerValues = new ContentValues();

        int i = 0;

        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PLACE_ID, 1);
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TITLE, "Casa Batlló");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_ADDRESS, "Paseo de Gracia, 43, 08007 Barcelona");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TRANSPORT, "Underground:Passeig de Gràcia -L3");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_EMAIL, "http://www.casabatllo.es/en/");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_GEOCOORDINATES,"41.398703,2.16877");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_DESCRIPTION,"Casa Batlló is a key feature in the architecture of modernist Barcelona. It was built by Antoni Gaudí between 1904 and 1906 having been commissioned by the textile industrialist Josep Batlló. Nowadays, the spectacular facade is an iconic landmark in the city. The 'Manzana de la Discordia', or Block of Discord, is a series of buildings in Passeig de Gràcia. It is home to a collection of works by the most renowned architects, amongst which is Casa Batlló. The house, now a museum, is open to the public, both for cultural visits and for celebrating events in its splendid modernist function rooms.");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PHONE,612123123);
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_FAX,612123123);
        valuesArray[i]=playerValues;
        i++;

        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PLACE_ID, 2);
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TITLE, "Fundació Antoni Tàpies");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_ADDRESS, "C/ ARAGÓ, 255, 08007 Barcelona");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TRANSPORT, "Underground:Passeig de Gràcia -L3");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_EMAIL, "http://www.fundaciotapies.org/");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_GEOCOORDINATES,"41.398703,2.16877");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_DESCRIPTION,"The Fundació opened its doors in June 1990 in the building of the former Editorial Montaner i Simon publishing house, the work of the Modernist architect Lluís Domènech i MontanerRestored and refurbished by the architects Roser Amadó and Lluís Domènech Girbau. Constructed between 1880 and 1885, at an early stage of the evolution of Catalan Modernism, the building was the first in the Eixample district to integrate industrial typology and technology, combining exposed brick and iron, into the fabric of the city centre.");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PHONE,612456789);
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_FAX,612123123);

        valuesArray[i]=playerValues;
        i++;

        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PLACE_ID, 1);
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TITLE, "Hospital de Sant Pau");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_ADDRESS, "C/ SANT ANTONI MARIA CLARET, 167, 08025 Barcelona");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_TRANSPORT, "Underground: Sant Pau / Dos de Maig - L5");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_EMAIL, "http://www.santpau.es");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_GEOCOORDINATES,"41.398703,2.16877");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_DESCRIPTION,"Hospital de la Santa Creu i Sant provides health services to its catchment area and serves as a referral facility for services of excellence in Catalonia. It is competitive and dynamic in its areas of activity: healthcare, teaching and research. The hospital is open to society and its healthcare environment and is client focused.");
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_PHONE,631112233);
        playerValues.put(DatabaseContract.PlaceDetailsEntry.COLUMN_FAX,612123123);
        valuesArray[i]=playerValues;

        return valuesArray;
    }

    @Override
    public int getBulkInsertRecordsToInsert() {
        return BULK_INSERT_RECORDS_TO_INSERT;
    }
}
