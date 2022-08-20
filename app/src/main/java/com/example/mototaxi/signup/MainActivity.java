package com.example.mototaxi.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.R;
import com.example.mototaxi.encryptapp.DarKnight;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements VolleyTasksListener {
    private static final String TAG = MainActivity.class.getSimpleName();
  EditText phonenuber;
  Button otp;
  TextView agree;
    String userMob,uniqueId,type;
    //APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        otp=(Button)findViewById(R.id.otp);
        phonenuber=(EditText) findViewById(R.id.phonenuber);
        agree=findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             getaddress("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJNb3RvU2VydmljZSIsImF1ZCI6IjEuMSIsImlhdCI6MTY0OTUzNjc5NiwibmJmIjoxNjQ5NTM2ODA2LCJleHAiOjE2NDk1NDAzOTYsImRhdGEiOnsiaWQiOiI3NDc3N2QyZS05NjM3LTQ2MzUtOTM5My0xM2UyMGNkNmZjMjRcbiIsInR5cGUiOiJ0cnVlY2FsbGVyIiwibG9naW5fdHlwZSI6ImN1c3RvbWVyIn19.VzWUaXbjGR42_oG56I_oI2-36DmNsoe8jDe7gvjtc8wrMKiSJdW3qFUleNEzrb28_r4XzdLalt-fZKsThFvJWA");
            }
        });
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phonenuber.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter the Mobile Number", Toast.LENGTH_SHORT).show();
                }else {
                    if(userMob==null){
                        userMob= phonenuber.getText().toString();
                    }else {
                        userMob= phonenuber.getText().toString();
                    }

                    if(isValidMobile(userMob)==true){
                        type="manual";
                        apicallnew();
                    }else {
                        Toast.makeText(MainActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

        phonenuber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                HintRequest hintRequest = new HintRequest.Builder()
                        .setPhoneNumberIdentifierSupported(true)
                        .build();

                PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest);
                try {
                    startIntentSenderForResult(intent.getIntentSender(), 1008, null, 0, 0, 0, null);
                } catch (IntentSender.SendIntentException e) {
                    Log.e("", "Could not start hint picker Intent", e);
                }
                return true;
            }
        });

//        phonenuber.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        (findViewById(R.id.login_with_truecaller)).setOnClickListener((View v) -> {
            //check if TrueCaller SDk is usable
            if(TruecallerSDK.getInstance().isUsable()){
                TruecallerSDK.getInstance().getUserProfile( MainActivity.this);
            }else{

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setMessage("Truecaller App not installed.");

                dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
                            Log.d(TAG, "onClick: Closing dialog");

                            dialog.dismiss();
                        }
                );

                dialogBuilder.setIcon(R.drawable.com_truecaller_icon);
                dialogBuilder.setTitle(" ");

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });

        // customisation of TrueCaller function like color , text can be done here
        TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(this, sdkCallback)
                .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
                .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
                .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
                .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_USE)
                .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
                .privacyPolicyUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
                .termsOfServiceUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
                .footerType(TruecallerSdkScope.FOOTER_TYPE_NONE)
                .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN)
                .build();
        TruecallerSDK.init(trueScope);
    }
    private final ITrueCallback sdkCallback = new ITrueCallback() {

        @Override
        public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {
            Log.i(TAG, trueProfile.firstName + " " + trueProfile.lastName);

                userMob=trueProfile.phoneNumber;
                type="truecaller";
                apicallnew();



        }

        @Override
        public void onFailureProfileShared(@NonNull final TrueError trueError) {
            Log.i(TAG, trueError.toString());

        }

        @Override
        public void onVerificationRequired(@Nullable final TrueError trueError) {
            Log.i(TAG, "onVerificationRequired");
        }

    };


    public void  apicallnew(){
        uniqueId = UUID.randomUUID().toString();

        try {
                    logi1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

//    public  void  apicall() throws JSONException {
//
//        apiInterface = APIClient.getClient().create(APIInterface.class);
//        Gson gson = new Gson();
//        userMob=userMob.replace("+91","");
//         LoginData loginData=new LoginData(userMob,uniqueId,type);
//        String json = gson.toJson(loginData);
//        JSONObject obj = new JSONObject(json);
//
//        Call<UserList> call3 = apiInterface.doCreateUserWithField(obj);
//        call3.enqueue(new Callback<UserList>() {
//            @Override
//            public void onResponse(Call<UserList> call, Response<UserList> response) {
//                 //  UserList userList = response.body();
//                // String text = userList.token;
//                Toast.makeText(getApplicationContext(), response.toString() , Toast.LENGTH_SHORT).show();
//
//              //  launchHome(type);
//
//            }
//
//            @Override
//            public void onFailure(Call<UserList> call, Throwable t) {
//                System.out.println("ttt"+t.toString());
//                call.cancel();
//            }
//        });
//
//    }

    public void getaddress(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.tandc, "privasy",token);

        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }
    private boolean isValidMobile(String phone) {

          String bb = phone.replace("+91","");


        if (bb.length()!=10) {
            return false;
        } else {
            boolean val=android.util.Patterns.PHONE.matcher(bb).matches();
            return val;
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
            TruecallerSDK.getInstance().onActivityResultObtained(this, requestCode, resultCode, data);
        }


        switch (requestCode) {
            case 1008:
                if (resultCode == RESULT_OK) {
                    Credential cred = data.getParcelableExtra(Credential.EXTRA_KEY);
//                    cred.getId====: ====+919*******
                    Log.e("cred.getId", cred.getId());
                    userMob = cred.getId();
                    phonenuber.setText(userMob);


                } else {
                    // Sim Card not found!
                    Log.e("cred.getId", "1008 else");

                    return;
                }


                break;
        }


    }

    private void launchHome(String trueProfile,String token) {
         if(trueProfile.equals("manual")){
             SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
             SharedPreferences.Editor editor = sharedPreferences.edit();
             editor.putString("token",token);
             editor.commit();
             Intent a = new Intent(MainActivity.this, Otpscreen.class);
             a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             a.putExtra("number",userMob);
             a.putExtra("uuid",uniqueId);
             startActivity(a);
             MainActivity.this.finish();
         }else if(trueProfile.equals("truecaller")){
             SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
             SharedPreferences.Editor editor = sharedPreferences.edit();
             editor.putString("key", "true");
             editor.putString("token",token);
             editor.commit();
             startActivity(new Intent(getApplicationContext(), Dashboard.class)
                     .putExtra("profile", "yyyy"));
             finish();
             MainActivity.this.finish();
         }



    }

    public void showDialog(Context activity, String title, String address,ArrayList<String>myulsss){
        final Dialog dialog = new Dialog(activity);

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogprivacy);
        CheckBox checkBox=(CheckBox) dialog.findViewById(R.id.check);


        TextView textView1=dialog.findViewById(R.id.text3);
       // checkBox.setChecked(true);
        textView1.setText(myulsss.get(1).toString());
        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(checkBox.isChecked()){
                        dialogButton.setEnabled(true);
                    }else {
                        dialogButton.setEnabled(false);
                    }
                }
            });

        }
        // ImageView a=(ImageView)dialog.findViewById(R.id.a);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);


        dialog.getWindow().setLayout(width, 700);
        dialog.show();

    }

    public void logi1() {
        try {
            userMob=userMob.replace("+91","");
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("mobilenumber",userMob);
            map.put("buuid",uniqueId);
            map.put("type",type);
            map.put("login_type","customer");
            JSONObject json = new JSONObject(map);
            VolleyTasks.makeVolleyPost(this, Constent.register, json, "Login","");

        }catch (Exception e){
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleResult(String method_name, JSONObject response) {
        if (response != null) {


            if (method_name.equals("Login")) {
                try {
                    String status = response.getString("message");
                    Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();

                    int code = response.getInt("code");
                    String otp = response.getString("otp");
                     // 16 bytes IV

                  // String myopt= Java_AES_Cipher.decrypt(Constent.key,otp);
                   // String myopt= Sandbox.decrypt(Constent.key,Constent.initVector,otp);
                    String myopt= DarKnight.getDecrypted(otp);
                     System.out.println(myopt);
                    String myopt1= DarKnight.getEncrypted("1870");
                    System.out.println(myopt1);

                    String token=response.getString("token");
                    if(token.equals("")){
                        launchHome(type,token);
                       // Toast.makeText(MainActivity.this, "Invalid token", Toast.LENGTH_SHORT).show();
                    }else {
                        launchHome(type,token);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else              if (method_name.equals("privasy")) {
                try {
                    String status = response.getString("message");
                   // Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();

                    int code = response.getInt("code");
                    if(code==200){
                        String output = response.getString("output");
                        JSONArray jsonArr = new JSONArray(output);
                        ArrayList<String> myurls=new ArrayList<>();
                        myurls.clear();
                        for (int i = 0; i < jsonArr.length(); i++)
                        {
                            JSONObject jsonObj = jsonArr.getJSONObject(0);
                            String terms= jsonObj.getString("terms");
                            myurls.add(terms);


                        }
                        showDialog(MainActivity.this,"Privasy and Policy ","",myurls);

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
        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

    }
}
