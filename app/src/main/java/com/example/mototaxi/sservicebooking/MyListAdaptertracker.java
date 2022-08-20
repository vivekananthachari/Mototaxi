package com.example.mototaxi.sservicebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mototaxi.R;

import java.util.ArrayList;

public class MyListAdaptertracker extends RecyclerView.Adapter<MyListAdaptertracker.ViewHolder> {
    private ArrayList<Service_tracking.Mydata> listdata;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdaptertracker(ArrayList<Service_tracking.Mydata> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public MyListAdaptertracker.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.tackerstaus, parent, false);
        MyListAdaptertracker.ViewHolder viewHolder = new MyListAdaptertracker.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyListAdaptertracker.ViewHolder holder, int position) {
        final Service_tracking.Mydata myListData = listdata.get(position);
        holder.shopname.setText(myListData.getTitle());
        holder.distance.setText(myListData.getStaus());
        holder.myyyy.setBackgroundResource(R.drawable.bordernew);
        if(position==1){
            holder.myyyy.setBackgroundResource(R.drawable.borderorange);
        }
        holder.myyyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent a = new Intent(context, Newlistofshpos.class);
//                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(a);

                // Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView shopname, distance;
        public LinearLayout relativeLayout;
        LinearLayout myyyy;

        public CardView main;

        public ViewHolder(View itemView) {
            super(itemView);
            this.shopname = (TextView) itemView.findViewById(R.id.shopname);
            this.imageView = (ImageView) itemView.findViewById(R.id.img);
            this.distance = (TextView) itemView.findViewById(R.id.distance);
            this.main = itemView.findViewById(R.id.main);

            this.myyyy=itemView.findViewById(R.id.myyyy);


        }
    }
}