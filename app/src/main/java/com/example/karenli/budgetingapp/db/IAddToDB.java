package com.example.karenli.budgetingapp.db;

import com.example.karenli.budgetingapp.models.Receipt;

/**
 * Created by karenli on 10/13/17.
 */

public interface IAddToDB {
    void addReceipt(Receipt receipt);
}
