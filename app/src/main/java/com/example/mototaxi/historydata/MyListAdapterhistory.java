package com.example.mototaxi.historydata;

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

public class MyListAdapterhistory extends RecyclerView.Adapter<MyListAdapterhistory.ViewHolder> {
    private ArrayList<MyListDatahistory> listdata;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdapterhistory(ArrayList<MyListDatahistory> listdata, Context context) {
        this.listdata = listdata;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_itemhistory, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyListDatahistory myListData = listdata.get(position);

        holder.textmodel.setText(myListData.getTextmodel());
        holder.rate.setText(myListData.getRate());
        holder.textaddress.setText(myListData.getTextaddress());
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
        public TextView rate,textmodel,textaddress;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);

            this.textmodel = (TextView) itemView.findViewById(R.id.textmodel);
            this.rate=(TextView)itemView.findViewById(R.id.rate) ;
            textaddress=itemView.findViewById(R.id.txtaddress);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }

}



