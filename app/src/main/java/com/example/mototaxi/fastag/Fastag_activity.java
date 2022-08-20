package com.example.mototaxi.fastag;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mototaxi.R;
import com.example.mototaxi.dashboaddata.Dashboard;

public class Fastag_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastag);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }

    @Override
    public void onBackPressed() {

        Intent a = new Intent(Fastag_activity.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Fastag_activity.this.finish();
        super.onBackPressed();
    }

}