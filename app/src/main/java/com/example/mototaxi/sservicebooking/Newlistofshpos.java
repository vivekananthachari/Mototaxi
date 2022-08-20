package com.example.mototaxi.sservicebooking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.mototaxi.R;

import java.util.ArrayList;

public class Newlistofshpos extends AppCompatActivity {
    GridView coursesGV;
    GridView tyrelist;
    GridView repairlist;
    GridView vechlist;
    GridView bodyshoplisy;
    LinearLayout checkup,oil_service,washing,tyre,rapire,vech,bodyshop;
    int previousSelectedPosition=0 ;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newlistofshpos);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        coursesGV = findViewById(R.id.idGVcourses);
        tyrelist = findViewById(R.id.tyrelist);
        repairlist = findViewById(R.id.repairlist);
        vechlist = findViewById(R.id.vechlist);
        bodyshoplisy = findViewById(R.id.bodyshoplisy);

        coursesGV.setVisibility(View.GONE);
        tyrelist.setVisibility(View.GONE);
        repairlist.setVisibility(View.GONE);
        vechlist.setVisibility(View.GONE);
        bodyshoplisy.setVisibility(View.GONE);


        checkup=findViewById(R.id.checkup);
        oil_service=findViewById(R.id.oil_service);
        washing=findViewById(R.id.washing);
        tyre=findViewById(R.id.tyre);
        rapire=findViewById(R.id.rapire);
        vech=findViewById(R.id.vech);
        bodyshop=findViewById(R.id.bodyshop);
        bodyshop.setVisibility(View.GONE);
        save=findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Newlistofshpos.this, Servicebooking.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(a);
                Newlistofshpos.this.finish();
            }
        });

        bodyshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkup.setBackgroundResource(R.drawable.bordernewdta);
                oil_service.setBackgroundResource(R.drawable.bordernewdta);
                washing.setBackgroundResource(R.drawable.bordernewdta);
                tyre.setBackgroundResource(R.drawable.bordernewdta);
                rapire.setBackgroundResource(R.drawable.bordernewdta);
                vech.setBackgroundResource(R.drawable.bordernewdta);
                bodyshop.setBackgroundResource(R.drawable.backround);

                coursesGV.setVisibility(View.GONE);
                tyrelist.setVisibility(View.GONE);
                repairlist.setVisibility(View.GONE);
                vechlist.setVisibility(View.GONE);
                bodyshoplisy.setVisibility(View.VISIBLE);

            }
        });
        vech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkup.setBackgroundResource(R.drawable.bordernewdta);
                oil_service.setBackgroundResource(R.drawable.bordernewdta);
                washing.setBackgroundResource(R.drawable.bordernewdta);
                tyre.setBackgroundResource(R.drawable.bordernewdta);
                rapire.setBackgroundResource(R.drawable.bordernewdta);
                vech.setBackgroundResource(R.drawable.backround);
                bodyshop.setBackgroundResource(R.drawable.bordernewdta);

                coursesGV.setVisibility(View.GONE);
                tyrelist.setVisibility(View.GONE);
                repairlist.setVisibility(View.GONE);
                vechlist.setVisibility(View.VISIBLE);
                bodyshoplisy.setVisibility(View.GONE);

            }
        });
        checkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkup.setBackgroundResource(R.drawable.backround);
                oil_service.setBackgroundResource(R.drawable.bordernewdta);
                washing.setBackgroundResource(R.drawable.bordernewdta);
                tyre.setBackgroundResource(R.drawable.bordernewdta);
                rapire.setBackgroundResource(R.drawable.bordernewdta);
                vech.setBackgroundResource(R.drawable.bordernewdta);
                bodyshop.setBackgroundResource(R.drawable.bordernewdta);

            }
        });

        oil_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkup.setBackgroundResource(R.drawable.bordernewdta);
                oil_service.setBackgroundResource(R.drawable.backround);
                washing.setBackgroundResource(R.drawable.bordernewdta);
                tyre.setBackgroundResource(R.drawable.bordernewdta);
                rapire.setBackgroundResource(R.drawable.bordernewdta);
                vech.setBackgroundResource(R.drawable.bordernewdta);
                bodyshop.setBackgroundResource(R.drawable.bordernewdta);

            }
        });

        washing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkup.setBackgroundResource(R.drawable.bordernewdta);
                oil_service.setBackgroundResource(R.drawable.bordernewdta);
                washing.setBackgroundResource(R.drawable.backround);
                tyre.setBackgroundResource(R.drawable.bordernewdta);
                rapire.setBackgroundResource(R.drawable.bordernewdta);
                vech.setBackgroundResource(R.drawable.bordernewdta);
                bodyshop.setBackgroundResource(R.drawable.bordernewdta);

                coursesGV.setVisibility(View.VISIBLE);
                tyrelist.setVisibility(View.GONE);
                repairlist.setVisibility(View.GONE);
                vechlist.setVisibility(View.GONE);
                bodyshoplisy.setVisibility(View.GONE);

            }
        });

        tyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkup.setBackgroundResource(R.drawable.bordernewdta);
                oil_service.setBackgroundResource(R.drawable.bordernewdta);
                washing.setBackgroundResource(R.drawable.bordernewdta);
                tyre.setBackgroundResource(R.drawable.backround);
                rapire.setBackgroundResource(R.drawable.bordernewdta);
                vech.setBackgroundResource(R.drawable.bordernewdta);
                bodyshop.setBackgroundResource(R.drawable.bordernewdta);
                coursesGV.setVisibility(View.GONE);
                tyrelist.setVisibility(View.VISIBLE);
                repairlist.setVisibility(View.GONE);
                vechlist.setVisibility(View.GONE);
                bodyshoplisy.setVisibility(View.GONE);
            }
        });

        rapire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkup.setBackgroundResource(R.drawable.bordernewdta);
                oil_service.setBackgroundResource(R.drawable.bordernewdta);
                washing.setBackgroundResource(R.drawable.bordernewdta);
                tyre.setBackgroundResource(R.drawable.bordernewdta);
                rapire.setBackgroundResource(R.drawable.backround);
                vech.setBackgroundResource(R.drawable.bordernewdta);
                bodyshop.setBackgroundResource(R.drawable.bordernewdta);
                coursesGV.setVisibility(View.GONE);
                tyrelist.setVisibility(View.GONE);
                repairlist.setVisibility(View.VISIBLE);
                vechlist.setVisibility(View.GONE);
                bodyshoplisy.setVisibility(View.GONE);

            }
        });
        ArrayList<CourseModelsericenee> courseModelArrayList = new ArrayList<CourseModelsericenee>();
        courseModelArrayList.add(new CourseModelsericenee("Washing"));
        courseModelArrayList.add(new CourseModelsericenee("Polishing"));
        courseModelArrayList.add(new CourseModelsericenee("Coating"));

        CourseModelserice adapter = new CourseModelserice(this, courseModelArrayList);
        coursesGV.setAdapter(adapter);
        coursesGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View currentPosition = coursesGV.getChildAt(position);

                if (previousSelectedPosition != -1) {
                    View previousPos = coursesGV.getChildAt(previousSelectedPosition);
                    previousPos.setBackgroundResource(R.drawable.bordernewdta);
                }
                currentPosition.setBackgroundResource(R.drawable.backround);
                previousSelectedPosition = position;


            }
        });




        ArrayList<CourseModelsericenee> courseModelArrayList1 = new ArrayList<CourseModelsericenee>();
        courseModelArrayList1.add(new CourseModelsericenee("Puncture"));
        courseModelArrayList1.add(new CourseModelsericenee("Balancing"));
        courseModelArrayList1.add(new CourseModelsericenee("Alignment"));

        CourseModelserice adapter1 = new CourseModelserice(this, courseModelArrayList1);
        tyrelist.setAdapter(adapter1);
        tyrelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){



                }
            }
        });





        ArrayList<CourseModelsericenee> courseModelArrayList2 = new ArrayList<CourseModelsericenee>();
        courseModelArrayList2.add(new CourseModelsericenee("Engine"));
        courseModelArrayList2.add(new CourseModelsericenee("Clutch"));
        courseModelArrayList2.add(new CourseModelsericenee("Break"));
        courseModelArrayList2.add(new CourseModelsericenee("Suspension"));
        courseModelArrayList2.add(new CourseModelsericenee("Electrical"));
        courseModelArrayList2.add(new CourseModelsericenee("Others"));
        CourseModelserice adapter2 = new CourseModelserice(this, courseModelArrayList2);
        repairlist.setAdapter(adapter2);
        repairlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){



                }
            }
        });


        ArrayList<CourseModelsericenee> courseModelArrayList3 = new ArrayList<CourseModelsericenee>();
        courseModelArrayList3.add(new CourseModelsericenee("2-Wheeler"));
        courseModelArrayList3.add(new CourseModelsericenee("4-Wheeler"));
        CourseModelserice adapter3 = new CourseModelserice(this, courseModelArrayList3);
        vechlist.setAdapter(adapter3);
        vechlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                 bodyshop.setVisibility(View.GONE);

                }
                if(position==1){
                    bodyshop.setVisibility(View.VISIBLE);

                }
            }
        });

        ArrayList<CourseModelsericenee> courseModelArrayList4 = new ArrayList<CourseModelsericenee>();
        courseModelArrayList4.add(new CourseModelsericenee("Paint"));
        courseModelArrayList4.add(new CourseModelsericenee("Dent"));
        courseModelArrayList4.add(new CourseModelsericenee("Damage"));
        CourseModelserice adapter4 = new CourseModelserice(this, courseModelArrayList4);
        bodyshoplisy.setAdapter(adapter4);
        bodyshoplisy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){


                }
            }
        });
    }
}