package com.example.karenli.budgetingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by karenli on 10/18/17.
 */

public class ChosenReceiptActivity extends BaseActivity {

    private ImageView myReceiptImage;
    private String myImgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosenreceipt);

        setUpView();
    }

    private void setUpView() {
        setUpToolbar();

        myReceiptImage = (ImageView) findViewById(R.id.ivFullReceipt);

        Intent intent = getIntent();
        myImgPath = intent.getStringExtra("imgPath");
        Picasso.with(this).load(myImgPath).into(myReceiptImage);
    }
}
