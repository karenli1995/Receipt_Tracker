package com.example.karenli.budgetingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChosenReceiptFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChosenReceiptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChosenReceiptFragment extends Fragment {
    private ImageView myReceiptImage;
    private String myImgPath;

    private OnFragmentInteractionListener mListener;

    public ChosenReceiptFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChosenReceiptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChosenReceiptFragment newInstance(String param1, String param2) {
        return new ChosenReceiptFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chosen_receipt, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        myReceiptImage = (ImageView) view.findViewById(R.id.ivFullReceipt);

        myImgPath = getArguments().getString("imgPath");
        Picasso.with(getContext()).load(myImgPath).into(myReceiptImage);
    }







    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
