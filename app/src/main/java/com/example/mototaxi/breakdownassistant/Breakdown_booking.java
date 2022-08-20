package com.example.mototaxi.breakdownassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.mototaxi.R;
import com.example.mototaxi.analytics.Analtic;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.historydata.history;
import com.example.mototaxi.vechicledata.MyListDatavechile;
import com.example.mototaxi.vechicledata.Vechile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Breakdown_booking extends AppCompatActivity {
    RecyclerView vechilerecyclier;
    ArrayList<MyListDatavechile> myListDatanew = new ArrayList() ;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        navigation = findViewById(R.id.bottom_navigation);
        navigation.getMenu().getItem(1).setChecked(true);
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener);
        vechilerecyclier=(RecyclerView) findViewById(R.id.vechilerecyclier);
        MyListDatavechile myListDatavechile=new MyListDatavechile("Pulsar 150","Arivukani","TN2460549595");
        MyListDatavechile myListDatavechile1=new MyListDatavechile("Pulsar 100","Sreejith","TN2460577595");
        MyListDatavechile myListDatavechile2=new MyListDatavechile("TATA 700 ","Sreejith","TN2460577595");

        myListDatanew.add(myListDatavechile);
        myListDatanew.add(myListDatavechile1);
        myListDatanew.add(myListDatavechile2);
        MyListAdaptervechileserice adapter = new MyListAdaptervechileserice(myListDatanew, Breakdown_booking.this);
        vechilerecyclier.setHasFixedSize(true);
        vechilerecyclier.setLayoutManager(new LinearLayoutManager(this));
        vechilerecyclier.setAdapter(adapter);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    Intent a = new Intent(Breakdown_booking.this,   Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Breakdown_booking.this.finish();

                    // start activity 1
                    return true;
                case R.id.page_2:
                    Intent a2 = new Intent(Breakdown_booking.this,   Vechile.class);
                    a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a2);
                    Breakdown_booking.this.finish();

                    // start activity 2
                    return true;
                case R.id.page_3:
                    Intent a1 = new Intent(Breakdown_booking.this, history.class);
                    a1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a1);
                    Breakdown_booking.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_4:
                    // start activity 2
                    Intent a3 = new Intent(Breakdown_booking.this, Analtic.class);
                    a3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a3);
                    Breakdown_booking.this.finish();

                    return true;

                default:
                    //default intent
                    Intent a5 = new Intent(Breakdown_booking.this,   Dashboard.class);
                    a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a5);
                    Breakdown_booking.this.finish();


                    return true;
            }

            // return false;

        }


    };

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Breakdown_booking.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Breakdown_booking.this.finish();
        super.onBackPressed();
    }
}