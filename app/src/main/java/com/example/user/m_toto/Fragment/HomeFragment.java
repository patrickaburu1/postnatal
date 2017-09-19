package com.example.user.m_toto.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.user.m_toto.LoginActivity;
import com.example.user.m_toto.NurseNavigator;
import com.example.user.m_toto.R;

import static android.content.Context.MODE_PRIVATE;
import static com.example.user.m_toto.LoginActivity.MyPREF;


public class HomeFragment extends Fragment {
LinearLayout clinic,registerChild,records,logout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_home, container, false);

        clinic= (LinearLayout) view.findViewById(R.id.linearClinic);
        registerChild= (LinearLayout) view.findViewById(R.id.linearregisterNewBorn);
        records= (LinearLayout) view.findViewById(R.id.linearRecords);
        logout= (LinearLayout) view.findViewById(R.id.linearLogout);

        clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.nurseNavigator, new ChildrenList(),"Clinic");
                f.commit();
            }
        });

        registerChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.nurseNavigator, new ParentList(),"Clinic");
                f.commit();
            }
        });

        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.nurseNavigator, new Report(),"Clinic");
                f.commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(getActivity(),LoginActivity.class);
                startActivity(i);

            }
        });
        return view;
    }


}
