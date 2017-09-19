package com.example.user.m_toto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.user.m_toto.Fragment.HomeFragment;
import com.example.user.m_toto.Network.AppController;
import com.example.user.m_toto.Network.CustomRequest;
import com.example.user.m_toto.Network.URLs;
import com.example.user.m_toto.ParentFragment.ChildrenListClinic;
import com.example.user.m_toto.ParentFragment.Forum;
import com.example.user.m_toto.ParentFragment.HomeParent;
import com.example.user.m_toto.ParentFragment.NextVisit;
import com.example.user.m_toto.ParentFragment.RecommendedFeeding;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.user.m_toto.LoginActivity.MyPREF;
import static com.example.user.m_toto.LoginActivity.person_id;

public class ParentNavigator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public  static FloatingActionButton fab;
    public static  String childClinicIdHold,childClinicNameHold,childClinicDobHold,loggedMotherId;
    public static final String MyPREF="mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_navigator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //call fragment
        FragmentTransaction f=getSupportFragmentManager().beginTransaction();
        f.replace(R.id.parent_navigator, new HomeParent(), getString(R.string.app_name));
        f.commit();


    fab = (FloatingActionButton) findViewById(R.id.fabParent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Call Emergency", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //getMotherId();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.parent_navigator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            SharedPreferences.Editor editor= getSharedPreferences(MyPREF,MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();

            Intent i = new Intent(ParentNavigator.this,LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {;
//            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
//            f.replace(R.id.parent_navigator, new HomeParent(), getString(R.string.app_name));
//            f.commit();
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.parent_navigator, new HomeParent(), getString(R.string.app_name));
            f.commit();
        }
        else if (id == R.id.nextVisit) {
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.parent_navigator, new NextVisit(), getString(R.string.app_name));
            f.commit();

        }
        else if (id == R.id.clinic) {
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.parent_navigator, new ChildrenListClinic(), getString(R.string.app_name));
            f.commit();

        }else if (id == R.id.feeding) {
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.parent_navigator, new RecommendedFeeding(), getString(R.string.app_name));
            f.commit();

        }

        else if (id == R.id.forum) {

            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.parent_navigator, new Forum(), getString(R.string.app_name));
            f.commit();
        }
        else if (id == R.id.pLogut) {
            SharedPreferences.Editor editor= getSharedPreferences(MyPREF,MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();

            Intent i = new Intent(ParentNavigator.this,LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void goToClinic(String child_id, String child_name, String child_dob){

        childClinicIdHold=child_id;
        childClinicNameHold=child_name;
        childClinicDobHold=child_dob;

        Intent i=new Intent(ParentNavigator.this, MainActivity.class);
        startActivity(i);

    }

    //method to get mother id

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
                new SweetAlertDialog(ParentNavigator.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("OOPS")
                        .setContentText(""+response)
                        .show();


            }
        });
        AppController.getInstance().addToRequestQueue(jsObjRequest);
    }

}
