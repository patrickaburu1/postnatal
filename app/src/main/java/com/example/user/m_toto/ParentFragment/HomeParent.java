package com.example.user.m_toto.ParentFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.user.m_toto.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeParent extends Fragment {

LinearLayout nextvisit, clinic,nutrition,forum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home_parent, container, false);
         nextvisit= (LinearLayout) view.findViewById(R.id.linearNextVisit);
         clinic= (LinearLayout) view.findViewById(R.id.linearPClinic);
         nutrition= (LinearLayout) view.findViewById(R.id.linearNutrion);
         forum= (LinearLayout) view.findViewById(R.id.linearForum);

        nextvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.parent_navigator, new NextVisit(), getString(R.string.app_name));
                f.commit();
            }
        });

        clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.parent_navigator, new ChildrenListClinic(), getString(R.string.app_name));
                f.commit();
            }
        });

        nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.parent_navigator, new RecommendedFeeding(), getString(R.string.app_name));
                f.commit();
            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                f.replace(R.id.parent_navigator, new Forum(), getString(R.string.app_name));
                f.commit();
            }
        });

        return view;
    }

}
