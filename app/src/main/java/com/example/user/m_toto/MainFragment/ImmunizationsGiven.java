package com.example.user.m_toto.MainFragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.m_toto.Models.ImmunizationGivenModel;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.ParentMainAdapter.RecyclerImmunizationsGiven;
import com.example.user.m_toto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.user.m_toto.ParentNavigator.childClinicIdHold;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImmunizationsGiven extends Fragment {

    List<ImmunizationGivenModel> getImmunizationGiven1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;
    
    ProgressBar progressBar;
    
    RequestQueue requestQueue ;
    
    public ImmunizationsGiven() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_immunizations_given, container, false);

        getImmunizationGiven1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewImmunizationGiven);

        //progressBar = (ProgressBar) view.findViewById(R.id.progressBarChildrenClinic);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        
        immunizationsGiven();
        return view;
    }
    
    public void immunizationsGiven(){
        final ProgressDialog progress = ProgressDialog.show(getActivity()," Loading Immunizations", "Please wait..");
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.GET_LIST_OF_IMMUNIZATION+"/"+childClinicIdHold,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                       // progressBar.setVisibility(View.GONE);
                        progress.dismiss();
                        json_parse_data(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressBar.setVisibility(View.GONE);
                        progress.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...!")
                                .setContentText("PLEASE CHECK YOUR NETWORK CONNECTION")
                                .show();
                        // Toast.makeText(getActivity(), ""+error, Toast.LENGTH_SHORT).show();

                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);
    }

    public void  json_parse_data(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            ImmunizationGivenModel GetImmunizationGiven2 = new ImmunizationGivenModel();
            //  List<String> list = new ArrayList<String>();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);


                GetImmunizationGiven2.setDate_given(json.getString("given_at"));
                GetImmunizationGiven2.setImmunization_name(json.getString("name"));
                GetImmunizationGiven2.setAge(json.getString("age"));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            getImmunizationGiven1.add(GetImmunizationGiven2);
        }

        recyclerViewadapter = new RecyclerImmunizationsGiven(getImmunizationGiven1, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);
    }

}
