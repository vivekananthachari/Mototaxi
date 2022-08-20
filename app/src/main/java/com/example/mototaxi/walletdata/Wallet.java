package com.example.mototaxi.walletdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mototaxi.R;
import com.example.mototaxi.analytics.Analtic;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.historydata.history;
import com.example.mototaxi.vechicledata.Vechile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Wallet extends AppCompatActivity {
    TextView vitual;
    LinearLayout vitual1,vitual2,vital3;
    BottomNavigationView  bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        vitual=findViewById(R.id.vitual);
        vitual1=findViewById(R.id.vitual1);
        vitual2=findViewById(R.id.vitual2);
        vital3=findViewById(R.id.vitual3);
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.getMenu().getItem(0).setChecked(false);
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener);

        vitual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Wallet.this, Virtualpayments.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Wallet.this.finish();
            }
        });
        vitual1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Wallet.this,Virtualpayments.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Wallet.this.finish();
            }
        });
        vitual2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Wallet.this,Virtualpayments.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Wallet.this.finish();
            }
        });
        vital3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Wallet.this,Virtualpayments.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Wallet.this.finish();
            }
        });



    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    Intent a = new Intent(Wallet.this,   Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Wallet.this.finish();
                    // start activity 1
                    return true;
                case R.id.page_2:
                    Intent a2 = new Intent(Wallet.this,   Vechile.class);
                    a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a2);
                    Wallet.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_3:
                    Intent a1 = new Intent(Wallet.this, history.class);
                    a1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a1);
                    Wallet.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_4:
                    // start activity 2
                    Intent a3 = new Intent(Wallet.this, Analtic.class);
                    a3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a3);
                    Wallet.this.finish();
                    return true;

                default:
                    //default intent
                    Intent a5 = new Intent(Wallet.this,   Dashboard.class);
                    a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a5);
                    Wallet.this.finish();
                    return true;
            }
            // return false;
        }

    };


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Wallet.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Wallet.this.finish();
        super.onBackPressed();
    }
}