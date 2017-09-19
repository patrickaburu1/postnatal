package com.example.user.m_toto.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.m_toto.Adapter.GetChildren;
import com.example.user.m_toto.Adapter.RecyclerChildrenAdapter;
import com.example.user.m_toto.Adapter.RecyclerReportAdapter;
import com.example.user.m_toto.Models.MonthlyReport;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class Report extends Fragment {
    List<MonthlyReport> GetChildren1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;
    
    RequestQueue requestQueue ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_report, container, false);

        GetChildren1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerReport);

        // progressBar = (ProgressBar) view.findViewById(R.id.progressBarChildren);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        
        report();
        
        return view;
    }

    private void report() {

        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading Report", "Please wait..");

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.MONTHLY_REPORT,

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
                        // progressBar.setVisibility(View.GONE);
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

            MonthlyReport report = new MonthlyReport();
            //  List<String> list = new ArrayList<String>();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                int n=i+1;

                String v=String.valueOf(n);
                report.setC(v);

                report.setImmune(json.getString("name"));
                report.setNumber(json.getString("number"));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetChildren1.add(report);
        }

        recyclerViewadapter = new RecyclerReportAdapter(GetChildren1, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);
    }}