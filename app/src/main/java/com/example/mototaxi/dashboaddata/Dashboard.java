package com.example.mototaxi.dashboaddata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mototaxi.analytics.Analtic;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.breakdownassistant.Breakdown_booking;
import com.example.mototaxi.encryptapp.DarKnight;
import com.example.mototaxi.fastag.Fastag_activity;
import com.example.mototaxi.fual.Fualscreen;
import com.example.mototaxi.historydata.history;
import com.example.mototaxi.notification.Notify;
import com.example.mototaxi.odb.MainActivitynew;
import com.example.mototaxi.profiledata.Profile;
import com.example.mototaxi.R;
import com.example.mototaxi.servicecost.ServicecostActivity;
import com.example.mototaxi.sservicebooking.Service_booking;
import com.example.mototaxi.teackdata.Tracking_activity;
import com.example.mototaxi.vechicledata.Vechile;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;
import com.example.mototaxi.walletdata.Wallet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ss.com.bannerslider.Slider;

public class Dashboard extends AppCompatActivity   {

   Slider slider;
    BottomNavigationView  bottomNavigationView;
    CircleImageView  profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
        String mytoken=sharedPreferences.getString("token","");
        System.out.println("token= "+mytoken);
      //  logi1(mytoken);
        home existingUserFragment = new home();
        setFragment(R.id.content, existingUserFragment);


        profile=(CircleImageView)findViewById(R.id.profile);



//        walletlayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent a = new Intent(Dashboard.this, Wallet.class);
//                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(a);
//                Dashboard.this.finish();
//            }
//        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Dashboard.this, Profile.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Dashboard.this.finish();
            }
        });


        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
               // if(item.g)
            }
        });








//        Battamn.setOnNavigationItemReselectedListener { item ->
//                when(item.itemId) {
//            R.id.item1 -> {
//                // Respond to navigation item 1 reselection
//            }
//            R.id.item2 -> {
//                // Respond to navigation item 2 reselection
//            }
//        }
//        }
    }

    protected void setFragment(int container, Fragment fragment) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction;
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(container, fragment, fragment.getClass().getSimpleName());
            getSupportActionBar().hide();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            Log.e("Errir", Log.getStackTraceString(e));
        }

    }

    public void logi1(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.tokencheck, "token",token);

        }catch (Exception e){
            Toast.makeText(Dashboard.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }
    public void getbanner(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.banner, "banner",token);

        }catch (Exception e){
            Toast.makeText(Dashboard.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    Intent a = new Intent(Dashboard.this,   Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Dashboard.this.finish();
                    // start activity 1
                    return true;
                case R.id.page_2:
                    Intent a2 = new Intent(Dashboard.this,   history.class);
                    a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a2);
                    Dashboard.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_3:
                    Intent a1 = new Intent(Dashboard.this, Notify.class);
                    a1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a1);
                    Dashboard.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_4:
                    // start activity 2
                    Intent a3 = new Intent(Dashboard.this, Analtic.class);
                    a3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a3);
                    Dashboard.this.finish();
                    return true;

                default:
                    //default intent
                    Intent a5 = new Intent(Dashboard.this,   Dashboard.class);
                    a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a5);
                    Dashboard.this.finish();
                    return true;
            }
           // return false;
        }

    };
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Logout Alert");
        builder.setMessage("Do you want to Logout? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("key", "false");
                editor.putString("token","");
                editor.commit();
                Dashboard.this.finish();
                Dashboard.super.onBackPressed();
            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              //  Dashboard.super.onBackPressed();
            }
        });
        builder.show();

    }

//    @Override
//    public void handleResult(String method_name, JSONObject response) {
//        if (method_name.equals("token")) {
//            try {
//                String status = response.getString("message");
//
//
//                int code = response.getInt("code");
//                if(code==200){
//                    Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();
//                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
//
//                    String mytoken=sharedPreferences.getString("token","");
//                    System.out.println("token= "+mytoken);
//                    getbanner(mytoken);
//                }else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//                    builder.setTitle("Token Expired");
//                    builder.setMessage("Please login Again!");
//                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("key", "false");
//                            editor.putString("token","");
//                            editor.commit();
//                            Dashboard.this.finish();
//                        }
//                    });
//                    builder.show();
//
//                }
//
//
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        }else
//        if (method_name.equals("banner")) {
//            try {
//                String status = response.getString("message");
//                //  Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();
//
//                int code = response.getInt("code");
//                if(code==200){
//                   // Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();
//                    String path = response.getString("path");
//                    String myopt1= DarKnight.getDecrypted(path);
//                    System.out.println(myopt1);
//                    Toast.makeText(Dashboard.this, myopt1, Toast.LENGTH_SHORT).show();
//                    String output = response.getString("output");
//                    JSONArray jsonArr = new JSONArray(output);
//                    ArrayList<String>myurls=new ArrayList<>();
//
//                    for (int i = 0; i < jsonArr.length(); i++)
//                    {
//                        JSONObject jsonObj = jsonArr.getJSONObject(i);
//                       String url= Constent.Mainhost+"/"+myopt1+"/"+jsonObj.getString("name");
//
//                        System.out.println("myyy"+url);
//                        myurls.add(url);
//                    }
//                    //test
//                   // myurls.clear();
//                   // myurls.add("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg");
//                    Slider.init(new PicassoImageLoadingService(Dashboard.this));
//                    slider = findViewById(R.id.banner_slider1);
//
//                     slider.setAdapter(new MainSliderAdapter(myurls));
//                    slider.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            slider.setAdapter(new MainSliderAdapter(myurls));
//                            slider.setSelectedSlide(0);
//                        }
//                    }, 2000);
//                }else {
//
//                    String error = response.getString("error");
//                    Toast.makeText(Dashboard.this, error, Toast.LENGTH_SHORT).show();
//
//                }
//                String error = response.getString("error");
//
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
//
//
//
//    }

//    @Override
//    public void handleError(VolleyError e) {
//        //Toast.makeText(Dashboard.this, e.toString(), Toast.LENGTH_SHORT).show();
////        AlertDialog.Builder builder = new AlertDialog.Builder(this);
////
////        builder.setTitle("Authentication Failure");
////        builder.setMessage("Please Reload screen");
////        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int id) {
////                Intent a5 = new Intent(Dashboard.this,   Dashboard.class);
////                a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////             startActivity(a5);
////             Dashboard.this.finish();
////            }
////        });
////        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int id) {
////                //  Dashboard.super.onBackPressed();
////            }
////        });
////        builder.show();
//
//        Slider.init(new PicassoImageLoadingService(Dashboard.this));
//        slider = findViewById(R.id.banner_slider1);
//        ArrayList<String>myurls=new ArrayList<>();
//        myurls.add("http://admin.motoservice.in//uploads/240_F_263925978_n2wDTEuYVoFUOhN6HXo1cagZSZE2YzmB.jpg");
//        slider.setAdapter(new MainSliderAdapter(myurls));
//        slider.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                slider.setAdapter(new MainSliderAdapter(myurls));
//                slider.setSelectedSlide(0);
//            }
//        }, 2000);
////        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
////                SharedPreferences.Editor editor = sharedPreferences.edit();
////                editor.putString("key", "false");
////                editor.putString("token","");
////                editor.commit();
////        Intent a5 = new Intent(Dashboard.this,   Sinupscreen.class);
////        a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
////        startActivity(a5);
////        Dashboard.this.finish();
////        AlertDialog.Builder builder = new AlertDialog.Builder(this);
////
////        builder.setTitle("Token Expired");
////        builder.setMessage("Please login Again!");
////        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int id) {
////                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Dashboard.this);
////                SharedPreferences.Editor editor = sharedPreferences.edit();
////                editor.putString("key", "false");
////                editor.putString("token","");
////                editor.commit();
////                Dashboard.this.finish();
////                Dashboard.super.onBackPressed();
////            }
////        });
////        builder.show();
//
//    }
}