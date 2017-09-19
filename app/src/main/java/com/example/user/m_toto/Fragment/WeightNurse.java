package com.example.user.m_toto.Fragment;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.m_toto.Network.AppController;
import com.example.user.m_toto.Network.CustomRequest;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.user.m_toto.NurseNavigator.childFnameImmunizationHold;
import static com.example.user.m_toto.NurseNavigator.childIdImmunizationHold;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightNurse extends Fragment {

    EditText inputWeight;
    Button recordWeight;

    ProgressDialog pDialog;

    RequestQueue requestQueue ;
    GraphView graphWeight;
    private LineGraphSeries<DataPoint> series;
    public WeightNurse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_weight_nurse, container, false);

        TextView weightChildName= (TextView) view.findViewById(R.id.weightChildName);

        weightChildName.setText(childFnameImmunizationHold);

        inputWeight= (EditText) view.findViewById(R.id.inputWeight);
        recordWeight= (Button) view.findViewById(R.id.recordWeight);

        //record weight button
        recordWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordWeight();
            }
        });

        graphWeight = (GraphView) view.findViewById(R.id.weightNurseGraph);
        series = new LineGraphSeries<>();

        //plot line for over weight
        LineGraphSeries<DataPoint> seriesObess = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 5),
                new DataPoint(1, 6),
                new DataPoint(2, 7),
                new DataPoint(3, 8),
                new DataPoint(4, 8.7),
                new DataPoint(5, 9.4),
                new DataPoint(6, 9.9),
                new DataPoint(7, 10.4),
                new DataPoint(8, 10.8),
                new DataPoint(9, 11.2),
                new DataPoint(10, 11.4),
                new DataPoint(11, 11.7),
                new DataPoint(12, 12)

        });
        seriesObess.setTitle("obess");
        seriesObess.setColor(Color.RED);
        //custom paint doted
        Paint paintObess = new Paint();
        paintObess.setStyle(Paint.Style.STROKE);
        paintObess.setStrokeWidth(5);
        paintObess.setColor(Color.RED);
        paintObess.setPathEffect(new DashPathEffect(new float[]{8, 10}, 0));
        seriesObess.setCustomPaint(paintObess);
        graphWeight.addSeries(seriesObess);

        // plot graphWeight for under height
        LineGraphSeries<DataPoint> seriesUnder = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 2.5),
                new DataPoint(1, 3.5),
                new DataPoint(2, 4.3),
                new DataPoint(3, 5),
                new DataPoint(4, 5.6),
                new DataPoint(5, 5.9),
                new DataPoint(6, 6.3),
                new DataPoint(7, 6.6),
                new DataPoint(8, 6.9),
                new DataPoint(9, 7.2),
                new DataPoint(10, 7.4),
                new DataPoint(11, 7.6),
                new DataPoint(12, 7.8)

        });
        seriesUnder.setTitle("under weight");
        seriesUnder.setColor(Color.RED);

        //custom paint doted
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 10}, 0));
        seriesUnder.setCustomPaint(paint);
        seriesUnder.setBackgroundColor(getResources().getColor(R.color.graph));
        seriesUnder.setDrawBackground(true);
        graphWeight.addSeries(seriesUnder);

        plotWeight();

        return view;
    }
    public void plotWeight(){

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.WEIGHT+"/"+childIdImmunizationHold,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //progressBar.setVisibility(View.GONE);
                       // progressDialog.dismiss();
                        json_parse_data(response);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...!")
                                .setContentText("PLEASE CHECK YOUR NETWORK CONNECTION")
                                .show();
                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);
    }

    public void  json_parse_data(JSONArray array){

        for(int i = 0; i<array.length(); i++) {


            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                int x=json.getInt("id");
                double y=json.getDouble("weight");

                series.appendData(new DataPoint(i, y), true, 10);
                graphWeight.addSeries(series);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

    }

    //method to submit weight of a child
    public void recordWeight(){

        String weight=inputWeight.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("weight", weight);
        params.put("child_id", childIdImmunizationHold);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading......................Hold On!");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();



        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.RECORD_WEIGHT, params,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            success = response.getInt("id");
                            if (success >=1) {
                                String id = response.getString("id");
                                // amount.setText(amountt);
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("SUCCESSFULLY")
                                        .setContentText("RECORDED")
                                        .show();
                                // Toast.makeText(getActivity(), amountt, Toast.LENGTH_SHORT).show();
                                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                                f.replace(R.id.nurseNavigator, new WeightNurse(), getString(R.string.app_name));
                                f.commit();
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

