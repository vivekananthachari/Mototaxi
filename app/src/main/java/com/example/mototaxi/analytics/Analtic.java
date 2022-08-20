package com.example.mototaxi.analytics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.mototaxi.R;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.faqdata.Faq;
import com.example.mototaxi.historydata.history;
import com.example.mototaxi.profiledata.Profile;
import com.example.mototaxi.vechicledata.MyListDatavechile;
import com.example.mototaxi.vechicledata.Vechile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Analtic extends AppCompatActivity {
   TextView textView;
    BottomNavigationView navigation;

    LinearLayout faq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analtic);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
       // textView=(TextView) findViewById(R.id.text);
        navigation = findViewById(R.id.bottom_navigation);
        navigation.getMenu().getItem(3).setChecked(true);
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener);
        faq=(LinearLayout) findViewById(R.id.faq);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Analtic.this, Faq.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Analtic.this.finish();
            }
        });

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopup(v);
//            }
//        });


    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.option_menu, popup.getMenu());
        popup.show();

    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Analtic.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Analtic.this.finish();
        super.onBackPressed();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    Intent a = new Intent(Analtic.this,   Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Analtic.this.finish();

                    // start activity 1
                    return true;
                case R.id.page_2:
                    Intent a2 = new Intent(Analtic.this,   Vechile.class);
                    a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a2);
                    Analtic.this.finish();

                    // start activity 2
                    return true;
                case R.id.page_3:
                    Intent a1 = new Intent(Analtic.this, history.class);
                    a1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a1);
                    Analtic.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_4:
                    // start activity 2
                    Intent a3 = new Intent(Analtic.this, Analtic.class);
                    a3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a3);
                    Analtic.this.finish();

                    return true;

                default:
                    //default intent
                    Intent a5 = new Intent(Analtic.this,   Dashboard.class);
                    a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a5);
                    Analtic.this.finish();


                    return true;
            }

            // return false;

        }


    };

}