package com.example.mototaxi.fual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mototaxi.R;

import java.util.ArrayList;

public class MyListAdapterfualdata extends RecyclerView.Adapter<MyListAdapterfualdata.ViewHolder> {
    private ArrayList<MyListDatafualnew> listdata;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdapterfualdata(ArrayList<MyListDatafualnew> listdata, Context context) {
        this.listdata = listdata;
        this.context=context;
    }

    @Override
    public MyListAdapterfualdata.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.listmydata, parent, false);
        MyListAdapterfualdata.ViewHolder viewHolder = new MyListAdapterfualdata.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyListAdapterfualdata.ViewHolder holder, int position) {
        final MyListDatafualnew myListData = listdata.get(position);
        holder.model.setText(myListData.getDate());
        holder.number.setText("\u20B9 "+myListData.getDiesel());
        holder.name.setText(myListData.getState());
        holder.Day.setText("\u20B9 "+myListData.getPetrol());


//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView model,number,name,textView,Day;
        public LinearLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.text);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.model = (TextView) itemView.findViewById(R.id.model);
            this.number = (TextView) itemView.findViewById(R.id.number);
            this.name = (TextView) itemView.findViewById(R.id.name);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
            this.Day=itemView.findViewById(R.id.day);

        }
    }

}






