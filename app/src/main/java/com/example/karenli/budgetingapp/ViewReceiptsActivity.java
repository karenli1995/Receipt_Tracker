package com.example.karenli.budgetingapp;

import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.karenli.budgetingapp.adapters.ReceiptArrayAdapter;
import com.example.karenli.budgetingapp.models.Receipt;

import java.util.ArrayList;
import java.util.List;

public class ViewReceiptsActivity extends BaseActivity {

    private ListView myListView;
    private ReceiptArrayAdapter myReceiptArrayAdapter;
    private List<Receipt> myReceipts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipts);

        setUpView();
    }

    public void setUpView() {
        Intent intent = getIntent();
        int month = intent.getIntExtra("month", 0);
        int year = intent.getIntExtra("year", 0);

//        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
//        myReceipts = databaseHelper.getReceipts(getArguments().getInt("month"), getArguments().getInt("year"));

        myListView = (ListView) this.findViewById(R.id.lvReceipts);
        myReceiptArrayAdapter = new ReceiptArrayAdapter(this, new ArrayList<Receipt>());
        myListView.setAdapter(myReceiptArrayAdapter);

        setListViewListener();
    }

    private void setListViewListener() {
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Receipt receipt = (Receipt) adapterView.getItemAtPosition(i);
                String receiptImgPath = receipt.getMyImgPath();

                //TODO: start new fragment with receipt view
                ChosenReceiptFragment chosenReceiptFragment = new ChosenReceiptFragment();

                Bundle args = new Bundle();
                args.putString("imgPath", receiptImgPath);
                chosenReceiptFragment.setArguments(args);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.chosenReceiptsFragment, chosenReceiptFragment);
                fragmentTransaction.commit();
                    }
                });
    }
}
