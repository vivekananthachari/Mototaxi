package com.example.mototaxi.vechicledata;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.mototaxi.R;
import com.example.mototaxi.apidata.Constent;
import com.example.mototaxi.volley.VolleyTasks;
import com.example.mototaxi.volley.VolleyTasksListener;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyListAdaptervechile extends RecyclerView.Adapter<MyListAdaptervechile.ViewHolder> implements VolleyTasksListener {
    private ArrayList<Vechile.Vech> listdata;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdaptervechile(ArrayList<Vechile.Vech> listdata, Context context) {
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
        final Vechile.Vech myListData = listdata.get(position);
        holder.model.setText(myListData.getOutput().get(position).getMakeDescription());
        holder.number.setText(myListData.getOutput().get(position).getVehicle_no());
        holder.name.setText(myListData.getOutput().get(position).getCustomer());

        if(myListData.getOutput().get(position).getIs_primary().equals("y")){
            holder.primery.setVisibility(View.VISIBLE);
        }else  if(myListData.getOutput().get(position).getIs_primary().equals("n")){
            holder.primery.setVisibility(View.INVISIBLE);
        }
       // holder.primery.setVisibility(View.INVISIBLE);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String mytoken=sharedPreferences.getString("token","");
                System.out.println("token= "+mytoken);
                showPopup(v,holder,mytoken,myListData.getOutput().get(position).getVehicle_no());
            }
        });

//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show();
//            }
//        });
    }
    private void showPopup(View v,ViewHolder viewHolder,String token,String vechno) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.option_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_7:

                        try {
                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("vehile",vechno);

                            JSONObject json = new JSONObject(map);
                            VolleyTasks.makeVolleyPostrecy(context, Constent.vehicle_update, json, "vechno3",token);
                            viewHolder.primery.setVisibility(View.VISIBLE);
                        }catch (Exception e){
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                        }


                        return true;
                    case R.id.page_8:

                        try {
                            HashMap<String, String> map = new HashMap<String, String>();

                            map.put("vehile",vechno);

                            JSONObject json = new JSONObject(map);
                            VolleyTasks.makeVolleyPostrecyde(context, Constent.vehicle_delete, json, "vechno3",token);
                            viewHolder.primery.setVisibility(View.INVISIBLE);
                        }catch (Exception e){
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                        }

                        return true;
                    default:
                        return true;
                }
            }
        });
    }


    @Override
    public void handleResult(String method_name, JSONObject response) {

    }

    @Override
    public void handleError(VolleyError e) {

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

        public ViewHolder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.text);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.model = (TextView) itemView.findViewById(R.id.model);
            this.number = (TextView) itemView.findViewById(R.id.number);
            this.name = (TextView) itemView.findViewById(R.id.name);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
            primery=(RelativeLayout) itemView.findViewById(R.id.primery);
        }
    }

}




