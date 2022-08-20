package com.example.mototaxi.profiledata;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.faqdata.Faq;
import com.example.mototaxi.R;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.signup.MainActivity;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Profile extends AppCompatActivity  {
   Button save;
   ImageView edit;
   TextView logout,rateus,share;
   LinearLayout faq,profile;
    String[] COUNTRIES = new String[] {"English", "Tamil"};

    String token,note, dark,lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        save=(Button) findViewById(R.id.save);
        edit=(ImageView) findViewById(R.id.edit);
        logout=(TextView)findViewById(R.id.logout);
        rateus=(TextView)findViewById(R.id.rateus);
        share=(TextView)findViewById(R.id.share);
        faq=(LinearLayout) findViewById(R.id.faq);
        profile=(LinearLayout)findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Profile.this, Profileedit.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Profile.this.finish();
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Profile.this);
        String mytoken=sharedPreferences.getString("token","");
        System.out.println("token= "+mytoken);
        token=mytoken;
        note="No";
        dark="No";
        lang="English";
       // updateprofile();
        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMarket();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareit();

            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Profile.this, Faq.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Profile.this.finish();
            }
        });





        SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(Profile.this);
        String mytoken1=sharedPreferences1.getString("token","");
        System.out.println("token= "+mytoken1);
        getaddress(mytoken1);




        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);

                builder.setTitle("Logout Alert");
                builder.setMessage("Do you want to Logout? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Profile.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("key", "false");
                        editor.commit();


                        Intent a = new Intent(Profile.this, MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(a);
                        Profile.this.finish();
                    }
                });
                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                builder.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Profile.this, Profileedit.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Profile.this.finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Profile.this,Profile.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Profile.this.finish();
            }
        });
    }
    public void updateprofile() {
        try {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("notification",note);
            map.put("dark",dark);
            map.put("language",lang);
            JSONObject json = new JSONObject(map);
            VolleyTasks.makeVolleyPost( this,Constent.settings_details, json, "edit",token);

        }catch (Exception e){
            Toast.makeText(Profile.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    private void launchMarket() {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=rs.srilanka.news.rslanka");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
    public void shareit()
    {
        View view =  findViewById(R.id.lin);//your layout id
        view.getRootView();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            File picDir  = new File(Environment.getExternalStorageDirectory()+ "/myPic");
            if (!picDir.exists())
            {
                picDir.mkdir();
            }
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache(true);
            Bitmap bitmap = view.getDrawingCache();
//          Date date = new Date();
            String fileName = "mylove" + ".jpg";
            File picFile = new File(picDir + "/" + fileName);
            try
            {
                picFile.createNewFile();
                FileOutputStream picOut = new FileOutputStream(picFile);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), (int)(bitmap.getHeight()/1.2));
                boolean saved = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, picOut);
                if (saved)
                {
                 //   Toast.makeText(getApplicationContext(), "Image saved to your device Pictures "+ "directory!", Toast.LENGTH_SHORT).show();
                } else
                {
                    //Error
                }
                picOut.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            view.destroyDrawingCache();

            // share via intent
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("image/jpeg");
            sharingIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=rs.srilanka.news.rslanka");//your Image Url
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(picFile.getAbsolutePath()));
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else {
            //Error

        }
    }    @Override
    public void onBackPressed() {
        Intent a = new Intent(Profile.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Profile.this.finish();
        super.onBackPressed();
    }

    public void getaddress(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.settings, "settingss",token);

        }catch (Exception e){
            Toast.makeText(Profile.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }


//    @Override
//    public void handleResult(String method_name, JSONObject response) {
//        if (method_name.equals("settingss")) {
//            try {
//                String status = response.getString("message");
//                //  Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();
//
//                int code = response.getInt("code");
//                if(code==200){
//                    // Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();
//
//                    String output = response.getString("output");
//                    JSONArray jsonArr = new JSONArray(output);
//                    ArrayList<String> myurls=new ArrayList<>();
//
//                    for (int i = 0; i < jsonArr.length(); i++)
//                    {
//                        JSONObject jsonObj = jsonArr.getJSONObject(0);
//                        String notification= jsonObj.getString("notification");
//                        if(notification.equals("Yes")){
//                            Notification.setChecked(true);
//                        }else {
//                            Notification.setChecked(false);
//                        }
//
//                        String dark=jsonObj.getString("dark");
//                        if(dark.equals("Yes")){
//                            Dark.setChecked(true);
//                        }else {
//                            Dark.setChecked(false);
//                        }
//                        String language=jsonObj.getString("language");
//                        if(language.equals("English")){
//                            spinner.setItems("English","Select Language","Tamil");
//
//                        }
//                        if(language.equals("Tamil")){
//                            spinner.setItems("Tamil","English","Select Language");
//
//                        }
//                        if(language.equals("Select Language")){
//                            spinner.setItems("Select Language","Tamil","English");
//
//                        }
//
//                    }
//
//
//                    //test
//
//                    //  slider.setAdapter(new MainSliderAdapter());
//
//                }else {
//
//                    String error = response.getString("error");
//                    //Toast.makeText(Profile.this, error, Toast.LENGTH_SHORT).show();
//
//                }
//             //   String error = response.getString("error");
//
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        }else         if (method_name.equals("edit")) {
//            try {
//                String status = response.getString("message");
//                  Toast.makeText(Profile.this, status, Toast.LENGTH_SHORT).show();
//
//                int code = response.getInt("code");
//                if(code==200){
//                    // Toast.makeText(Dashboard.this, status, Toast.LENGTH_SHORT).show();
//
//
//
//
//
//                    //test
//
//                    //  slider.setAdapter(new MainSliderAdapter());
//
//                }else {
//
//                    String error = response.getString("error");
//                    //Toast.makeText(Profile.this, error, Toast.LENGTH_SHORT).show();
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
//    }

//    @Override
//    public void handleError(VolleyError e) {
//
//    }
}