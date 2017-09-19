package com.example.user.m_toto.ParentFragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.m_toto.ParentMainAdapter.RecyclerChildrenClinicAdapter;
import com.example.user.m_toto.Models.ChildrenClinic;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.example.user.m_toto.ParentNavigator.MyPREF;
import static com.example.user.m_toto.ParentNavigator.loggedMotherId;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildrenListClinic extends Fragment {

    List<ChildrenClinic> GetChildren1;
    RecyclerView recyclerView;
    ImageView empty_child_list;

    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;

  //  ProgressBar progressBar;
    String JSON_FNAME = "firstName";
    String JSON_LNAME = "lastName";
    String JSON_PHONE = "dob";
    String JSON_CHILD_ID = "id";
    RequestQueue requestQueue ;


    public ChildrenListClinic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_children_list_clinic, container, false);

        empty_child_list= (ImageView) view.findViewById(R.id.empty_child_list);

        GetChildren1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChildrenClinic);

        //progressBar = (ProgressBar) view.findViewById(R.id.progressBarChildrenClinic);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        childrenClinic();
        return view;
    }

    public void childrenClinic(){

        final ProgressDialog progress = ProgressDialog.show(getActivity()," Loading Children ", "Please wait..");

        SharedPreferences editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        int mother_id=editor.getInt("mother_id",0);

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.MOTHERS_CHILDREN+"/"+mother_id,
        //final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.MOTHERS_CHILDREN+"/"+loggedMotherId,

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

    public void  json_parse_data(JSONArray array) {

        if (array.length() == 0) {
            empty_child_list.setVisibility(View.VISIBLE);
        } else {


        for (int i = 0; i < array.length(); i++) {

            ChildrenClinic GetChildren2 = new ChildrenClinic();
            //  List<String> list = new ArrayList<String>();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);


                GetChildren2.setFname(json.getString(JSON_FNAME));

                GetChildren2.setLname(json.getString(JSON_LNAME));
                GetChildren2.setDob(json.getString(JSON_PHONE));
                GetChildren2.setChild_id(json.getString(JSON_CHILD_ID));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetChildren1.add(GetChildren2);
        }
    }

        recyclerViewadapter = new RecyclerChildrenClinicAdapter(GetChildren1, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);
    }

}
