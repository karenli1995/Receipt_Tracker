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

public class RemoveFromDBImpl extends DatabaseHelper implements IRemoveFromDB {

    private static RemoveFromDBImpl sInstance;

    public static synchronized RemoveFromDBImpl getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new RemoveFromDBImpl(context.getApplicationContext());
        }
        return sInstance;
    }

    public RemoveFromDBImpl(Context context) {
        super(context);
    }

    @Override
    public void removeReceipt(Receipt receipt) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            String receiptMyId = Long.toString(receipt.getMyId());
            db.delete(TABLE_RECEIPTS,"ID=?", new String[]{receiptMyId});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to remove post from database");
        } finally {
            db.endTransaction();
        }
    }
}
