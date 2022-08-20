package com.example.mototaxi.notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mototaxi.R;
import com.example.mototaxi.sservicebooking.Delarlocation;

import com.example.mototaxi.sservicebooking.Newlistofshpos;

import java.util.ArrayList;

public class MyListAdapternoti extends RecyclerView.Adapter<MyListAdapternoti.ViewHolder> {
    private ArrayList<MyListDatanoft> listdata;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdapternoti(ArrayList<MyListDatanoft> listdata, Context context) {
        this.listdata = listdata;
        this.context=context;
    }

    @Override
    public MyListAdapternoti.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.notificationlist, parent, false);
        MyListAdapternoti.ViewHolder viewHolder = new MyListAdapternoti.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyListAdapternoti.ViewHolder holder, int position) {
        final MyListDatanoft myListData = listdata.get(position);
        holder.shopname.setText(myListData.getModel());


//        holder.main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent a = new Intent(context, Newlistofshpos.class);
//                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(a);
//
//                // Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView shopname;


        public CardView main;

        public ViewHolder(View itemView) {
            super(itemView);
            this.shopname=(TextView) itemView.findViewById(R.id.shopname);



        }
    }

}




