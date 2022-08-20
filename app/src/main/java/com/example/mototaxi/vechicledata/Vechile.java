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
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.HashMap;

public class Vechile extends AppCompatActivity implements VolleyTasksListener {
   RecyclerView vechilerecyclier;
    ArrayList<Vech> myListDatanew = new ArrayList() ;
    LinearLayout edit_vecharrow;
    ImageView edit_vech;
    ImageView edit_vecharrowbut;
    GridView listitem,listitem1;
    BottomNavigationView navigation;
    EditText vech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vechile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        edit_vecharrowbut=findViewById(R.id.edit_vecharrowbut);
        navigation = findViewById(R.id.bottom_navigation);
        vech=findViewById(R.id.vech);
        navigation.getMenu().getItem(1).setChecked(true);
        navigation.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener);
        edit_vech=findViewById(R.id.edit_vech);
        edit_vecharrow=findViewById(R.id.edit_vecharrow);
        listitem=findViewById(R.id.listitem);
        listitem1=findViewById(R.id.listitem1);

        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();
        courseModelArrayList.add(new CourseModel("Challans", R.drawable.challan,"1","#2050e2"));
        courseModelArrayList.add(new CourseModel("Blacklist", R.drawable.blacklist,"0","#fe0a4e"));
        courseModelArrayList.add(new CourseModel("Insurance", R.drawable.insure,"0","#ff7a1e"));
        courseModelArrayList.add(new CourseModel("Road Tax", R.drawable.roadtax,"0","#8638fe"));
        courseModelArrayList.add(new CourseModel("Fitness", R.drawable.fitness,"0","#feb100"));
        courseModelArrayList.add(new CourseModel("Permit", R.drawable.permit,"0","#00d200"));
        ArrayList<CourseModel> courseModelArrayList1 = new ArrayList<CourseModel>();
        courseModelArrayList1.add(new CourseModel("RC", R.drawable.rc,null,null));
        courseModelArrayList1.add(new CourseModel("Challan", R.drawable.challandetails,null,null));
        courseModelArrayList1.add(new CourseModel("licence", R.drawable.license,null,null));

        CourseGVAdaptervech adapter1 = new CourseGVAdaptervech(this, courseModelArrayList);
        listitem.setAdapter(adapter1);
        CourseGVAdaptervechnew adapter2 = new CourseGVAdaptervechnew(this, courseModelArrayList1);
        listitem1.setAdapter(adapter2);
        edit_vecharrowbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vech.getText().toString().isEmpty()){
                    Toast.makeText(Vechile.this, "Please Enter Vehicle Number", Toast.LENGTH_SHORT).show();
                }else {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Vechile.this);
                    vechilerecyclier=(RecyclerView) findViewById(R.id.vechilerecyclier);
                    String mytoken=sharedPreferences.getString("token","");
                    System.out.println("token= "+mytoken);
                    vechadd(mytoken,vech.getText().toString());

                }
//                Intent a = new Intent(Vechile.this, Vechile_edit.class);
//                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(a);
//                Vechile.this.finish();
            }
        });

        edit_vech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Vechile.this, Vechile_edit.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                a.putExtra("num",vech.getText().toString());
                startActivity(a);
                Vechile.this.finish();




            }
        });

//        edit_vecharrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent a = new Intent(Vechile.this, Vechile_edit.class);
//                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                a.putExtra("num","");
//                startActivity(a);
//                Vechile.this.finish();
//            }
//        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Vechile.this);
        vechilerecyclier=(RecyclerView) findViewById(R.id.vechilerecyclier);
        String mytoken=sharedPreferences.getString("token","");
        System.out.println("token= "+mytoken);
        getaddress(mytoken);


    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Vechile.this, Dashboard.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(a);
        Vechile.this.finish();
        super.onBackPressed();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1:
                    Intent a = new Intent(Vechile.this,   Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a);
                    Vechile.this.finish();

                    // start activity 1
                    return true;
                case R.id.page_2:
                    Intent a2 = new Intent(Vechile.this,   Vechile.class);
                    a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a2);
                    Vechile.this.finish();

                    // start activity 2
                    return true;
                case R.id.page_3:
                    Intent a1 = new Intent(Vechile.this, history.class);
                    a1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a1);
                    Vechile.this.finish();
                    // start activity 2
                    return true;
                case R.id.page_4:
                    // start activity 2
                    Intent a3 = new Intent(Vechile.this, Analtic.class);
                    a3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a3);
                    Vechile.this.finish();

                    return true;

                default:
                    //default intent
                    Intent a5 = new Intent(Vechile.this,   Dashboard.class);
                    a5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(a5);
                    Vechile.this.finish();


                    return true;
            }

            // return false;

        }


    };

    public void vechadd(String token,String vechno) {
        try {
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("vehile_no",vechno);

            JSONObject json = new JSONObject(map);
            VolleyTasks.makeVolleyPost(this, Constent.vehicle_info, json, "vechno",token);

        }catch (Exception e){
            Toast.makeText(Vechile.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }


    public void getaddress(String token) {
        try {


            VolleyTasks.makeVolleyGETObject(this, Constent.vehiclelist, "privasy",token);

        }catch (Exception e){
            Toast.makeText(Vechile.this, e.toString(), Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void handleResult(String method_name, JSONObject response) {
        if (method_name.equals("privasy")) {
            try {

                Vech vech=new Vech();

                String status = response.getString("message");

                vech.setMessage(status);
                // Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                vech.setCode(code);


                if(code==200){
                    String output = response.getString("output");
                    JSONArray jsonArr = new JSONArray(output);
                    ArrayList<Output> myurls=new ArrayList<>();
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

                         Output output1=new Output();
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


                    MyListAdaptervechile adapter = new MyListAdaptervechile(myListDatanew, Vechile.this);
                    vechilerecyclier.setHasFixedSize(true);
                    vechilerecyclier.setLayoutManager(new LinearLayoutManager(this));
                    vechilerecyclier.setAdapter(adapter);



                }
                // 16 bytes IV




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        else          if (method_name.equals("vechno")) {
            try {
                String status = response.getString("message");
                String error = response.getString("error");
                // Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                if(code==200){

                    Toast.makeText(Vechile.this, status, Toast.LENGTH_SHORT).show();
                    Intent a = new Intent(Vechile.this, Vechile_edit.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    a.putExtra("num",vech.getText().toString());
                    startActivity(a);
                    Vechile.this.finish();

                }else {
                    Toast.makeText(Vechile.this, error, Toast.LENGTH_SHORT).show();
                }
                // 16 bytes IV




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        else                   if (method_name.equals("vechno3")) {
            try {
                String status = response.getString("message");

                // Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();

                int code = response.getInt("code");
                if(code==200){

                    Toast.makeText(Vechile.this, status, Toast.LENGTH_SHORT).show();
                }else {

                }
                // 16 bytes IV




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


    public static class Vech
    {
        private ArrayList<Output> output;

        public ArrayList<Output> getOutput() {
            return output;
        }

        public void setOutput(ArrayList<Output> output) {
            this.output = output;
        }

        private Integer code;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        private String message;




        public String getMessage ()
        {
            return message;
        }

        public void setMessage (String message)
        {
            this.message = message;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [output = "+output+", code = "+code+", message = "+message+"]";
        }
    }

    public static class Output
    {
        private String PUCC;

        private String Owner;

        private String Description;

        private String RegistrationDate;

        private String is_primary;

        private String uuid;

        private String FuelType;

        private String MakeDescription;

        private String datetime;

        private String EngineNumber;

        private String id;

        private String RegistrationYear;

        private String ModelDescription;

        private String EngineSize;

        private String vehicle_no;

        private String VehicleType;

        private String ImageUrl;

        private String CarMake;

        private String Variant;

        private String CarModel;

        private String NumberOfSeats;

        private String Insurance;

        private String VechileIdentificationNumber;

        private String Fitness;

        private String Location;

        private String status;

        private String customer;

        public String getPUCC ()
        {
            return PUCC;
        }

        public void setPUCC (String PUCC)
        {
            this.PUCC = PUCC;
        }

        public String getOwner ()
        {
            return Owner;
        }

        public void setOwner (String Owner)
        {
            this.Owner = Owner;
        }

        public String getDescription ()
        {
            return Description;
        }

        public void setDescription (String Description)
        {
            this.Description = Description;
        }

        public String getRegistrationDate ()
        {
            return RegistrationDate;
        }

        public void setRegistrationDate (String RegistrationDate)
        {
            this.RegistrationDate = RegistrationDate;
        }

        public String getIs_primary ()
        {
            return is_primary;
        }

        public void setIs_primary (String is_primary)
        {
            this.is_primary = is_primary;
        }

        public String getUuid ()
        {
            return uuid;
        }

        public void setUuid (String uuid)
        {
            this.uuid = uuid;
        }

        public String getFuelType ()
        {
            return FuelType;
        }

        public void setFuelType (String FuelType)
        {
            this.FuelType = FuelType;
        }

        public String getMakeDescription ()
        {
            return MakeDescription;
        }

        public void setMakeDescription (String MakeDescription)
        {
            this.MakeDescription = MakeDescription;
        }

        public String getDatetime ()
        {
            return datetime;
        }

        public void setDatetime (String datetime)
        {
            this.datetime = datetime;
        }

        public String getEngineNumber ()
        {
            return EngineNumber;
        }

        public void setEngineNumber (String EngineNumber)
        {
            this.EngineNumber = EngineNumber;
        }

        public String getId ()
        {
            return id;
        }

        public void setId (String id)
        {
            this.id = id;
        }

        public String getRegistrationYear ()
        {
            return RegistrationYear;
        }

        public void setRegistrationYear (String RegistrationYear)
        {
            this.RegistrationYear = RegistrationYear;
        }

        public String getModelDescription ()
        {
            return ModelDescription;
        }

        public void setModelDescription (String ModelDescription)
        {
            this.ModelDescription = ModelDescription;
        }

        public String getEngineSize ()
        {
            return EngineSize;
        }

        public void setEngineSize (String EngineSize)
        {
            this.EngineSize = EngineSize;
        }

        public String getVehicle_no ()
        {
            return vehicle_no;
        }

        public void setVehicle_no (String vehicle_no)
        {
            this.vehicle_no = vehicle_no;
        }

        public String getVehicleType ()
        {
            return VehicleType;
        }

        public void setVehicleType (String VehicleType)
        {
            this.VehicleType = VehicleType;
        }

        public String getImageUrl ()
        {
            return ImageUrl;
        }

        public void setImageUrl (String ImageUrl)
        {
            this.ImageUrl = ImageUrl;
        }

        public String getCarMake ()
        {
            return CarMake;
        }

        public void setCarMake (String CarMake)
        {
            this.CarMake = CarMake;
        }

        public String getVariant ()
        {
            return Variant;
        }

        public void setVariant (String Variant)
        {
            this.Variant = Variant;
        }

        public String getCarModel ()
        {
            return CarModel;
        }

        public void setCarModel (String CarModel)
        {
            this.CarModel = CarModel;
        }

        public String getNumberOfSeats ()
        {
            return NumberOfSeats;
        }

        public void setNumberOfSeats (String NumberOfSeats)
        {
            this.NumberOfSeats = NumberOfSeats;
        }

        public String getInsurance ()
        {
            return Insurance;
        }

        public void setInsurance (String Insurance)
        {
            this.Insurance = Insurance;
        }

        public String getVechileIdentificationNumber ()
        {
            return VechileIdentificationNumber;
        }

        public void setVechileIdentificationNumber (String VechileIdentificationNumber)
        {
            this.VechileIdentificationNumber = VechileIdentificationNumber;
        }

        public String getFitness ()
        {
            return Fitness;
        }

        public void setFitness (String Fitness)
        {
            this.Fitness = Fitness;
        }

        public String getLocation ()
        {
            return Location;
        }

        public void setLocation (String Location)
        {
            this.Location = Location;
        }

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        public String getCustomer ()
        {
            return customer;
        }

        public void setCustomer (String customer)
        {
            this.customer = customer;
        }


    }

    @Override
    public void handleError(VolleyError e) {

        Toast.makeText(Vechile.this, e.toString(), Toast.LENGTH_SHORT).show();

    }
}