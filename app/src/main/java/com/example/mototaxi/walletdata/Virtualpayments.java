package com.example.mototaxi.walletdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.mototaxi.R;
import com.example.mototaxi.analytics.Analtic;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.historydata.history;
import com.example.mototaxi.vechicledata.Vechile;
import com.example.mototaxi.walletdata.paytm.checksum;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Virtualpayments extends AppCompatActivity {
  LinearLayout vitual2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualpayments);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        vitual2=findViewById(R.id.vitual2);
        vitual2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Virtualpayments.this, checksum.class);
//                intent.putExtra("orderid", orderid.getText().toString());
//                intent.putExtra("custid", custid.getText().toString());

                intent.putExtra("orderid", "743636525362");
                intent.putExtra("custid", "5263744758");
                startActivity(intent);
            }
        });
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.getMenu().getItem(0).setChecked(false);
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener);
    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Virtualpayments.this, Wallet.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Virtualpayments.this.finish();
        super.onBackPressed();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    Intent a = new Intent(Virtualpayments.this,   Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Virtualpayments.this.finish();
                    // start activity 1
                    return true;
                case R.id.page_2:
                    Intent a2 = new Intent(Virtualpayments.this,   Vechile.class);
                    a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a2);
                    Virtualpayments.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_3:
                    Intent a1 = new Intent(Virtualpayments.this, history.class);
                    a1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a1);
                    Virtualpayments.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_4:
                    // start activity 2
                    Intent a3 = new Intent(Virtualpayments.this, Analtic.class);
                    a3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a3);
                    Virtualpayments.this.finish();
                    return true;

                default:
                    //default intent
                    Intent a5 = new Intent(Virtualpayments.this,   Dashboard.class);
                    a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a5);
                    Virtualpayments.this.finish();
                    return true;
            }
            // return false;
        }

    };

}