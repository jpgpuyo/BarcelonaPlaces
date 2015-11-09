package com.jpuyo.barcelonaplaces.app.database.table;

import android.content.ContentValues;

import java.util.HashSet;

public interface TableTest {
    String getTableName();
    HashSet<String> getTableColumnsSet();
    ContentValues createValuesForSingleInsert();
    ContentValues[] createValuesForBulkInsert();
    int getBulkInsertRecordsToInsert();
}

