package com.example.mototaxi.sservicebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mototaxi.R;

import java.util.ArrayList;

class CourseModelserice extends ArrayAdapter<CourseModelsericenee> {
    public CourseModelserice(Context context, ArrayList<CourseModelsericenee> courseModelArrayList) {
//        this.context=context;
//       this.courseModelArrayList=courseModelArrayList;
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.service_item1, parent, false);
        }
        CourseModelsericenee courseModel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.idTVCourse);
        LinearLayout layout1=listitemView.findViewById(R.id.layout);
//        layout1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                courseTV.setBackgroundResource(R.drawable.backround);
//            }
//        });

        courseTV.setText(courseModel.getCourse_name());
        return listitemView;
    }
}