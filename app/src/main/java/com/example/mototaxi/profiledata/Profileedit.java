package com.example.mototaxi.profiledata;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mototaxi.R;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Profileedit extends AppCompatActivity implements VolleyTasksListener {
RecyclerView recyclier;
Button save;
TextView newaddress;
EditText name,email;
TextView number;
    ArrayList<MyListData> myListDatanew = new ArrayList() ;

    GoogleMap mMap;
    // New variables for Current Place Picker
    final String TAG = "MapsActivity";
    ListView lstPlaces;
    PlacesClient mPlacesClient;
    String uuid;


    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.


    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.


    // Used for selecting the current place.

//            new MyListData("Temp address", "Adddressss"),
//            new MyListData("Permanent Address", "new streeet"),

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileedit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        newaddress=(TextView) findViewById(R.id.newaddress);
         save=findViewById(R.id.save);
        Intent intent=new Intent(getIntent());
        if(intent!=null){
            if(intent.getExtras()==null){

            }else {
                String auuid= intent.getExtras().get("auid").toString();
                String label= intent.getExtras().get("label").toString();
                String address=intent.getExtras().get("address").toString();
                String lat=intent.getExtras().get("lat").toString();
                String longnew=intent.getExtras().get("long").toString();
                MyListData myListData=new MyListData(auuid,label,address,lat,longnew);

                myListDatanew.add(myListData);
            }

        }
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        number=findViewById(R.id.number);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Profileedit.this);
        String mytoken=sharedPreferences.getString("token","");
        System.out.println("token= "+mytoken);
        getprofile(mytoken);

  save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Profileedit.this);
          String mytoken=sharedPreferences.getString("token","");
          System.out.println("token= "+mytoken);
          updateprofile(mytoken);

      }
  });
        newaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showDialog(Profileedit.this,"","");
                Intent a = new Intent(Profileedit.this, MapsActivity.class);
//                a.putExtra("auid",uuid);
               a.putExtra("type","add");
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Profileedit.this.finish();
            }
        });

        recyclier=findViewById(R.id.recyclier);
        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(Profileedit.this);
        String mytoken1=sharedPreferences1.getString("token","");
        System.out.println("token= "+mytoken1);
        getaddress(mytoken1);

    }


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Profileedit.this, Profile.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Profileedit.this.finish();
        super.onBackPressed();
    }

    public void getprofile(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.profile_details, "profile",token);

        }catch (Exception e){
            Toast.makeText(Profileedit.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    public void getaddress(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.address_details, "Address_list",token);

        }catch (Exception e){
            Toast.makeText(Profileedit.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    public void updateprofile(String token) {
        try {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name",name.getText().toString());
            map.put("email",email.getText().toString());
            JSONObject json = new JSONObject(map);
            VolleyTasks.makeVolleyPostactiviy( this,Constent.profile_update, json, "edit",token);

        }catch (Exception e){
            Toast.makeText(Profileedit.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void handleResult(String method_name, JSONObject response) {
        if (method_name.equals("profile")) {
            try {
                String status = response.getString("message");
                //  Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                if(code==200){
                    // Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();

                    String output = response.getString("output");
                    JSONArray jsonArr = new JSONArray(output);
                    ArrayList<String>myurls=new ArrayList<>();

                    for (int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String namenew= jsonObj.getString("name");
                        name.setText(namenew);
                        String emailnew= jsonObj.getString("email");
                        email.setText(emailnew);
                        String mobilenumber= jsonObj.getString("mobilenumber");
                        number.setText(mobilenumber);
                    }
                    //test

                    //  slider.setAdapter(new MainSliderAdapter());

                }else {

                    String error = response.getString("error");
                    Toast.makeText(Profileedit.this, error, Toast.LENGTH_SHORT).show();

                }
                String error = response.getString("error");




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else         if (method_name.equals("edit")) {
            try {
                String status = response.getString("message");
                Toast.makeText(Profileedit.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                if(code==200){
                    Toast.makeText(Profileedit.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(Profileedit.this, Profile.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Profileedit.this.finish();
                    // Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();

//                    String output = response.getString("output");
//                    JSONArray jsonArr = new JSONArray(output);
//                    ArrayList<String>myurls=new ArrayList<>();
//
//                    for (int i = 0; i < jsonArr.length(); i++)
//                    {
//                        JSONObject jsonObj = jsonArr.getJSONObject(i);
//                        String namenew= jsonObj.getString("name");
//                        name.setText(namenew);
//                        String emailnew= jsonObj.getString("email");
//                        email.setText(emailnew);
//                        String mobilenumber= jsonObj.getString("mobilenumber");
//                        number.setText(mobilenumber);
//                    }
                    //test

                    //  slider.setAdapter(new MainSliderAdapter());

                }else {

                    String error = response.getString("error");
                    Toast.makeText(Profileedit.this, error, Toast.LENGTH_SHORT).show();

                }
                String error = response.getString("error");




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else      if (method_name.equals("Address_list")) {
            try {
                String status = response.getString("message");
                //  Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                if(code==200){
                    // Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();

                    String output = response.getString("output");
                    JSONArray jsonArr = new JSONArray(output);
                    ArrayList<String>myurls=new ArrayList<>();

                    for (int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String auuid= jsonObj.getString("auuid");
                        uuid=auuid;
                        String label=jsonObj.getString("label");
                        String address=jsonObj.getString("address");
                        String lat=jsonObj.getString("lat");
                        String longnew=jsonObj.getString("long");
                        MyListData myListData=new MyListData(auuid,label,address,lat,longnew);
                        myListDatanew.add(myListData);

                    }

                    MyListAdapter adapter = new MyListAdapter(myListDatanew,Profileedit.this);
                    recyclier.setHasFixedSize(true);
                    recyclier.setLayoutManager(new LinearLayoutManager(this));
                    recyclier.setAdapter(adapter);
                    //test

                    //  slider.setAdapter(new MainSliderAdapter());

                }else {

                    String error = response.getString("error");
                    Toast.makeText(Profileedit.this, error, Toast.LENGTH_SHORT).show();

                }
                String error = response.getString("error");




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }

    @Override
    public void handleError(VolleyError e) {

    }
}