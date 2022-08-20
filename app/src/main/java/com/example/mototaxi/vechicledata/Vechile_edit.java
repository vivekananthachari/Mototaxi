package com.example.mototaxi.vechicledata;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mototaxi.R;
import com.example.mototaxi.analytics.Analtic;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.dashboaddata.CourseModel;
import com.example.mototaxi.dashboaddata.Dashboard;
import com.example.mototaxi.historydata.history;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Vechile_edit extends AppCompatActivity implements VolleyTasksListener {
    RecyclerView vechilerecyclier;
    ArrayList<Vechile.Vech> myListDatanew = new ArrayList() ;
    LinearLayout edit_vecharrow;
    ImageView edit_vech;
    BottomNavigationView navigation;
     TextView vech1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vechile_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        navigation = findViewById(R.id.bottom_navigation);
        vech1=findViewById(R.id.vech1);
        Intent intent=new Intent(getIntent());
        if(intent!=null){
            String bbbb= intent.getExtras().get("num").toString();
            if(bbbb.equals("")){

            }else {
                vech1.setText(bbbb);
            }

        }
        navigation.getMenu().getItem(1).setChecked(true);
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener);
        edit_vech=findViewById(R.id.edit_vech);
        edit_vecharrow=findViewById(R.id.edit_vecharrow);
       GridView listitem1=findViewById(R.id.listitem1);
        ArrayList<CourseModel> courseModelArrayList1 = new ArrayList<CourseModel>();
        courseModelArrayList1.add(new CourseModel("RC", R.drawable.rc,null,null));
        courseModelArrayList1.add(new CourseModel("Challan", R.drawable.challandetails,null,null));
        courseModelArrayList1.add(new CourseModel("licence", R.drawable.license,null,null));
        CourseGVAdaptervechnew adapter2 = new CourseGVAdaptervechnew(this, courseModelArrayList1);
        listitem1.setAdapter(adapter2);

        vechilerecyclier=(RecyclerView) findViewById(R.id.vechilerecyclier);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Vechile_edit.this);
        vechilerecyclier=(RecyclerView) findViewById(R.id.vechilerecyclier);
        String mytoken=sharedPreferences.getString("token","");
        System.out.println("token= "+mytoken);
        getaddress(mytoken);
    }


    public void getaddress(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.vehiclelist, "privasy",token);

        }catch (Exception e){
            Toast.makeText(Vechile_edit.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void handleResult(String method_name, JSONObject response) {
        if (method_name.equals("privasy")) {
            try {

                Vechile.Vech vech=new Vechile.Vech();

                String status = response.getString("message");

                vech.setMessage(status);
                // Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                vech.setCode(code);


                if(code==200){
                    String output = response.getString("output");
                    JSONArray jsonArr = new JSONArray(output);
                    ArrayList<Vechile.Output> myurls=new ArrayList<>();
                    for (int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String id= jsonObj.getString("id");
                        String uuid= jsonObj.getString("uuid");
                        String Description= jsonObj.getString("Description");
                        String RegistrationYear= jsonObj.getString("RegistrationYear");
                        String vehicle_no= jsonObj.getString("vehicle_no");
                        String CarMake= jsonObj.getString("CarMake");
                        String CarModel= jsonObj.getString("CarModel");
                        String Variant= jsonObj.getString("Variant");
                        String EngineSize= jsonObj.getString("EngineSize");
                        String MakeDescription= jsonObj.getString("MakeDescription");
                        String ModelDescription= jsonObj.getString("ModelDescription");
                        String NumberOfSeats= jsonObj.getString("NumberOfSeats");

                        String VechileIdentificationNumber= jsonObj.getString("VechileIdentificationNumber");

                        String EngineNumber= jsonObj.getString("EngineNumber");
                        String FuelType= jsonObj.getString("FuelType");
                        String RegistrationDate= jsonObj.getString("RegistrationDate");
                        String Owner= jsonObj.getString("Owner");
                        String Insurance= jsonObj.getString("Insurance");
                        String Fitness= jsonObj.getString("Fitness");
                        String PUCC= jsonObj.getString("PUCC");
                        String VehicleType= jsonObj.getString("VehicleType");
                        String Location= jsonObj.getString("Location");
                        String ImageUrl= jsonObj.getString("ImageUrl");
                        String status1= jsonObj.getString("status");
                        String customer= jsonObj.getString("customer");
                        String datetime= jsonObj.getString("datetime");
                        String is_primary=jsonObj.getString("is_primary");

                        Vechile.Output output1=new Vechile.Output();
                        output1.setId(id);
                        output1.setUuid(uuid);
                        output1.setDescription(Description);
                        output1.setRegistrationYear(RegistrationYear);
                        output1.setVehicle_no(vehicle_no);
                        output1.setCarMake(CarMake);
                        output1.setCarModel(CarModel);
                        output1.setVariant(Variant);
                        output1.setEngineSize(EngineSize);
                        output1.setMakeDescription(MakeDescription);
                        output1.setModelDescription(ModelDescription);
                        output1.setNumberOfSeats(NumberOfSeats);

                        output1.setVechileIdentificationNumber(VechileIdentificationNumber);
                        output1.setEngineNumber(EngineNumber);
                        output1.setFuelType(FuelType);
                        output1.setRegistrationDate(RegistrationDate);
                        output1.setOwner(Owner);
                        output1.setInsurance(Insurance);
                        output1.setFitness(Fitness);
                        output1.setPUCC(PUCC);
                        output1.setVehicleType(VehicleType);
                        output1.setLocation(Location);
                        output1.setImageUrl(ImageUrl);
                        output1.setStatus(status1);
                        output1.setCustomer(customer);
                        output1.setDatetime(datetime);
                        output1.setIs_primary(is_primary);



                        myurls.add(output1);


                    }

                    vech.setOutput(myurls);
                    myListDatanew.add(vech);

                    MyListAdaptervechile adapter = new MyListAdaptervechile(myListDatanew, Vechile_edit.this);
                    vechilerecyclier.setHasFixedSize(true);
                    vechilerecyclier.setLayoutManager(new LinearLayoutManager(this));
                    vechilerecyclier.setAdapter(adapter);



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


    @Override
    public void onBackPressed() {
        Intent a = new Intent(Vechile_edit.this, Vechile.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Vechile_edit.this.finish();
        super.onBackPressed();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    Intent a = new Intent(Vechile_edit.this,   Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Vechile_edit.this.finish();

                    // start activity 1
                    return true;
                case R.id.page_2:
                    Intent a2 = new Intent(Vechile_edit.this,   Vechile.class);
                    a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a2);
                    Vechile_edit.this.finish();

                    // start activity 2
                    return true;
                case R.id.page_3:
                    Intent a1 = new Intent(Vechile_edit.this, history.class);
                    a1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a1);
                    Vechile_edit.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_4:
                    // start activity 2
                    Intent a3 = new Intent(Vechile_edit.this, Analtic.class);
                    a3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a3);
                    Vechile_edit.this.finish();

                    return true;

                default:
                    //default intent
                    Intent a5 = new Intent(Vechile_edit.this,   Dashboard.class);
                    a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a5);
                    Vechile_edit.this.finish();


                    return true;
            }

            // return false;

        }


    };

}