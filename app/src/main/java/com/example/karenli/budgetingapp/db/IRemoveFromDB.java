package com.example.karenli.budgetingapp.db;

import com.example.karenli.budgetingapp.models.Receipt;

/**
 * Created by karenli on 10/23/17.
 */

public interface IRemoveFromDB {
    public void removeReceipt(Receipt receipt);
}
