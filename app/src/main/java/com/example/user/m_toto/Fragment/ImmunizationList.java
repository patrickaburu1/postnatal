package com.example.user.m_toto.Fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.m_toto.Adapter.GetImmunization;
import com.example.user.m_toto.Adapter.RecyclerImmunizationAdapter;
import com.example.user.m_toto.Network.AppController;
import com.example.user.m_toto.Network.CustomRequest;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.user.m_toto.NurseNavigator.childDobImmunizationHold;
import static com.example.user.m_toto.NurseNavigator.childFnameImmunizationHold;
import static com.example.user.m_toto.NurseNavigator.childIdImmunizationHold;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImmunizationList extends Fragment {
    TextView dueChildNameImmunization,dueChildAgeImmunization,dueChildNextVisitImmunization,lastImmunizationGiven;



    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

  //  int daysAge;

    List<GetImmunization> GetImmunization1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;
    //ProgressBar progressBar;
    String JSON_NAME = "name";
    String JSON_AGE = "age";
    String JSON_ID= "id";


    RequestQueue requestQueue ;


    public ImmunizationList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_immunization_list, container, false);

        dueChildNameImmunization= (TextView) view.findViewById(R.id.dueChildNameImmunization);
        dueChildAgeImmunization= (TextView) view.findViewById(R.id.dueChildAgeImmunization);
        dueChildNextVisitImmunization= (TextView) view.findViewById(R.id.dueChildNextVisitImmunization);
        lastImmunizationGiven= (TextView) view.findViewById(R.id.lastImmunizationGiven);

        dueChildNameImmunization.setText(childFnameImmunizationHold);
        dueChildAgeImmunization.setText(childDobImmunizationHold);

        GetImmunization1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewImmunization);

       // progressBar = (ProgressBar) view.findViewById(R.id.progressBarImmunization);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        //get last immunization given
        lastImmunizationGiven();

        //show immunization to be given
        immunization();

        //get days difference

        try {
            getDateDifference();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "error  "+e, Toast.LENGTH_SHORT).show();
        }


        return view;
    }
    public void immunization(){
        final ProgressDialog progress = ProgressDialog.show(getActivity(),"Loading  ", "Please wait...");

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.IMMUNIZATION+"/"+childIdImmunizationHold,

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

            GetImmunization GetImmunization2 = new GetImmunization();
            //  List<String> list = new ArrayList<String>();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);


                GetImmunization2.setName(json.getString(JSON_NAME));

                GetImmunization2.setAge(json.getString(JSON_AGE));
                GetImmunization2.setId(json.getString(JSON_ID));



            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetImmunization1.add(GetImmunization2);
        }

        recyclerViewadapter = new RecyclerImmunizationAdapter(GetImmunization1, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);
    }


    //method to get immunization last given and date scheduled fo r next visit
    public void lastImmunizationGiven(){

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.GET_LAST_IMMUNIZATION+"/"+childIdImmunizationHold, null,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            success = response.getInt("id");
                            if (success >=1) {
                                Log.d("Amount Successful!", response.toString());
                                String immunizationName = response.getString("name");
                                String nextVisit = response.getString("next_visit");

                                lastImmunizationGiven.setText(immunizationName);
                                dueChildNextVisitImmunization.setText(nextVisit);


                            } else {
                                //else set default current date and immunization N/A to be done later

                                // nextVisit.setText(date);
                                dueChildNameImmunization.setText("N/A");
                                dueChildNextVisitImmunization.setText(currentDate);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {

                Log.d("Response: ", response.toString());
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("OPPS...!!")
                        .setContentText("PLEASE TRY AGAIN"+response)
                        .show();
                // Toast.makeText(getActivity(), "Somthing went wrong please try again ", Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

    //get day difference btw dob and current date
    public void getDateDifference() throws ParseException {

        SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
        String current;

       // String finalDate = dueChildAgeImmunization.getText().toString();
       // String finalDate="04/20/2017";

        Date date1 = new Date();
        Date date2;
        current=dates.format(date1);

        date1=dates.parse(current);
        date2=dates.parse(childDobImmunizationHold);

        Calendar c1 = Calendar.getInstance();

        c1.setTime(date1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

//       int daysAge =c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
       int daysAge =c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR);

        int weeksAge=daysAge/7;
        if(daysAge>=7){
            dueChildAgeImmunization.setText(weeksAge+" Weeks");
        }else {
            dueChildAgeImmunization.setText(daysAge+" Days");
        }


        //Toast.makeText(getActivity(), ""+daysAge, Toast.LENGTH_SHORT).show();
       // return c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
    }

}
