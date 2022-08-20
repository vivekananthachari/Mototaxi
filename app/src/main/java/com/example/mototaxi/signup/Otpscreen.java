package com.example.mototaxi.signup;

import static android.view.KeyEvent.KEYCODE_DEL;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.R;
import com.example.mototaxi.encryptapp.DarKnight;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Otpscreen extends AppCompatActivity implements VolleyTasksListener  {
 TextView phonenubernew;
    TextView val;

    Button enterotp;
    AppCompatEditText[] otpETs;
    ImageView imgback;
    String type,uniqueId,userMob,otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpscreen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        val=(TextView) findViewById(R.id.val);

        reverseTimer(60,val);
        phonenubernew=(TextView) findViewById(R.id.phonenubernew);
        imgback=(ImageView)findViewById(R.id.imgback) ;

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Otpscreen.this, MainActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Otpscreen.this.finish();
            }
        });
        enterotp=(Button)findViewById(R.id.enterotp);
        enterotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logi1();

            }
        });
        Intent intent=new Intent(getIntent());
        if(intent!=null){
           String bbbb= intent.getExtras().get("number").toString();
             userMob= intent.getExtras().get("number").toString();
            uniqueId = intent.getExtras().get("uuid").toString();
            type="manual";
            String mask = PhoneNumberWithoutCountryCode(bbbb).replaceAll("\\w(?=\\w{4})", "*");

            phonenubernew.setText(mask);
        }


         otpETs = new AppCompatEditText[4];


        otpETs[0] = findViewById(R.id.otpET1);
        otpETs[1] = findViewById(R.id.otpET2);
        otpETs[2] = findViewById(R.id.otpET3);
        otpETs[3] = findViewById(R.id.otpET4);


    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == 7 || keyCode == 8 ||
                keyCode == 9 || keyCode == 10 ||
                keyCode == 11 || keyCode == 12 ||
                keyCode == 13 || keyCode == 14 ||
                keyCode == 15 || keyCode == 16 ||
                keyCode == 67) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KEYCODE_DEL) {
                    int index = checkWhoHasFocus();
                    if (index != 123) {
                        if (Helpers.rS(otpETs[index]).equals("")) {
                            if (index != 0) {
                                otpETs[index - 1].requestFocus();
                            }
                        } else {
                            return super.dispatchKeyEvent(event);
                        }
                    }
                } else {
                    int index = checkWhoHasFocus();
                    if (index != 123) {
                        if (Helpers.rS(otpETs[index]).equals("")) {
                            return super.dispatchKeyEvent(event);
                        } else {
                            if (index != 5) {
                                otpETs[index + 1].requestFocus();
                            }
                        }
                    }
                    return super.dispatchKeyEvent(event);
                }
            }
        } else {
            return super.dispatchKeyEvent(event);
        }
        return true;
    }

    private int checkWhoHasFocus() {
        for (int i = 0; i < otpETs.length; i++) {
            EditText tempET = otpETs[i];
            if (tempET.hasFocus()) {
                return i;
            }
        }
        return 123;
    }

    public void reverseTimer(int Seconds,final TextView tv){

        new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText("EXPIRY TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                tv.setText("Resend SMS");
            }
        }.start();
    }
    public static String PhoneNumberWithoutCountryCode(String phoneNumberWithCountryCode){//+91 7698989898
        Pattern compile = Pattern.compile("\\+(?:998|996|995|994|993|992|977|976|975|974|973|972|971|970|968|967|966|965|964|963|962|961|960|886|880|856|855|853|852|850|692|691|690|689|688|687|686|685|683|682|681|680|679|678|677|676|675|674|673|672|670|599|598|597|595|593|592|591|590|509|508|507|506|505|504|503|502|501|500|423|421|420|389|387|386|385|383|382|381|380|379|378|377|376|375|374|373|372|371|370|359|358|357|356|355|354|353|352|351|350|299|298|297|291|290|269|268|267|266|265|264|263|262|261|260|258|257|256|255|254|253|252|251|250|249|248|246|245|244|243|242|241|240|239|238|237|236|235|234|233|232|231|230|229|228|227|226|225|224|223|222|221|220|218|216|213|212|211|98|95|94|93|92|91|90|86|84|82|81|66|65|64|63|62|61|60|58|57|56|55|54|53|52|51|49|48|47|46|45|44\\D?1624|44\\D?1534|44\\D?1481|44|43|41|40|39|36|34|33|32|31|30|27|20|7|1\\D?939|1\\D?876|1\\D?869|1\\D?868|1\\D?849|1\\D?829|1\\D?809|1\\D?787|1\\D?784|1\\D?767|1\\D?758|1\\D?721|1\\D?684|1\\D?671|1\\D?670|1\\D?664|1\\D?649|1\\D?473|1\\D?441|1\\D?345|1\\D?340|1\\D?284|1\\D?268|1\\D?264|1\\D?246|1\\D?242|1)\\D?");
        String number = phoneNumberWithCountryCode.replaceAll(compile.pattern(), "");
        //Log.e(tag, "number::_>" +  number);//OutPut::7698989898
        return number;
    }

    public void logi1() {
        try {
            userMob=userMob.replace("+91","");
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("mobilenumber",userMob);
            map.put("buuid",uniqueId);
            int myNum = Integer.parseInt( otpETs[0].getText().toString()+otpETs[1].getText().toString()+otpETs[2].getText().toString()+otpETs[3].getText().toString());
           int myNum1=myNum+1;
            String strI = String.valueOf(myNum1);

            String myopt1= DarKnight.getEncrypted(strI);
            System.out.println(myopt1);
            map.put("otpnumber",myopt1);
            map.put("type",type);
            map.put("login_type","customer");
            JSONObject json = new JSONObject(map);
            VolleyTasks.makeVolleyPost(this, Constent.otp, json, "Login1","");

        }catch (Exception e){
            Toast.makeText(Otpscreen.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleResult(String method_name, JSONObject response) {
        if (response != null) {


            if (method_name.equals("Login1")) {
                try {
                    String status = response.getString("message");
                    Toast.makeText(Otpscreen.this, status, Toast.LENGTH_SHORT).show();

                    int code = response.getInt("code");
                   // String otp = response.getString("otp");
                    // 16 bytes IV

                    // String myopt= Java_AES_Cipher.decrypt(Constent.key,otp);
                    // String myopt= Sandbox.decrypt(Constent.key,Constent.initVector,otp);
                   // String myopt= DarKnight.getDecrypted(otp);
                   // System.out.println(myopt);
                   // String myopt1= DarKnight.getEncrypted("1870");
                   // System.out.println(myopt1);

                    String token=response.getString("token");
                    if(token.equals("")){

                         Toast.makeText(Otpscreen.this, "Invalid token", Toast.LENGTH_SHORT).show();
                    }else {

                        launchHome(type,token);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

    }



    @Override
    public void handleError(VolleyError e) {
        Toast.makeText(Otpscreen.this, e.toString(), Toast.LENGTH_SHORT).show();

    }

    public  void launchHome(String trueProfile,String token){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Otpscreen.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", "true");
        editor.putString("token",token);
        editor.commit();
        Toast.makeText(Otpscreen.this, "OTP VERIFIED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
        Intent a = new Intent(Otpscreen.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Otpscreen.this.finish();
    }
}