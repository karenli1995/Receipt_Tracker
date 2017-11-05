package com.example.karenli.budgetingapp;

/**
 * Created by karenli on 9/27/17.
 */

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.karenli.budgetingapp.db.AddToDBHelperImpl;
import com.example.karenli.budgetingapp.models.Receipt;

public class CreateReceiptActivity extends BaseActivity {
    private EditText etName;
    private EditText etDescr;
    private EditText etTotal;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiptinfo);

        setUpView();
    }

    private void setUpView() {
        setUpToolbar();

        etName = (EditText) findViewById(R.id.etName);
        etDescr = (EditText) findViewById(R.id.etDescript);
        etTotal = (EditText) findViewById(R.id.etTotal);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        addOnClickForSubmitBtn();
    }

    private void addOnClickForSubmitBtn() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: error checks for information entry && modify the date
                String name = etName.getText().toString();
                String descr = etDescr.getText().toString();
                double total = Double.parseDouble(etTotal.getText().toString());

                Intent intent = getIntent();
                String imgPath = intent.getStringExtra("imgPath");
                int year = intent.getIntExtra("year", 0);
                int month = intent.getIntExtra("month", 01);

                Receipt receipt = new Receipt(name, descr, imgPath, month, year, total);
                //persist receipt to the db
                AddToDBHelperImpl databaseHelper = AddToDBHelperImpl.getInstance(getApplicationContext());
                databaseHelper.addReceipt(receipt);

                //TODO: update home page with updated data

                finish();
            }
        });
    }
}
