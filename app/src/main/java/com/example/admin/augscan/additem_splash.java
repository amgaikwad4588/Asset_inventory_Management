package com.example.admin.augscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class additem_splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_additem_splash);
        Thread thread=new Thread()
        {
            public void run()
            {
                try {
                    sleep(3000);
                    startActivity(new Intent(additem_splash.this,dashboardActivity.class));
                    finish();
                }catch(Exception e)
                {

                }
            }
        };thread.start();
    }
}