package com.example.funky.registeration_system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread mThread=new Thread(){
            @Override
            public void run(){
                try {
                    sleep(5000);
                    Intent mIntent = new Intent(getApplicationContext(),RegisterationActivity.class);
                    startActivity(mIntent);
                    finish();
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        mThread.start();
    }
}
