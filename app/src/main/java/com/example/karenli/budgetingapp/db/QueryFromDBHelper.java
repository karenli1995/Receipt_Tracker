package com.example.karenli.budgetingapp.db;

import com.example.karenli.budgetingapp.models.Receipt;

import java.util.List;

/**
 * Created by karenli on 10/13/17.
 */

public interface QueryFromDBHelper {
    List<Receipt> getReceipts(int month, int year);
}
