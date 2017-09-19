package com.example.user.m_toto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.user.m_toto.LoginActivity.MyPREF;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences editor = getSharedPreferences(MyPREF, MODE_PRIVATE);

                int id = editor.getInt("ROLE",0);

                if(id==(1)){
                    Intent i = new Intent(SplashScreen.this, ParentNavigator.class);
                    startActivity(i);
                    finish();
                } else if(id==(2)){
                    Intent i = new Intent(SplashScreen.this, NurseNavigator.class);
                    startActivity(i);
                    finish();
                } else
                if(id==(0)){
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 1000);


    }
}
