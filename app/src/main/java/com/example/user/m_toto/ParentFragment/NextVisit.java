package com.example.user.m_toto.ParentFragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.user.m_toto.Network.AppController;
import com.example.user.m_toto.Network.CustomRequest;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.example.user.m_toto.NurseNavigator.childIdImmunizationHold;
import static com.example.user.m_toto.ParentNavigator.MyPREF;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextVisit extends Fragment {

    TextView nextVisit,childName;
    CalendarView calendarNextVisit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_next_visit, container, false);
        nextVisit= (TextView) view.findViewById(R.id.nextVisitClinic);
        childName= (TextView) view.findViewById(R.id.nextVisitChildName);

        calendarNextVisit= (CalendarView) view.findViewById(R.id.calendarNextVisit);

        nextVisit();
        return view;
    }

    public void nextVisit(){

        SharedPreferences editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        int mother_id=editor.getInt("mother_id",0);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.GET_NEXT_VISIT+"/"+mother_id, null,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            success = response.getInt("id");
                            if (success >=1) {
                                Log.d("Amount Successful!", response.toString());
                                String child = response.getString("firstName");
                                String nextVisitt = response.getString("next_visit");
                               // long  date = response.getLong("next_visit");

                                childName.setText(child);
                                nextVisit.setText(nextVisitt);

                              //  calendarNextVisit.setDate(date);
                                //calendarNextVisit.setDate(new SimpleDateFormat("yyyy/MM/dd").parse(nextVisitt), true, true);



                            } else {
                                //else set default current date and immunization N/A to be done later

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {

                Log.d("Response: ", response.toString());
//                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("OPPS...!!")
//                        .setContentText("PLEASE TRY AGAIN"+response)
//                        .show();
                // Toast.makeText(getActivity(), "Somthing went wrong please try again ", Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

}
