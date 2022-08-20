package com.example.mototaxi.servicecost;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mototaxi.R;
import com.example.mototaxi.analytics.MyListAdaptervechilehorizonal;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.vechicledata.MyListDatavechile;

import java.util.ArrayList;

public class ServicecostActivity extends AppCompatActivity {
    RecyclerView vechilerecyclier;
    ArrayList<MyListDatavechile> myListDatanew = new ArrayList() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicecost);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        vechilerecyclier=(RecyclerView) findViewById(R.id.vechilerecyclier);
        MyListDatavechile myListDatavechile=new MyListDatavechile("Pulsar 150","Arivukani","TN2460549595");
        MyListDatavechile myListDatavechile1=new MyListDatavechile("Pulsar 100","Arivukani","TN2460549595");
        MyListDatavechile myListDatavechile2=new MyListDatavechile("Pulsar 100","Arivukani","TN2460549595");

        myListDatanew.add(myListDatavechile);
        myListDatanew.add(myListDatavechile1);
        myListDatanew.add(myListDatavechile2);
        MyListAdaptervechilehorizonal adapter = new MyListAdaptervechilehorizonal(myListDatanew, ServicecostActivity.this);
        vechilerecyclier.setHasFixedSize(true);
        vechilerecyclier.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        // vechilerecyclier.setLayoutManager(new LinearLayoutManager(this));
        vechilerecyclier.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {

        Intent a = new Intent(ServicecostActivity.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        ServicecostActivity.this.finish();
        super.onBackPressed();
    }
}