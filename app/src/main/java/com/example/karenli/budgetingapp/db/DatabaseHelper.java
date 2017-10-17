package com.example.karenli.budgetingapp.db;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

import com.example.karenli.budgetingapp.models.Receipt;

import static android.content.ContentValues.TAG;

/**
 * Created by karenli on 10/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper implements AddToDBHelper, QueryFromDBHelper {
    // Database Info
    private static final String DATABASE_NAME = "receiptsDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_RECEIPTS = "receipts";
    private static final String TABLE_USERS = "users";

    // Post Table Columns
    private static final String KEY_RECEIPTS_ID = "id";
    private static final String KEY_RECEIPTS_NAME = "name";
    private static final String KEY_RECEIPTS_DESCR = "descr";
    private static final String KEY_RECEIPTS_MONTH = "month";
    private static final String KEY_RECEIPTS_YEAR = "year";
    private static final String KEY_RECEIPTS_TOTAL = "total";
    private static final String KEY_RECEIPTS_IMGPATH = "imgpath";

    // User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PROFILE_PICTURE_URL = "profilePictureUrl";

    private static DatabaseHelper sInstance;

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

    @Override
    public void addReceipt(Receipt receipt) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
//            long userId = addOrUpdateUser(receipt.user);

            ContentValues values = new ContentValues();
            values.put(KEY_RECEIPTS_NAME, receipt.getMyName());
            values.put(KEY_RECEIPTS_DESCR, receipt.getMyDescription());
            values.put(KEY_RECEIPTS_MONTH, receipt.getMyMonth());
            values.put(KEY_RECEIPTS_YEAR, receipt.getMyYear());
            values.put(KEY_RECEIPTS_TOTAL, receipt.getMyTotal());
            values.put(KEY_RECEIPTS_IMGPATH, receipt.getMyImgPath());


            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_RECEIPTS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public List<Receipt> getReceipts(int month, int year) {
        List<Receipt> receipts = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = %d AND %s = %d",
                        TABLE_RECEIPTS,
                        KEY_RECEIPTS_MONTH, month,
                        KEY_RECEIPTS_YEAR, year);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Receipt newReceipt = new Receipt();
                    newReceipt.setMyName(cursor.getString(cursor.getColumnIndex(KEY_RECEIPTS_NAME)));
                    newReceipt.setMyDescription(cursor.getString(cursor.getColumnIndex(KEY_RECEIPTS_DESCR)));
                    newReceipt.setMyImgPath(cursor.getString(cursor.getColumnIndex(KEY_RECEIPTS_IMGPATH)));
                    newReceipt.setMyMonth(cursor.getInt(cursor.getColumnIndex(KEY_RECEIPTS_MONTH)));
                    newReceipt.setMyYear(cursor.getInt(cursor.getColumnIndex(KEY_RECEIPTS_YEAR)));
                    newReceipt.setMyTotal(cursor.getInt(cursor.getColumnIndex(KEY_RECEIPTS_TOTAL)));
                    receipts.add(newReceipt);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return receipts;
    }
}