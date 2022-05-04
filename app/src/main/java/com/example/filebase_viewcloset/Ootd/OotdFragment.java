package com.example.filebase_viewcloset.Ootd;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.filebase_viewcloset.R;


public class OotdFragment extends Fragment {

    Button btn_ootd_codi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_ootd, container, false);

       btn_ootd_codi = view.findViewById(R.id.btn_ootd_codi);

       btn_ootd_codi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent = new Intent(getActivity(),stylistActivity.class);
               startActivity(intent);

           }
       });


        return view;
    }
}