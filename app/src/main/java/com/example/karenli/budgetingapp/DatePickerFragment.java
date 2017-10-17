package com.example.karenli.budgetingapp;

import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.icu.util.Calendar;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import java.util.List;

import com.example.karenli.budgetingapp.db.DatabaseHelper;
import com.example.karenli.budgetingapp.models.Receipt;

/**
 * Created by karenli on 10/12/17.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //TODO: start up new ListView fragment of all receipts
        Intent intent = new Intent(getContext(), ViewReceiptsActivity.class);
        intent.putExtra("month", month);
        intent.putExtra("year", year);
        startActivity(intent);

//        ViewReceiptsFragment viewReceiptFragment = new ViewReceiptsFragment();
//
//        Bundle args = new Bundle();
//        args.putInt("month", month);
//        args.putInt("year", year);
//        viewReceiptFragment.setArguments(args);
//
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.viewReceiptsFragment, viewReceiptFragment);
//        fragmentTransaction.commit();
    }
}
