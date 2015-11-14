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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jpuyo.barcelonaplaces.app.R;
import com.jpuyo.barcelonaplaces.app.services.placesdownload.retrofit.PlaceDetails;

import java.sql.SQLException;

/**
 * Helper Database
 */
class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "places.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                R.raw.ormlite_config);
    }

    /**
     * Definition of database model
     *
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            executeCommandsCreateTables(connectionSource);
        } catch (SQLException e) {
            logErrorAndThrowRunTimeException(e);
        }
    }

    /**
     * We use database as a cache.
     * If we change database model, we can drop tables
     * because users can't lose information
     *
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            executeCommandsDropTables(connectionSource);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            logErrorAndThrowRunTimeException(e);
        }
    }

    private void executeCommandsCreateTables(ConnectionSource connectionSource)
            throws SQLException {
        TableUtils.createTable(connectionSource, PlaceDetails.class);
    }

    private void executeCommandsDropTables(ConnectionSource connectionSource)
            throws SQLException {
        TableUtils.dropTable(connectionSource, PlaceDetails.class, true);
    }

    private void logErrorAndThrowRunTimeException(SQLException e) {
        Log.e(DatabaseHelper.class.getName(), e.getMessage(), e);
        throw new RuntimeException(e);
    }
}
