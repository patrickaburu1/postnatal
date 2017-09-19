package com.example.user.m_toto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.user.m_toto.Fragment.ChildrenList;
import com.example.user.m_toto.Fragment.ClinicCategory;
import com.example.user.m_toto.Fragment.GiveImmunization;
import com.example.user.m_toto.Fragment.HomeFragment;
import com.example.user.m_toto.Fragment.ParentList;
import com.example.user.m_toto.Fragment.RegisterNewBorn;
import com.example.user.m_toto.Fragment.Report;

import static com.example.user.m_toto.LoginActivity.MyPREF;

public class NurseNavigator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
SearchView search;

    public  static String motherPhoneHold,motherIdHold,motherNameHold,childIdImmunizationHold,childDobImmunizationHold,
            childFnameImmunizationHold,ImmunizationIdHold,ImmunizationNameHold;
    TextView motherphone,motherid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_navigator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     //   search= (SearchView) findViewById(R.id.ParentTopSearch);

        motherphone= (TextView) findViewById(R.id.motherchildphone);
        motherid= (TextView) findViewById(R.id.motherchildId);
        // search.onActionViewExpanded();

        FragmentTransaction f=getSupportFragmentManager().beginTransaction();
        f.replace(R.id.nurseNavigator, new HomeFragment(),"Clinic");
        f.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Coming Soon", Snackbar.LENGTH_LONG)
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
        getMenuInflater().inflate(R.menu.nurse_navigator, menu);
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

            Intent i = new Intent(NurseNavigator.this,LoginActivity.class);
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

        if (id == R.id.home) {
            // Handle the camera action
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.nurseNavigator, new HomeFragment(),"Clinic");
            f.commit();
           // search.setVisibility(View.GONE);
        } else if (id == R.id.clinic) {
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.nurseNavigator, new ChildrenList(),"Clinic");
            f.commit();
           // search.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.registerChild) {
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.nurseNavigator, new ParentList(),"Clinic");
            f.commit();
            // search.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.dailyRecord) {
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.nurseNavigator, new Report(),"Clinic");
            f.commit();
           // search.setVisibility(View.VISIBLE);
        }
//        else if (id == R.id.monthlyRecord) {
//
//        }
        else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor= getSharedPreferences(MyPREF,MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();

            Intent i = new Intent(NurseNavigator.this,LoginActivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //methods to other fragments
    public void goToRegisterNewBorn(String motherPhone,String motherId, String motherName){
        motherPhoneHold=motherPhone;
        motherIdHold=motherId;
        motherNameHold=motherName;
      // motherphone.setText(motherPhone);
        FragmentTransaction f=getSupportFragmentManager().beginTransaction();
        f.replace(R.id.nurseNavigator, new RegisterNewBorn(),"Clinic");
        f.commit();
       // search.setVisibility(View.GONE);

           }

    public void goToCategory(String clinic_child_id,String clinic_child_fname,String clinic_child_dob){

        childIdImmunizationHold=clinic_child_id;
        childFnameImmunizationHold=clinic_child_fname;
        childDobImmunizationHold=clinic_child_dob;

        FragmentTransaction f=getSupportFragmentManager().beginTransaction();
        f.replace(R.id.nurseNavigator, new ClinicCategory(),"Clinic");
        f.commit();
       // search.setVisibility(View.GONE);

    }
    public void goToGiveImmunization(String immunization_id,String immunization_name){

        ImmunizationIdHold=immunization_id;
        ImmunizationNameHold=immunization_name;

        FragmentTransaction f=getSupportFragmentManager().beginTransaction();
        f.replace(R.id.nurseNavigator, new GiveImmunization(),"Clinic");
        f.commit();
       // search.setVisibility(View.GONE);

    }

}
