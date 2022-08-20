package com.example.mototaxi.signup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.R;

public class Flashscreen extends AppCompatActivity {
    TextView appversion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashscreen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        appversion=(TextView) findViewById(R.id.appversion);
//        appversion.setText("App Version"+" "+String.valueOf(BuildConfig.VERSION_NAME));

        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());

        ImageView image= (ImageView) findViewById(R.id.logo);

        image.startAnimation(rotate);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Flashscreen.this);
                String name = sharedPreferences.getString("key", "false");
                if(name.equals("true")){
                    Intent a = new Intent(Flashscreen.this, Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Flashscreen.this.finish();
                }else {
                    Intent a = new Intent(Flashscreen.this, Sinupscreen.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Flashscreen.this.finish();
                }


                //Do something after 100ms
            }
        }, 3000);


    }
}