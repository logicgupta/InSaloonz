package com.logistic.logic.e_saloon;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.logistic.logic.e_saloon.Intro.AppMainIntro;
import com.logistic.logic.e_saloon.Login.Login_Activity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends Activity {

    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String projectID = "esaloon-5fd69";
        FirebaseOptions options=new FirebaseOptions.Builder()
                .setApplicationId("1:223613673427:android:3b4839d0bba76d3f")
                .setApiKey("AIzaSyDSXAbbFKVhqzwFdVTe2ALCgveIKlVhQXY")
                .setDatabaseUrl("https://esaloon-5fd69.firebaseio.com")
                .setProjectId(projectID)
                .setStorageBucket("esaloon-5fd69.appspot.com")
                .build();
        FirebaseApp.initializeApp(getApplicationContext(),options);

        // Call the function callInstamojo to start payment here
        auth=FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                        if (auth.getCurrentUser()!=null){
                            startActivity(new Intent(SplashActivity.this, Login_Activity.class));

                        }
                        else {
                            startActivity(new Intent(SplashActivity.this, AppMainIntro.class));
                            finish();
                        }


            }
        },2000);
    }
}
