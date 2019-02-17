package com.nyit.librarysystem.reader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nyit.librarysystem.R;
import com.nyit.librarysystem.MainActivity;

/**
 * Created by Apoorvagiri on 20-03-2016.
 */
public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 300000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
        }

