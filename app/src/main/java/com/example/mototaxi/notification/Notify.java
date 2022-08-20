package com.example.mototaxi.notification;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mototaxi.R;
import com.example.mototaxi.analytics.MyListAdaptervechilehorizonal;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.servicecost.ServicecostActivity;
import com.example.mototaxi.sservicebooking.Delarlocation;
import com.example.mototaxi.sservicebooking.MyListAdapterdelear;
import com.example.mototaxi.vechicledata.MyListDatavechile;

import java.util.ArrayList;

public class Notify extends AppCompatActivity {
    RecyclerView vechilerecyclier;
    ArrayList<MyListDatanoft> myListDatanew = new ArrayList() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        vechilerecyclier=(RecyclerView) findViewById(R.id.vechilerecyclier);
        MyListDatanoft myListDatavechile=new MyListDatanoft("Sat, Dec 26 12.27\n" +
                "Booking Confirmed And Share OTP:2525 Before Departing.");
        MyListDatanoft myListDatavechile1=new MyListDatanoft("Sat, Dec 26 12.25\n" +
                "Booking Cancelled.\n" );

        myListDatanew.add(myListDatavechile);
        myListDatanew.add(myListDatavechile1);

        MyListAdapternoti adapter = new MyListAdapternoti(myListDatanew, Notify.this);
        vechilerecyclier.setHasFixedSize(true);
        vechilerecyclier.setLayoutManager(new LinearLayoutManager(this));
        vechilerecyclier.setAdapter(adapter);

        // vechilerecyclier.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public void onBackPressed() {

        Intent a = new Intent(Notify.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Notify.this.finish();
        super.onBackPressed();
    }
}