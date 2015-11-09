package com.jpuyo.barcelonaplaces.app.database.managers;

import android.database.Cursor;

/**
 * Class with common code to all table managers
 */
public class TableManager {

    public long getLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public String getString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
