package com.example.mototaxi.historydata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mototaxi.R;
import com.example.mototaxi.analytics.Analtic;
import com.example.mototaxi.analytics.MyListAdaptervechilehorizonal;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.vechicledata.MyListDatavechile;
import com.example.mototaxi.vechicledata.Vechile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class history extends AppCompatActivity {
  RecyclerView historycycle;
    ArrayList<MyListDatahistory> myListDatanew1= new ArrayList() ;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
         navigation = findViewById(R.id.bottom_navigation);
        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener);
       // navigation.setSelectedItemId(R.id.page_3);


        MyListDatahistory myListData1=new MyListDatahistory("24-Nov-2020","456 Days old","Sat Dec 26 12.47","\u20B9 165.0","Auto CHN 3348958758");
        MyListDatahistory myListData2=new MyListDatahistory("24-Nov-2020","456 Days old","Sat Dec 26 12.47","\u20B9 165.0","Auto CHN 3348958758");

        myListDatanew1.add(myListData1);
        myListDatanew1.add(myListData2);
        historycycle=findViewById(R.id.historycycle);
        MyListAdapterhistory adapter1 = new MyListAdapterhistory(myListDatanew1, history.this);
        historycycle.setHasFixedSize(true);
        historycycle.setLayoutManager(new LinearLayoutManager(this));
        historycycle.setAdapter(adapter1);

    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(history.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        history.this.finish();
        super.onBackPressed();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    Intent a = new Intent(history.this,   Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    history.this.finish();

                    // start activity 1
                    return true;
                case R.id.page_2:
                    Intent a2 = new Intent(history.this,   Vechile.class);
                    a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a2);
                    history.this.finish();

                    // start activity 2
                    return true;
                case R.id.page_3:
                    Intent a1 = new Intent(history.this, history.class);
                    a1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a1);
                    history.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_4:
                    // start activity 2
                    Intent a3 = new Intent(history.this, Analtic.class);
                    a3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a3);
                    history.this.finish();

                    return true;

                default:
                    //default intent
                    Intent a5 = new Intent(history.this,   Dashboard.class);
                    a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a5);
                    history.this.finish();


                    return true;
            }

            // return false;

        }


    };


}