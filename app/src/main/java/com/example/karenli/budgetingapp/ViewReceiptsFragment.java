package com.example.karenli.budgetingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.karenli.budgetingapp.adapters.ReceiptArrayAdapter;
import com.example.karenli.budgetingapp.db.DatabaseHelper;
import com.example.karenli.budgetingapp.models.Receipt;

/**
 * Created by karenli on 10/12/17.
 */

public class ViewReceiptsFragment extends Fragment {
    private ListView myListView;
    private ReceiptArrayAdapter myReceiptArrayAdapter;
    private List<Receipt> myReceipts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewreceipts, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getContext());
//        myReceipts = databaseHelper.getReceipts(getArguments().getInt("month"), getArguments().getInt("year"));

        myListView = (ListView) view.findViewById(R.id.lvReceipts);
        myReceiptArrayAdapter = new ReceiptArrayAdapter(getContext(), new ArrayList<Receipt>());
        myListView.setAdapter(myReceiptArrayAdapter);

        setListViewListener();
    }

    private void setListViewListener() {
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Receipt receipt = (Receipt) adapterView.getItemAtPosition(i);

                //TODO: start new activity with receipt view
            }
        });
    }
}
