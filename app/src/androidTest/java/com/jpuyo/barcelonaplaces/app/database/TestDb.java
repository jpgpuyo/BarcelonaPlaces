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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.jpuyo.barcelonaplaces.app.database.table.TableTest;
import com.jpuyo.barcelonaplaces.app.database.table.TableTestFactory;

import java.util.ArrayList;
import java.util.HashSet;

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();
    private final HashSet<String> tableNameHashSet = new HashSet<>();

    public void setUp() {
        deleteTheDatabase();
        tableNameHashSet.add(DatabaseContract.PlaceDetailsEntry.TABLE_NAME);
    }

    private void deleteTheDatabase() {
        mContext.deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }

    public void testCreateDb() throws Throwable {
        SQLiteDatabase db = new DatabaseHelper(this.mContext).getWritableDatabase();
        checkTables(db);
        checkColumns(db);
        db.close();
    }

    private void checkTables(SQLiteDatabase db) {
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        do {
            tableNameHashSet.remove(c.getString(0));
        } while( c.moveToNext() );

        assertTrue("Error: Your database was created without the correct tables",
                tableNameHashSet.isEmpty());
    }

    private void checkColumns(SQLiteDatabase db) {

        TableTestFactory tableTestFactory = new TableTestFactory();
        ArrayList<TableTest> tableTestList = tableTestFactory.getAllTables();

        for (TableTest tableTest : tableTestList) {
            Cursor c = db.rawQuery("PRAGMA table_info(" + tableTest.getTableName() + ")", null);
            assertTrue("Error: This means that we were unable to query the database for tableTest information from tableTest " + tableTest.getTableName(),
                    c.moveToFirst());

            HashSet<String> columnsHashSet = tableTest.getTableColumnsSet();
            int columnNameIndex = c.getColumnIndex("name");
            do {
                String columnName = c.getString(columnNameIndex);
                columnsHashSet.remove(columnName);
            } while(c.moveToNext());
            assertTrue("Error: The database doesn't contain all of the required sqliteColumns for the tableTest " + tableTest.getTableName(),
                    columnsHashSet.isEmpty());
        }
    }

    public void testInsertOneRecordForEachTable() {

        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        TableTestFactory tableTestFactory = new TableTestFactory();
        ArrayList<TableTest> tableTestList = tableTestFactory.getAllTables();

        for (TableTest tableTest : tableTestList) {
            ContentValues tableValues = tableTest.createValuesForSingleInsert();
            long tableRowId = db.insert(tableTest.getTableName(), null, tableValues);
            assertTrue(tableRowId != -1);

            Cursor tableCursor = db.query(
                    tableTest.getTableName(),  // TableTest to Query
                    null, // leaving "sqliteColumns" null just returns all the sqliteColumns.
                    null, // cols for "where" clause
                    null, // values for "where" clause
                    null, // sqliteColumns to group by
                    null, // sqliteColumns to filter by row groups
                    null  // sort order
            );
            assertTrue( "Error: No Records returned from " + tableTest.getTableName() +" query", tableCursor.moveToFirst() );

            TestUtilities.validateCurrentRecord("testInsertReadDb for tableTest " + tableTest.getTableName() + " failed to validate",
                    tableCursor, tableValues);

            assertFalse("Error: More than one record returned from " + tableTest.getTableName() + " query",
                    tableCursor.moveToNext());

            tableCursor.close();
        }

        dbHelper.close();
    }
}
