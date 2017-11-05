package com.example.karenli.budgetingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karenli.budgetingapp.R;
import com.squareup.picasso.Picasso;

import com.example.karenli.budgetingapp.models.Receipt;

import java.util.List;

/**
 * Created by karenli on 10/13/17.
 */

public class ReceiptArrayAdapter extends ArrayAdapter<Receipt> {
    public ReceiptArrayAdapter(Context context, List<Receipt> receipts){
        super(context, android.R.layout.simple_list_item_1, receipts);
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        Receipt receipt = getItem(pos);

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_receipt, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivReceipt);
        ivImage.setImageResource(0);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvDescr = (TextView) convertView.findViewById(R.id.tvDescr);
        TextView tvTotal = (TextView) convertView.findViewById(R.id.tvTotal);

        tvName.setText(receipt.getMyName());
        tvDescr.setText(receipt.getMyDescription());
        tvTotal.setText(Double.toString(receipt.getMyTotal()));

//        Picasso.with(getContext()).load(receipt.getMyImgPath()).into(ivImage);

        return convertView;
    }
}
