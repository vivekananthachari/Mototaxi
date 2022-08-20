package com.example.mototaxi.faqdata;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mototaxi.R;
import com.example.mototaxi.analytics.Analtic;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.profiledata.Profile;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Faq extends AppCompatActivity implements VolleyTasksListener {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),
                    "Data Saved Successfully.",
                    Toast.LENGTH_SHORT).show();
                Intent a = new Intent(Faq.this, Profile.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Faq.this.finish();
            }
        });
        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(Faq.this);
        String mytoken1=sharedPreferences1.getString("token","");
        System.out.println("token= "+mytoken1);
        getaddress(mytoken1);
}

    public void getaddress(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.faq, "privasy",token);

        }catch (Exception e){
            Toast.makeText(Faq.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Faq.this, Analtic.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Faq.this.finish();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleResult(String method_name, JSONObject response) {
        if (response != null) {


              if (method_name.equals("privasy")) {
                try {
                    String status = response.getString("message");
                    // Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();

                    int code = response.getInt("code");
                    if(code==200){
                        String output = response.getString("output");
                        JSONArray jsonArr = new JSONArray(output);
                        ArrayList<String> myurls=new ArrayList<>();
                        myurls.clear();
                        HashMap<String, List<String>> expandableListDetail1 = new HashMap<String, List<String>>();
                        expandableListDetail1.clear();
                        for (int i = 0; i < jsonArr.length(); i++)
                        {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            String question= jsonObj.getString("question");
                            String answer= jsonObj.getString("answer");
                            List<String> basketball = new ArrayList<String>();
                            basketball.add(answer);
                            expandableListDetail1.put(question, basketball);


                        }
                        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
                        expandableListDetail = expandableListDetail1;
                        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
                        expandableListView.setAdapter(expandableListAdapter);
                        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                            @Override
                            public void onGroupExpand(int groupPosition) {
//            Toast.makeText(getApplicationContext(),
//                    expandableListTitle.get(groupPosition) + " List Expanded.",
//                    Toast.LENGTH_SHORT).show();
                            }
                        });

                        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                            @Override
                            public void onGroupCollapse(int groupPosition) {
//            Toast.makeText(getApplicationContext(),
//                    expandableListTitle.get(groupPosition) + " List Collapsed.",
//                    Toast.LENGTH_SHORT).show();

                            }
                        });

                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v,
                                                        int groupPosition, int childPosition, long id) {
//            Toast.makeText(
//                    getApplicationContext(),
//                    expandableListTitle.get(groupPosition)
//                            + " -> "
//                            + expandableListDetail.get(
//                            expandableListTitle.get(groupPosition)).get(
//                            childPosition), Toast.LENGTH_SHORT
//            ).show();

//                                Toast.makeText(
//                                        getApplicationContext(),
//                                        "Your Answer is "+expandableListDetail.get(
//                                                expandableListTitle.get(groupPosition)).get(
//                                                childPosition), Toast.LENGTH_SHORT
//                                ).show();

                                return false;
                            }
                        });



                    }
                    // 16 bytes IV




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }

    }



    @Override
    public void handleError(VolleyError e) {
        Toast.makeText(Faq.this, e.toString(), Toast.LENGTH_SHORT).show();

    }

}