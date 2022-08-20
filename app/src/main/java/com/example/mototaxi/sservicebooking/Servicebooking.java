package com.example.mototaxi.sservicebooking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mototaxi.R;

public class Servicebooking extends AppCompatActivity {
 Button conform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicebooking);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        conform=findViewById(R.id.conform);
        conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Servicebooking.this, Bookingconform.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Servicebooking.this.finish();
            }
        });
    }
}