package com.example.mototaxi.signup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mototaxi.BuildConfig;
import com.example.mototaxi.R;

import java.util.ArrayList;
import java.util.List;

public class Sinupscreen extends AppCompatActivity {
    TextView appversion;
    Button submit;

    final List<String> permissionsNeeded = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinupscreen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        appversion=(TextView) findViewById(R.id.appversionsu);
        submit=(Button)findViewById(R.id.submit);
        appversion.setText("App Version"+" "+String.valueOf(BuildConfig.VERSION_NAME));
        permissionsNeeded.clear();
        permissionsNeeded.add(Manifest.permission.GET_ACCOUNTS);
        permissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        permissionsNeeded.add(Manifest.permission.READ_SMS);
        permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsNeeded.add(Manifest.permission.READ_PHONE_NUMBERS);
        permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(Sinupscreen.this, MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Sinupscreen.this.finish();
            }
        });


    }

    private boolean checkIfAlreadyhavePermission() {

        boolean fff = false;
        for (int i = 0; i < permissionsNeeded.size(); i++) {
            int result = ContextCompat.checkSelfPermission(this,permissionsNeeded.get(i));
            if (result == PackageManager.PERMISSION_GRANTED) {
                fff=true;
            } else {
                fff=false;
            }
        }
        return   fff;
    }


    private void requestForSpecificPermission() {
        String[] namesArr = permissionsNeeded.toArray(new String[permissionsNeeded.size()]);

        ActivityCompat.requestPermissions(this,namesArr, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                   // submit.setEnabled(true);
                } else {
                   // submit.setEnabled(false);
                   // Toast.makeText(Sinupscreen.this, "Please allow the prtmissons", Toast.LENGTH_SHORT).show();
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    }

