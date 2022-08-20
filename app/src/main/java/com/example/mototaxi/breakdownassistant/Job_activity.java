package com.example.mototaxi.breakdownassistant;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mototaxi.R;
import com.example.mototaxi.sservicebooking.Servicebooking;

public class Job_activity extends AppCompatActivity {
  TextView battery,tyre;
    TextView flat,lify;

    Button myclick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        battery=findViewById(R.id.battery);
        tyre=findViewById(R.id.Tyre);

        flat=findViewById(R.id.flat);
        lify=findViewById(R.id.lify);
        flat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flat.setBackgroundResource(R.drawable.mybackground);
                lify.setBackgroundResource(R.drawable.bordernewdta);
                flat.setTextColor(getResources().getColor(R.color.white));
                lify.setTextColor(getResources().getColor(R.color.black));
            }
        });
        lify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flat.setBackgroundResource(R.drawable.bordernewdta);
                lify.setBackgroundResource(R.drawable.mybackground);
                flat.setTextColor(getResources().getColor(R.color.black));
                lify.setTextColor(getResources().getColor(R.color.black));


            }
        });


        myclick=findViewById(R.id.myclick);
        myclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a2 = new Intent(Job_activity.this,   Servicebooking.class);
                a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a2);
                Job_activity.this.finish();

            }
        });
        battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battery.setBackgroundResource(R.drawable.mybackground);
                tyre.setBackgroundResource(R.drawable.bordernewdta);
                battery.setTextColor(getResources().getColor(R.color.white));
                tyre.setTextColor(getResources().getColor(R.color.black));
            }
        });
        tyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                battery.setBackgroundResource(R.drawable.bordernewdta);
                tyre.setBackgroundResource(R.drawable.mybackground);
                tyre.setTextColor(getResources().getColor(R.color.white));
                battery.setTextColor(getResources().getColor(R.color.black));


            }
        });

    }
}