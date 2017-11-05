package com.example.karenli.budgetingapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.karenli.budgetingapp.models.Receipt;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by karenli on 10/23/17.
 */

public class QueryFromDBImpl extends DatabaseHelper implements IQueryFromDB {

    private static QueryFromDBImpl sInstance;

    public static synchronized QueryFromDBImpl getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new QueryFromDBImpl(context.getApplicationContext());
        }
        return sInstance;
    }

    public QueryFromDBImpl(Context context) {
        super(context);
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
