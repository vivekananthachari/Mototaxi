package com.example.mototaxi.fual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mototaxi.R;
import com.example.mototaxi.analytics.Analtic;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.historydata.history;
import com.example.mototaxi.vechicledata.Vechile;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fualscreen extends AppCompatActivity implements VolleyTasksListener {
   RecyclerView pricelist,pricelistnew;
    BottomNavigationView navigation;
    ArrayList<MyListDatafual> myListDatanew = new ArrayList() ;
    ArrayList<MyListDatafualnew> myListDatanew1 = new ArrayList() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fualscreen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        navigation = findViewById(R.id.bottom_navigation);
        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener);
        pricelist=findViewById(R.id.pricelist);
        pricelistnew=findViewById(R.id.pricelistnew);
       // MyListDatafual myListDatavechil1=new MyListDatafual("DIESEL","\u20B9 16345.0","0.5");
        MyListDatafual myListDatavechile=new MyListDatafual("PETROL","\u20B9 16345.0","0.5");
        MyListDatafual myListDatavechil1=new MyListDatafual("DIESEL","\u20B9 16345.0","0.5");
        MyListDatafual myListDatavechil2=new MyListDatafual("CNG","\u20B9 16345.0","0.5");

        myListDatanew.add(myListDatavechile);
        myListDatanew.add(myListDatavechil1);
        myListDatanew.add(myListDatavechil2);

        MyListAdapterfual adapter = new MyListAdapterfual(myListDatanew, Fualscreen.this);
       // pricelist.setHasFixedSize(true);
      //  pricelist.setLayoutManager(new LinearLayoutManager(this));
       // pricelist.setAdapter(adapter);

        pricelist.setHasFixedSize(true);
        pricelist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        // vechilerecyclier.setLayoutManager(new LinearLayoutManager(this));
        pricelist.setAdapter(adapter);
//        MyListDatafualnew myListDatavechile5=new MyListDatafualnew("Today","\u20B9 16345.0","₹ 16345.0","NA");
//        MyListDatafualnew myListDatavechile7=new MyListDatafualnew("Today","\u20B9 16345.0","₹ 16345.0","NA");
//        MyListDatafualnew myListDatavechile8=new MyListDatafualnew("Today","\u20B9 16345.0","₹ 16345.0","NA");
//        MyListDatafualnew myListDatavechile9=new MyListDatafualnew("Today","\u20B9 16345.0","₹ 16345.0","NA");
//        MyListDatafualnew myListDatavechile10=new MyListDatafualnew("Today","\u20B9 16345.0","₹ 16345.0","NA");
//        MyListDatafualnew myListDatavechile11=new MyListDatafualnew("Today","\u20B9 16345.0","₹ 16345.0","NA");
//
//        myListDatanew1.add(myListDatavechile5);
//        myListDatanew1.add(myListDatavechile7);
//        myListDatanew1.add(myListDatavechile8);
//        myListDatanew1.add(myListDatavechile9);
//        myListDatanew1.add(myListDatavechile10);
//        myListDatanew1.add(myListDatavechile11);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Fualscreen.this);
            String mytoken=sharedPreferences.getString("token","");
            System.out.println("token= "+mytoken);
        getaddress(mytoken);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    Intent a = new Intent(Fualscreen.this,   Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Fualscreen.this.finish();

                    // start activity 1
                    return true;
                case R.id.page_2:
                    Intent a2 = new Intent(Fualscreen.this,   Vechile.class);
                    a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a2);
                    Fualscreen.this.finish();

                    // start activity 2
                    return true;
                case R.id.page_3:
                    Intent a1 = new Intent(Fualscreen.this, history.class);
                    a1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a1);
                    Fualscreen.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_4:
                    // start activity 2
                    Intent a3 = new Intent(Fualscreen.this, Analtic.class);
                    a3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a3);
                    Fualscreen.this.finish();

                    return true;

                default:
                    //default intent
                    Intent a5 = new Intent(Fualscreen.this,   Dashboard.class);
                    a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a5);
                    Fualscreen.this.finish();


                    return true;
            }

            // return false;

        }


    };

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Fualscreen.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Fualscreen.this.finish();
        super.onBackPressed();
    }

    public void getaddress(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.fuelhistory, "fuel_history",token);

        }catch (Exception e){
            Toast.makeText(Fualscreen.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void handleResult(String method_name, JSONObject response) {
              if (method_name.equals("fuel_history")) {
            try {
                String status = response.getString("message");
                // Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                if(code==200){
                    String output = response.getString("output");
                    JSONArray jsonArr = new JSONArray(output);
                    ArrayList<String> myurls=new ArrayList<>();
                    myurls.clear();
                    myListDatanew1.clear();
                    for (int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String state= jsonObj.getString("state");
                        String petrol= jsonObj.getString("petrol");
                        String diesel= jsonObj.getString("diesel");
                        String date= jsonObj.getString("date");
                        MyListDatafualnew myListDatafualnew=new MyListDatafualnew(state,petrol,diesel,date);
                        myListDatanew1.add(myListDatafualnew);
                    }

                    MyListAdapterfualdata adapter1 = new MyListAdapterfualdata(myListDatanew1, Fualscreen.this);
                    pricelistnew.setHasFixedSize(true);
                    pricelistnew.setLayoutManager(new LinearLayoutManager(this));
                    pricelistnew.setAdapter(adapter1);
                }
                // 16 bytes IV




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void handleError(VolleyError e) {

    }
}