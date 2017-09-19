package com.example.user.m_toto.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

import static com.example.user.m_toto.NurseNavigator.motherIdHold;
import static com.example.user.m_toto.NurseNavigator.motherNameHold;
import static com.example.user.m_toto.NurseNavigator.motherPhoneHold;


public class RegisterNewBorn extends Fragment {
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    EditText fname,lname,weight,height;
    ImageView imagedob;
    TextView motherid,motherphone,mothername,dob;
    Button registernewborn;
    ProgressDialog pDialog;
    String dob1,fname1,lname1,weight1,height1,motherId1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_new_born, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        //setSupportActionBar(toolbar);
//        toolbar.setTitle("Register");

        dob= (TextView) view.findViewById(R.id.dob);
        String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        dob.setText(date);

        //imagedob= (ImageView) view.findViewById(R.id.dobimage);
        fname= (EditText) view.findViewById(R.id.childfname);
        lname= (EditText) view.findViewById(R.id.childlname);
        weight= (EditText) view.findViewById(R.id.childweght);
        height= (EditText) view.findViewById(R.id.childheight);

        motherid= (TextView) view.findViewById(R.id.motherchildId);
        motherphone= (TextView) view.findViewById(R.id.motherchildphone);
        mothername= (TextView) view.findViewById(R.id.motherchildname);

        registernewborn= (Button) view.findViewById(R.id.registerNewBornButton);

//       setText from Click mother
        motherphone.setText(motherPhoneHold);
        motherid.setText(motherIdHold);
        mothername.setText(motherNameHold);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        registernewborn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewBorn();
            }
        });
    }
    //date time picker
    public void showDatePickerDialog() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dob.setText(dateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    public void registerNewBorn(){
        fname1=fname.getText().toString();
        lname1=lname.getText().toString();
        dob1=dob.getText().toString();
        height1=height.getText().toString();
        weight1=weight.getText().toString();
        motherId1=motherid.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("fname", fname1);
        params.put("lname", lname1);
        params.put("dob", dob1);
        params.put("height", height1);
        params.put("weight", weight1);
        params.put("motherid", motherId1);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Registering New......................Hold On!");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();



        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.REGISTER_CHILD, params,
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
                                        .setTitleText("SUCCESSFULLY")
                                        .setContentText("REGISTERED")
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
}
