package com.example.user.m_toto.ParentFragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.m_toto.Models.ForumModel;
import com.example.user.m_toto.Network.AppController;
import com.example.user.m_toto.Network.CustomRequest;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.ParentMainAdapter.RecyclerForumAdapter;
import com.example.user.m_toto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;
import static com.example.user.m_toto.ParentNavigator.MyPREF;
import static com.example.user.m_toto.ParentNavigator.fab;
import static com.example.user.m_toto.ParentNavigator.loggedMotherId;

/**
 * A simple {@link Fragment} subclass.
 */
public class Forum extends Fragment {

    ImageButton buttonSend;
    EditText editTextMessage;
   // ProgressDialog pDialog;

    List<ForumModel> GetChildren1;
    RecyclerView recyclerView;
    ImageView empty_child_list;

    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter recyclerViewadapter;

    RequestQueue requestQueue ;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_forum, container, false);

        editTextMessage= (EditText) view.findViewById(R.id.forumMessage);
        buttonSend= (ImageButton) view.findViewById(R.id.sendMessageButton);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        GetChildren1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerforum);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        getMessages();

        fab.setVisibility(View.GONE);

        return view;
    }

    //send message method
    public void sendMessage(){

        SharedPreferences editor= getActivity().getSharedPreferences(MyPREF,MODE_PRIVATE);
        int mother_id=editor.getInt("mother_id",0);



//        pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Sending.....");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
        final ProgressDialog pDialog = ProgressDialog.show(getActivity(),"Sending ", "..");

        String message=editTextMessage.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("message", message);
       // params.put("mother_id", loggedMotherId );
       // params.put("mother_id", loggedMotherId );


        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.SEND_MESSAGE+"/"+mother_id, params,
        //CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.SEND_MESSAGE, params,
                new Response.Listener<JSONObject>() {
                    int success;

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            success = response.getInt("id");
                            if (success >=1) {
                                Log.d("Message Successful!", response.toString());

//                                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                                        .setTitleText("Message Sent")
//                                        .setContentText("Successfully")
//                                        .show();
                                 Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_SHORT).show();
                                FragmentTransaction f=getActivity().getSupportFragmentManager().beginTransaction();
                                f.replace(R.id.parent_navigator, new Forum(), getString(R.string.app_name));
                                f.commit();

                            } else {
                                pDialog.dismiss();

                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("OPPS...!!")
                                        .setContentText("Something went wrong. TRY AGAIN"+response)
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
                        .setContentText("Check Your Network Connection"+response)
                        .show();
                // Toast.makeText(getActivity(), "Somthing went wrong please try again ", Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    
    //get all messages
    public void getMessages(){

//        pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Please wait...........");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
        final ProgressDialog pDialog = ProgressDialog.show(getActivity()," Loading Messages", "Please wait..");
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URLs.GET_MESSAGES,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pDialog.dismiss();
                       // progressBar.setVisibility(View.GONE);
                        json_parse_data(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // progressBar.setVisibility(View.GONE);
                        pDialog.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...!")
                                .setContentText("PLEASE CHECK YOUR NETWORK CONNECTION")
                                .show();

                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);
    }

    public void  json_parse_data(JSONArray array) {

        if (array.length() == 0) {
           // empty_child_list.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "No messages found", Toast.LENGTH_SHORT).show();
        } else {


            for (int i = 0; i < array.length(); i++) {

                ForumModel GetChildren2 = new ForumModel();

                JSONObject json = null;
                try {
                    json = array.getJSONObject(i);

                    GetChildren2.setDate(json.getString("created_at"));
                    GetChildren2.setSent_by(json.getString("firstName"));
                    GetChildren2.setMessage(json.getString("message"));


                } catch (JSONException e) {

                    e.printStackTrace();
                }
                GetChildren1.add(GetChildren2);
            }
        }

        recyclerViewadapter = new RecyclerForumAdapter(GetChildren1, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);
    }

}
