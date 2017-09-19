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
public class HeightNurse extends Fragment {

    EditText inputHeight;
    Button recordHeight;
    RequestQueue requestQueue;
    GraphView graphHeight;
    private LineGraphSeries<DataPoint> series,seriesObess,seriesUnder;
    ProgressDialog pDialog;

    public HeightNurse() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_height_nurse, container, false);

        TextView  heightChildName= (TextView) view.findViewById(R.id.heightChildName);

               heightChildName.setText(childFnameImmunizationHold);

        inputHeight= (EditText) view.findViewById(R.id.inputHeight);
        recordHeight= (Button) view.findViewById(R.id.recordHeight);

        recordHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordHeight();
            }
        });

        graphHeight = (GraphView) view.findViewById(R.id.heightNurseGraph);

//        graphHeight.getViewport().setScalable(true);
//
//// activate horizontal scrolling
//        graphHeight.getViewport().setScrollable(true);
//
//        graphHeight.getViewport().setXAxisBoundsManual(true);
//        graphHeight.getViewport().setMinX(0);
//        graphHeight.getViewport().setMaxX(12);
//
//// set manual Y bounds
//        graphHeight.getViewport().setYAxisBoundsManual(true);
//        graphHeight.getViewport().setMinY(40);
//        graphHeight.getViewport().setMaxY(80);

// activate horizontal and vertical zooming and scrolling
//        graphHeight.getViewport().setScalableY(true);
//
//// activate vertical scrolling
//        graphHeight.getViewport().setScrollableY(true);

        series = new LineGraphSeries<>();

        //plot line for over height
        LineGraphSeries<DataPoint> seriesObess = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 54),
                new DataPoint(1, 59),
                new DataPoint(2, 62),
                new DataPoint(3, 65),
                new DataPoint(4, 68),
                new DataPoint(5, 70),
                new DataPoint(6, 72),
                new DataPoint(7, 73),
                new DataPoint(8, 75),
                new DataPoint(9, 76),
                new DataPoint(10, 78),
                new DataPoint(11, 79),
                new DataPoint(12, 80)

        });
        seriesObess.setTitle("obess");
        //seriesObess.setDrawDataPoints(true);
        seriesObess.setColor(Color.RED);

        //custom paint doted
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 10}, 0));
        seriesObess.setCustomPaint(paint);

        graphHeight.addSeries(seriesObess);

        // plot graphHeight for under height
        LineGraphSeries<DataPoint> seriesUnder = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 46),
                new DataPoint(1, 51),
                new DataPoint(2, 54),
                new DataPoint(3, 57),
                new DataPoint(4, 59),
                new DataPoint(5, 61),
                new DataPoint(6, 63),
                new DataPoint(7, 65),
                new DataPoint(8, 66),
                new DataPoint(9, 67),
                new DataPoint(10, 68),
                new DataPoint(11, 69),
                new DataPoint(12, 70)

        });
        seriesUnder.setTitle("under height");
        seriesUnder.setColor(Color.RED);
        //custom paint doted
        Paint paintUnder = new Paint();
        paintUnder.setStyle(Paint.Style.STROKE);
        paintUnder.setStrokeWidth(5);
        paintUnder.setColor(Color.RED);
        paintUnder.setPathEffect(new DashPathEffect(new float[]{8, 10}, 0));
        seriesUnder.setCustomPaint(paintUnder);

        //shade the lower part of line
        seriesUnder.setBackgroundColor(getResources().getColor(R.color.graph));
        seriesUnder.setDrawBackground(true);

        graphHeight.addSeries(seriesUnder);

        plotHeight();
        return view;
    }

    //plot height graph
    public void plotHeight() {

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.HEIGHT+"/"+childIdImmunizationHold,

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

    public void json_parse_data(JSONArray array) {

        for (int i = 0; i < array.length(); i++) {


            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                int x = json.getInt("id");
                double y = json.getDouble("height");

                series.appendData(new DataPoint(i, y), true, 10);
                graphHeight.addSeries(series);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

    }

    // method to input child height
    public void recordHeight() {
        String height = inputHeight.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("height", height);
        params.put("child_id", childIdImmunizationHold);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading......................Hold On!");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.RECORD_HEIGHT, params,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            success = response.getInt("id");
                            if (success >= 1) {
                                String id = response.getString("id");
                                // amount.setText(amountt);
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("SUCCESSFULLY")
                                        .setContentText("RECORDED")
                                        .show();
                                // Toast.makeText(getActivity(), amountt, Toast.LENGTH_SHORT).show();
                                FragmentTransaction f = getActivity().getSupportFragmentManager().beginTransaction();
                                f.replace(R.id.nurseNavigator, new HeightNurse(), getString(R.string.app_name));
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
                        .setContentText("PLEASE TRY AGAIN" + response)
                        .show();
                // Toast.makeText(getActivity(), "Somthing went wrong please try again ", Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

}
