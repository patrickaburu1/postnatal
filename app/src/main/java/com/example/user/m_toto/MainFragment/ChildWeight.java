package com.example.user.m_toto.MainFragment;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.user.m_toto.ParentNavigator.childClinicIdHold;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildWeight extends Fragment {

    GraphView graphWeight;
    private LineGraphSeries<DataPoint> series,seriesObess,seriesUnder;

    ProgressDialog progressDialog;
    RequestQueue requestQueue ;

    public ChildWeight() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_child_weight, container, false);
        graphWeight = (GraphView) view.findViewById(R.id.graphWeight);
        
        series = new LineGraphSeries<>();

        //plot graph value for over weight

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

        Paint paintObess = new Paint();
        paintObess.setStyle(Paint.Style.STROKE);
        paintObess.setStrokeWidth(5);
        paintObess.setColor(Color.RED);
        paintObess.setPathEffect(new DashPathEffect(new float[]{8, 10}, 0));
        seriesObess.setCustomPaint(paintObess);
        graphWeight.addSeries(seriesObess);

        // plot graphWeight for under weight
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
        //doted line
        Paint paintUnder = new Paint();
        paintUnder.setStyle(Paint.Style.STROKE);
        paintUnder.setStrokeWidth(5);
        paintUnder.setColor(Color.RED);
        paintUnder.setPathEffect(new DashPathEffect(new float[]{8, 10}, 0));
        seriesUnder.setCustomPaint(paintUnder);
        // shade under
        seriesUnder.setBackgroundColor(getResources().getColor(R.color.graph));
        seriesUnder.setDrawBackground(true);
        graphWeight.addSeries(seriesUnder);

        plotWeight();

        return view;
    }
    public void plotWeight(){
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Loading......................Hold On!");
//        progressDialog.setIndeterminate(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        final ProgressDialog progress = ProgressDialog.show(getActivity()," Loading Weight", "Please wait..");
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.WEIGHT+"/"+childClinicIdHold,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //progressBar.setVisibility(View.GONE);
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

}
