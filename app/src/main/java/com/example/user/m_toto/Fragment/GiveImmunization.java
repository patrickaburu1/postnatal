package com.example.user.m_toto.Fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.user.m_toto.Network.AppController;
import com.example.user.m_toto.Network.CustomRequest;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.user.m_toto.NurseNavigator.ImmunizationIdHold;
import static com.example.user.m_toto.NurseNavigator.ImmunizationNameHold;
import static com.example.user.m_toto.NurseNavigator.childDobImmunizationHold;
import static com.example.user.m_toto.NurseNavigator.childFnameImmunizationHold;
import static com.example.user.m_toto.NurseNavigator.childIdImmunizationHold;


/**
 * A simple {@link Fragment} subclass.
 */
public class GiveImmunization extends Fragment {
    TextView vaccine,child,dateGiven,nextVisit,child_age;
    Button giveVaccine;
    ProgressDialog pDialog;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

    public GiveImmunization() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_give_immunization, container, false);

        vaccine= (TextView) view.findViewById(R.id.giveImmunizationName);
        child= (TextView) view.findViewById(R.id.immunizationchildName);
        dateGiven= (TextView) view.findViewById(R.id.immunizationDateGiven);
        nextVisit= (TextView) view.findViewById(R.id.immunizationNextVisit);
        child_age= (TextView) view.findViewById(R.id.immunizationchildAge);

        giveVaccine= (Button) view.findViewById(R.id.giveImmunization);

        String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
       // nextVisit.setText(date);
        dateGiven.setText(date);

        //add 28 days to current date for next visit
        Calendar cal = Calendar.getInstance();
         cal.add(Calendar.DATE, 28); // add 28 days
        Date date1=cal.getTime();
        String next = DateFormat.format("MM/dd/yyyy", date1).toString();

        nextVisit.setText(next);

        child_age.setText(childDobImmunizationHold);
        //child_age.setText("0 WEEKS");

        nextVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        giveVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveImmunization();

            }
        });

        vaccine.setText(ImmunizationNameHold);
        child.setText(childFnameImmunizationHold);


        return view;
    }

    public void giveImmunization(){

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading......................Hold On!");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

         String nextVisit1=nextVisit.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("child_id", childIdImmunizationHold);
        params.put("immunization_id",ImmunizationIdHold );
        params.put("nextvisit",nextVisit1 );



        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.GIVE_IMMUNIZATION, params,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            success = response.getInt("id");
                            if (success >=1) {
                                Log.d("Amount Successful!", response.toString());
                                String id = response.getString("id");
                                // amount.setText(amountt);
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("DONE")
                                        .setContentText("SUCCESSFULLY")
                                        .show();
                                // Toast.makeText(getActivity(), amountt, Toast.LENGTH_SHORT).show();
                                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                                f.replace(R.id.nurseNavigator, new ClinicCategory(), getString(R.string.app_name));
                                f.commit();
                            } else {
                                pDialog.dismiss();

                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("OPPS...!!")
                                        .setContentText("PLEASE TRY AGAIN"+response)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                pDialog.dismiss();
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
    public void showDatePickerDialog() {
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
               nextVisit.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
}
