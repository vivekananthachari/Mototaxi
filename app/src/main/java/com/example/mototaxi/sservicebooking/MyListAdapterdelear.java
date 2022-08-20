package com.example.mototaxi.sservicebooking;

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

import java.util.ArrayList;

public class MyListAdapterdelear extends RecyclerView.Adapter<MyListAdapterdelear.ViewHolder> {
    private ArrayList<Delarlocation.Mydata> listdata;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdapterdelear(ArrayList<Delarlocation.Mydata> listdata, Context context) {
        this.listdata = listdata;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.delarlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Delarlocation.Mydata myListData = listdata.get(position);
        holder.shopname.setText(myListData.getShop_name());
        holder.distance.setText(myListData.getDistance()+" kms");
        holder.rating.setText("Rating : "+myListData.getRating());
        holder.ruppy.setText(myListData.getRuppe()+" INR");
        holder.imageView.setImageResource(myListData.getImgid());


        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(context, Newlistofshpos.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(a);

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
        public TextView shopname,distance,rating,ruppy;
        public LinearLayout relativeLayout;

        public  CardView main;

        public ViewHolder(View itemView) {
            super(itemView);
           this.shopname=(TextView) itemView.findViewById(R.id.shopname);
            this.imageView = (ImageView) itemView.findViewById(R.id.img);
            this.distance = (TextView) itemView.findViewById(R.id.distance);
            this.rating = (TextView) itemView.findViewById(R.id.rating);
            this.ruppy = (TextView) itemView.findViewById(R.id.ruppy);
            this.main=itemView.findViewById(R.id.main);



        }
    }

}




