package com.example.andrew.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andrew on 2/16/2017.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbLab5.db";
    private static final int DATABASE_VERSION = 2;
    final static String TABLE_NAME = "messages_t";
    final static String ACTIVITY_NAME = "ChatWindow";
    static String KEY_ID = "id";
    static String KEY_MESSAGE = "message";

    public ChatDatabaseHelper(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE messages_t(" +
                KEY_ID + " INTEGER primary key, " +
                KEY_MESSAGE + " TEXT);");
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
    }
}


//Write the onCreate() function so that it creates a temporary ChatDatabaseHelper object,
// which then gets a writeable database and stores that as an instance variable.
// After opening the database,
// execute a query for any existing chat messages and
// add them into the ArrayList of messages that was created in Lab 4.
// You can either use the rawQuery() method, or query() to build the query statement yourself. Add Log.i() messages for each message that you retrieve from the Cursor object: