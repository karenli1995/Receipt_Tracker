package com.example.karenli.budgetingapp;

/**
 * Created by karenli on 9/27/17.
 */

import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import models.Receipt;

public class ReceiptFragment extends Fragment {
    private EditText etName;
    private EditText etDescr;
    private EditText etTotal;
    private Button btnSubmit;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_receiptinfo, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        etName = (EditText) view.findViewById(R.id.etName);
        etDescr = (EditText) view.findViewById(R.id.etDescript);
        etTotal = (EditText) view.findViewById(R.id.etTotal);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        addOnClickForSubmitBtn();
    }

    private void addOnClickForSubmitBtn() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: error checks for information entry && modify the date
                String name = etName.getText().toString();
                String descr = etDescr.getText().toString();
                long total = Long.parseLong(etTotal.getText().toString());

                String imgPath = getArguments().getString("imgPath");
                DateFormat date = new DateFormat();

                Receipt receipt = new Receipt(name, descr, imgPath, date, total);
                //TODO: persist receipt to the db
                //TODO: update home page with updated data

                getActivity().finish();
            }
        });
    }
}
