package com.example.mototaxi.vechicledata;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mototaxi.R;
import com.example.mototaxi.dashboaddata.CourseModel;

import java.util.ArrayList;

public class CourseGVAdaptervech extends ArrayAdapter<CourseModel> {
    public CourseGVAdaptervech(@NonNull Context context, ArrayList<CourseModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item2, parent, false);
        }
        CourseModel courseModel = getItem(position);
        RelativeLayout lineat=listitemView.findViewById(R.id.lineat);
        TextView idTVCourse2 = listitemView.findViewById(R.id.idTVCourse2);
        TextView idTVCourse1 = listitemView.findViewById(R.id.idTVCourse1);
        ImageView idIVcourse = listitemView.findViewById(R.id.idIVcourse);
        idTVCourse2.setText(courseModel.getCourse_name());
        idTVCourse1.setText(courseModel.getCount());
        lineat.setBackgroundColor(Color.parseColor(courseModel.getBacgroundcolor()));
        idIVcourse.setImageResource(courseModel.getImgid());
        return listitemView;
    }
}
