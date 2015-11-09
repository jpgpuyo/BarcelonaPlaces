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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and columns for the places database
 */
public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "com.jpuyo.barcelonaplaces.app";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Table place_detail
     */
    public static final class PlaceDetailsEntry implements BaseColumns {

        public static final String PATH = "place_detail";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

        public static final String TABLE_NAME = "place_detail";

        public static final String COLUMN_PLACE_ID = "place_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_TRANSPORT = "transport";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_GEOCOORDINATES = "geocoordinates";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_FAX = "fax";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ( " +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_PLACE_ID + " INTEGER NOT NULL, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_TRANSPORT + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_GEOCOORDINATES + " TEXT NOT NULL," +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_FAX + " TEXT" +
                " );";

        public final static String SQL_DROP_TABLE = "DROP TABLE " + TABLE_NAME + " IF EXISTS;";

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
