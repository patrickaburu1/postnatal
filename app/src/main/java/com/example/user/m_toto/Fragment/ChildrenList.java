package com.example.user.m_toto.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.m_toto.Adapter.GetChildren;
import com.example.user.m_toto.Adapter.GetParentAdapter;
import com.example.user.m_toto.Adapter.RecyclerChildrenAdapter;
import com.example.user.m_toto.Adapter.RecyclerParentAdapter;
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
public class ChildrenList extends Fragment {

    List<GetChildren> GetChildren1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;
   // ProgressBar progressBar;
    String JSON_FNAME = "firstName";
    String JSON_LNAME = "lastName";
    String JSON_DOB = "dob";
    String JSON_CHILD_ID = "id";


    RequestQueue requestQueue ;

    public ChildrenList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_children_list, container, false);
        GetChildren1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChildren);

       // progressBar = (ProgressBar) view.findViewById(R.id.progressBarChildren);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        children();
        return view;
    }
    public void  children(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading Children", "Please wait..");

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.CHILDREN,

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

            GetChildren GetChildren2 = new GetChildren();
            //  List<String> list = new ArrayList<String>();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);


                GetChildren2.setFname(json.getString(JSON_FNAME));
                GetChildren2.setLname(json.getString(JSON_LNAME));
                GetChildren2.setDob(json.getString(JSON_DOB));
                GetChildren2.setChildId(json.getString(JSON_CHILD_ID));



            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetChildren1.add(GetChildren2);
        }

        recyclerViewadapter = new RecyclerChildrenAdapter(GetChildren1, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);
    }

}
