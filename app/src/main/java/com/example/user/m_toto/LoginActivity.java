package com.example.user.m_toto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.user.m_toto.Network.AppController;
import com.example.user.m_toto.Network.AppStatus;
import com.example.user.m_toto.Network.CustomRequest;
import com.example.user.m_toto.Network.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    Button login;
    ProgressDialog pDialog;
    EditText loginusername, loginpassword;
    String username, password;
   // ProgressBar google;
    public String  loggedMotherId;
    public static  String person_id;
    public static final String MyPREF="mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btn_login);
        loginusername = (EditText) findViewById(R.id.username);
        loginpassword = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }
    public void attemptLogin() {
        username = loginusername.getText().toString();
        password = loginpassword.getText().toString();
        //Check for empty Edit Text fields
        //Check Phone Number
        if (TextUtils.isEmpty(username)) {
            loginusername.setError("Enter username");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            loginpassword.setError("Enter password");
            return;
        }
        login();

//        if(AppStatus.getInstance(getApplicationContext()).isOnline()){
//
//            login();
//
//        }else{
//            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
//                    .setTitleText("Oops...")
//                    .setContentText("No Internet! Enable Mobile Data or WiFi")
//                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sDialog) {
//                            sDialog.dismissWithAnimation();
//                        }
//                    })
//                    .show();
//
//        }

    }
    public void login() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Authenticating...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        //google.setVisibility(View.VISIBLE);

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.LOGIN_URL, params, new Response.Listener<JSONObject>() {
            int success;
          //  String suc;

            int user_id;

            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                //google.setVisibility(View.INVISIBLE);
                try {
                    //check if user exist
                    success = response.getInt("role");

                    if (success == 1) {
                       // google.setVisibility(View.INVISIBLE);
                        pDialog.dismiss();
                        Log.d("Login Failure!", response.toString());

                        final String firstname = response.getString("firstName");
                         person_id = response.getString("id");

                        user_id = response.getInt("id");

                    //shared pref
                        SharedPreferences.Editor editor = getSharedPreferences(MyPREF, MODE_PRIVATE).edit();
                        editor.putInt("ID",user_id);
                        editor.putInt("ROLE",success);
                        editor.commit();

                        Toast.makeText(LoginActivity.this, "WELCOME " + firstname, Toast.LENGTH_SHORT).show();

                        //ParentNavigator.getMotherId(user_id);
                            getMotherId();

                        Intent i = new Intent(getApplicationContext(), ParentNavigator.class);
                        startActivity(i);
                        finish();
                    }

                        else if (success == 2) {
                            Log.d("Login Successful!", response.toString());
                        pDialog.dismiss();

                            final String firstname = response.getString("firstName");

                        SharedPreferences.Editor editor = getSharedPreferences(MyPREF, MODE_PRIVATE).edit();
                        editor.putInt("ID",user_id);
                        editor.putInt("ROLE",success);
                        editor.commit();

                            Toast.makeText(LoginActivity.this, "WELCOME " + firstname, Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getApplicationContext(), NurseNavigator.class);
                            startActivity(i);
                            finish();
                    }
                    else {
                        pDialog.dismiss();

                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("OOPS")
                                .setContentText("INCORRECT USERNAME OR PASSWORD. \n Please retry")
                                .show();
                        loginpassword.setText("");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
              pDialog.dismiss();
              //  google.setVisibility(View.INVISIBLE);
                Log.d("Response: ", response.toString());
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("OOPS")
                        .setContentText(""+response)
                        .show();
                //  Toast.makeText(LoginActivity.this, "NETWORK ERROR", Toast.LENGTH_SHORT).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }
    @Override
    public void onBackPressed() {
    }

    public  void getMotherId(){
        SharedPreferences editor= getSharedPreferences(MyPREF,MODE_PRIVATE);
        int user_id=editor.getInt("ID",0);

        //CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.GET_MOTHER_ID+"/"+person_id, null, new Response.Listener<JSONObject>() {
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, URLs.GET_MOTHER_ID+"/"+user_id, null, new Response.Listener<JSONObject>() {
            int success;


            @Override
            public void onResponse(JSONObject response) {

                try {

                    success = response.getInt("id");

                    if (success >= 1) {

                        loggedMotherId=response.getString("id");
                        //Toast.makeText(ParentNavigator.this, "yes " + success, Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = getSharedPreferences(MyPREF, MODE_PRIVATE).edit();
                        editor.putInt("mother_id",success);
                        editor.commit();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {

                Log.d("Response: ", response.toString());
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("OOPS")
                        .setContentText(""+response)
                        .show();


            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

}