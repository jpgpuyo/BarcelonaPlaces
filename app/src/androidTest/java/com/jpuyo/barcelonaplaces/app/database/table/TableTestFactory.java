package com.jpuyo.barcelonaplaces.app.database.table;

import com.jpuyo.barcelonaplaces.app.database.DatabaseContract;

import java.util.ArrayList;
import java.util.HashMap;

public class TableTestFactory {

    private final HashMap<String, TableTest> tables;

    public TableTestFactory(){
        tables = new HashMap<>();
        tables.put(DatabaseContract.PlaceDetailsEntry.TABLE_NAME, new PlaceDetailTableTest());
    }

    public ArrayList<TableTest> getAllTables() {

        ArrayList<TableTest> tableTestList = new ArrayList<>();

        for (TableTest tableTest : tables.values()) {
            tableTestList.add(tableTest);
        }

        return tableTestList;
    }

    public TableTest getPlacesTable() {
        return tables.get(DatabaseContract.PlaceDetailsEntry.TABLE_NAME);
    }
}
