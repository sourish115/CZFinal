package com.sendbird.android.sample.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sendbird.android.sample.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton call1,call2,call3;
    EditText num1, num2, num3;
    String numcall;



    private OnFragmentInteractionListener mListener;

    public Sos() {
        // Required empty public constructor
    }


    public static Sos newInstance(String param1, String param2) {
        Sos fragment = new Sos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_sos, container, false);
        call1=rootView.findViewById(R.id.call1);
        call2=rootView.findViewById(R.id.call2);
        call3=rootView.findViewById(R.id.call3);
        num1 =rootView.findViewById(R.id.num1);
        num2 =rootView.findViewById(R.id.num2);
        num3 =rootView.findViewById(R.id.num3);

        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_CALL);
                numcall= num1.getText().toString();
                //numcall = name1.getText().toString();
                if(numcall.equals(null)){
                   // i.setData(Uri.parse("tel:998638834")); // default dial number
                    Toast.makeText(getContext(),"Please enter a number",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),numcall.toString(),Toast.LENGTH_SHORT).show();
                    i.setData(Uri.parse("tel:"+numcall));
                    startActivity(i);
                }
              //  startActivity(i);
            }
        });
        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
