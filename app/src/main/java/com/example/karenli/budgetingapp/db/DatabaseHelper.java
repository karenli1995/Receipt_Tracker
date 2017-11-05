package com.example.karenli.budgetingapp.db;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.provider.ContactsContract;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;

import com.example.karenli.budgetingapp.models.Receipt;

import static android.content.ContentValues.TAG;

/**
 * Created by karenli on 10/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    protected static final String DATABASE_NAME = "receiptsDatabase";
    protected static final int DATABASE_VERSION = 2;

    // Table Names
    protected static final String TABLE_RECEIPTS = "receipts";
    protected static final String TABLE_USERS = "users";

    // Post Table Columns
    protected static final String KEY_RECEIPTS_ID = "id";
    protected static final String KEY_RECEIPTS_NAME = "name";
    protected static final String KEY_RECEIPTS_DESCR = "descr";
    protected static final String KEY_RECEIPTS_MONTH = "month";
    protected static final String KEY_RECEIPTS_YEAR = "year";
    protected static final String KEY_RECEIPTS_TOTAL = "total";
    protected static final String KEY_RECEIPTS_IMGPATH = "imgpath";

    // User Table Columns
    protected static final String KEY_USER_ID = "id";
    protected static final String KEY_USER_NAME = "userName";
    protected static final String KEY_USER_PROFILE_PICTURE_URL = "profilePictureUrl";

    protected static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
                sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECEIPTS_TABLE = "CREATE TABLE " + TABLE_RECEIPTS +
                " (" +
                KEY_RECEIPTS_ID + " INTEGER PRIMARY KEY," + // Define a primary key
//                KEY_POST_USER_ID_FK + " INTEGER REFERENCES " + TABLE_USERS + "," + // Define a foreign key
                KEY_RECEIPTS_NAME + " TEXT," +
                KEY_RECEIPTS_DESCR + " TEXT," +
                KEY_RECEIPTS_MONTH + " INTEGER," +
                KEY_RECEIPTS_YEAR + " INTEGER," +
                KEY_RECEIPTS_TOTAL + " DOUBLE," +
                KEY_RECEIPTS_IMGPATH + " TEXT" +
                ")";

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                " (" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_NAME + " TEXT," +
                KEY_USER_PROFILE_PICTURE_URL + " TEXT" +
                ")";

        db.execSQL(CREATE_RECEIPTS_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIPTS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }
}