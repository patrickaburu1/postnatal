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
import android.widget.TextView;

import com.example.user.m_toto.MainFragment.ChildHeight;
import com.example.user.m_toto.MainFragment.ChildWeight;
import com.example.user.m_toto.MainFragment.ImmunizationsGiven;
import com.example.user.m_toto.ParentFragment.HomeParent;

import static com.example.user.m_toto.LoginActivity.MyPREF;
import static com.example.user.m_toto.ParentNavigator.childClinicDobHold;
import static com.example.user.m_toto.ParentNavigator.childClinicIdHold;
import static com.example.user.m_toto.ParentNavigator.childClinicNameHold;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

       public static TextView headerChildName,headerChildDob,headerChildId;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        toolbar.setTitle(childClinicNameHold);

        FragmentTransaction f=getSupportFragmentManager().beginTransaction();
        f.replace(R.id.main, new ImmunizationsGiven(), getString(R.string.app_name));
        f.commit();

        //copied
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeader = navigationView.getHeaderView(0);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // headerChildName= (TextView) findViewById(R.id.headerChildName);
        headerChildName= (TextView) navHeader.findViewById(R.id.headerChildName);
        headerChildId= (TextView) navHeader.findViewById(R.id.headerChildId);
        headerChildDob= (TextView) navHeader.findViewById(R.id.headerChildDob);

        headerChildName.setText(childClinicNameHold);
        headerChildId.setText(childClinicIdHold);
        headerChildDob.setText(childClinicDobHold);

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
        getMenuInflater().inflate(R.menu.main, menu);
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

            Intent i = new Intent(MainActivity.this,LoginActivity.class);
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

        if (id == R.id.homeParentNavigator) {

            Intent i=new Intent(MainActivity.this, ParentNavigator.class);
            startActivity(i);
        } else if (id == R.id.length) {
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.main, new ChildHeight(), getString(R.string.app_name));
            f.commit();

        } else if (id == R.id.weight) {
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.main, new ChildWeight(), getString(R.string.app_name));
            f.commit();

        } else if (id == R.id.mainImmunization) {
            FragmentTransaction f=getSupportFragmentManager().beginTransaction();
            f.replace(R.id.main, new ImmunizationsGiven(), getString(R.string.app_name));
            f.commit();

        }
//        else if (id == R.id.mainDevelopment) {
//
//        }
//        else if (id == R.id.mainDeworming) {
//
//        }
//        else if (id == R.id.mainVitamin) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
