package com.example.karenli.budgetingapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.karenli.budgetingapp.models.Receipt;

import static android.content.ContentValues.TAG;

/**
 * Created by karenli on 10/23/17.
 */

public class AddToDBHelperImpl extends DatabaseHelper implements IAddToDB {

    private static AddToDBHelperImpl sInstance;

    public static synchronized AddToDBHelperImpl getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new AddToDBHelperImpl(context.getApplicationContext());
        }
        return sInstance;
    }

    public AddToDBHelperImpl(Context context) {
        super(context);
    }

    @Override
    public void addReceipt(Receipt receipt) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_RECEIPTS_NAME, receipt.getMyName());
            values.put(KEY_RECEIPTS_DESCR, receipt.getMyDescription());
            values.put(KEY_RECEIPTS_MONTH, receipt.getMyMonth());
            values.put(KEY_RECEIPTS_YEAR, receipt.getMyYear());
            values.put(KEY_RECEIPTS_TOTAL, receipt.getMyTotal());
            values.put(KEY_RECEIPTS_IMGPATH, receipt.getMyImgPath());

            long receiptId = db.insertOrThrow(TABLE_RECEIPTS, null, values);
            receipt.setMyId(receiptId);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }
}
