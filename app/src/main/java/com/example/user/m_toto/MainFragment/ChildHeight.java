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
import com.example.user.m_toto.Adapter.GetParentAdapter;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.user.m_toto.ParentNavigator.childClinicIdHold;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildHeight extends Fragment {
    GraphView graph;
    private LineGraphSeries<DataPoint> seriesObess,seriesUnder,series;

    ProgressDialog progressDialog;
    RequestQueue requestQueue ;

    public ChildHeight() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_child_height, container, false);
         graph = (GraphView) view.findViewById(R.id.graph);

        series = new LineGraphSeries<>();

        seriesObess=new LineGraphSeries<>();

        seriesUnder=new LineGraphSeries<>();

       
        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        
        // plot graph for obess
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
        seriesObess.setColor(Color.RED);

        //custom paint doted
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 10}, 0));
        seriesObess.setCustomPaint(paint);

        graph.addSeries(seriesObess);

        // plot graph for under length
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
        seriesUnder.setTitle("under length");
        seriesUnder.setColor(Color.RED);

        Paint paintUnder = new Paint();
        paintUnder.setStyle(Paint.Style.STROKE);
        paintUnder.setStrokeWidth(5);
        paintUnder.setColor(Color.RED);
        paintUnder.setPathEffect(new DashPathEffect(new float[]{8, 10}, 0));
        seriesUnder.setCustomPaint(paintUnder);
        //shade under
        seriesUnder.setBackgroundColor(getResources().getColor(R.color.graph));
        seriesUnder.setDrawBackground(true);
        graph.addSeries(seriesUnder);

        //customize view port
//        Viewport viewport = graph.getViewport();
//        viewport.setYAxisBoundsManual(true);
//        viewport.setMinY(0);
//        viewport.setMinX(0);
//       // viewport.setMaxY(10);
//        viewport.setScrollable(true);

        plotGraph();

        return  view;
    }
    public void plotGraph(){
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Loading......................Hold On!");
//        progressDialog.setIndeterminate(false);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        final ProgressDialog progress = ProgressDialog.show(getActivity()," Loading Height", "Please wait..");
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.HEIGHT+"/"+childClinicIdHold,

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
              double y=json.getDouble("height");
                //String xx=json.getString("created_at");

//                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
//                        //new DataPoint(x, y),
//
//                });
//                graph.addSeries(series);

              series.appendData(new DataPoint(i, y), true, 10);
                graph.addSeries(series);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

    }
}
