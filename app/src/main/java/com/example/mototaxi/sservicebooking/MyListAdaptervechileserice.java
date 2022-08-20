package com.example.mototaxi.sservicebooking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mototaxi.R;
import com.example.mototaxi.vechicledata.MyListDatavechile;

import java.util.ArrayList;

public class MyListAdaptervechileserice extends RecyclerView.Adapter<MyListAdaptervechileserice.ViewHolder> {
    private ArrayList<MyListDatavechile> listdata;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdaptervechileserice(ArrayList<MyListDatavechile> listdata, Context context) {
        this.listdata = listdata;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.vechilelist, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyListDatavechile myListData = listdata.get(position);
        holder.model.setText(myListData.getModel());
        holder.number.setText(myListData.getNumber());
        holder.name.setText(myListData.getName());
        holder.primery.setVisibility(View.INVISIBLE);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v,holder);
            }
        });

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a2 = new Intent(context,   Maplocation.class);
                a2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(a2);

            }
        });

//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show();
//            }
//        });
    }
    private void showPopup(View v,ViewHolder viewHolder) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.option_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_7:
                        viewHolder.primery.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.page_8:
                        viewHolder.primery.setVisibility(View.INVISIBLE);
                        return true;
                    default:
                        return true;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView model,number,name,textView;
        public LinearLayout relativeLayout;
        public RelativeLayout primery;
        public LinearLayout myyyy;
        public CardView main;

        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.text);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.model = (TextView) itemView.findViewById(R.id.model);
            this.number = (TextView) itemView.findViewById(R.id.number);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.main=itemView.findViewById(R.id.main);
            myyyy=itemView.findViewById(R.id.myyyy);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
            primery=(RelativeLayout) itemView.findViewById(R.id.primery);
        }
    }

}




