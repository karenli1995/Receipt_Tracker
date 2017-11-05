package com.example.karenli.budgetingapp;

import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.karenli.budgetingapp.adapters.ReceiptArrayAdapter;
import com.example.karenli.budgetingapp.db.QueryFromDBImpl;
import com.example.karenli.budgetingapp.models.Receipt;

import java.util.ArrayList;
import java.util.List;

public class ViewReceiptsActivity extends BaseActivity {

    private ListView myListView;
    private ImageButton myDeleteBtn;

    private ReceiptArrayAdapter myReceiptArrayAdapter;
    private List<Receipt> myReceipts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipts);

        setUpView();
    }

    public void setUpView() {
        setUpToolbar();

        Intent intent = getIntent();
        int month = intent.getIntExtra("month", 0);
        int year = intent.getIntExtra("year", 0);

        QueryFromDBImpl databaseHelper = QueryFromDBImpl.getInstance(this);
        myReceipts.clear();
        myReceipts.addAll(databaseHelper.getReceipts(month, year));

        myListView = (ListView) this.findViewById(R.id.lvReceipts);
        myReceiptArrayAdapter = new ReceiptArrayAdapter(this, myReceipts);
        myListView.setAdapter(myReceiptArrayAdapter);
        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        myDeleteBtn = (ImageButton) findViewById(R.id.imgBtn_delete);
        setDeleteBtnListener();

        setListViewListener();
    }

    private void setDeleteBtnListener() {
        myDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedItemPositions = myListView.getCheckedItemPositions();
                int itemCount = myListView.getCount();
                for(int i=itemCount-1; i>=0; i--){
                    if(checkedItemPositions.get(i)){
                        myReceiptArrayAdapter.remove(myReceipts.get(i));
                    }
                }
                checkedItemPositions.clear();
                myReceiptArrayAdapter.notifyDataSetChanged();
            }
        } );
    }

    private void setListViewListener() {
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Receipt receipt = (Receipt) adapterView.getItemAtPosition(i);
                String receiptImgPath = receipt.getMyImgPath();

                //TODO: start new fragment with receipt view
                Intent intent = new Intent(getApplicationContext(), ChosenReceiptActivity.class);
                intent.putExtra("imgPath", receiptImgPath);
                startActivity(intent);

//                ChosenReceiptFragment chosenReceiptFragment = new ChosenReceiptFragment();
//
//                Bundle args = new Bundle();
//                args.putString("imgPath", receiptImgPath);
//                chosenReceiptFragment.setArguments(args);
//
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.chosenReceiptsFragment, chosenReceiptFragment);
//                fragmentTransaction.commit();
                    }
                });
    }

}
