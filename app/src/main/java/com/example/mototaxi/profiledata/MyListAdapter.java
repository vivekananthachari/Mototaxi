package com.example.mototaxi.profiledata;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mototaxi.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyListAdapter  extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    private ArrayList<MyListData> listdata;
    Context context;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;

    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<MyListData> listdata, Context context) {
        this.listdata = listdata;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyListData myListData = listdata.get(position);
        holder.textView.setText(myListData.getLabel());
        holder.textaddress.setText(myListData.getAddress());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(context, MapsActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                a.putExtra("type","edit");
                a.putExtra("auid",listdata.get(holder.getAdapterPosition()).getAuuid());
                a.putExtra("label",listdata.get(holder.getAdapterPosition()).getLabel());
                a.putExtra("address",listdata.get(holder.getAdapterPosition()).getAddress());
                a.putExtra("lat",listdata.get(holder.getAdapterPosition()).getLat());
                a.putExtra("long",listdata.get(holder.getAdapterPosition()).getLongs());
                context.startActivity(a);
          // showDialog(context,listdata.get(holder.getAdapterPosition()).getTitle(),listdata.get(holder.getAdapterPosition()).getAddress(),holder,holder.getAdapterPosition());
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Delete Alert");
                builder.setMessage("Do you want to Delete this Item? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("key", "false");
//                        editor.commit();
                        deleteItem(holder.getAdapterPosition());
                    }
                });
                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Dashboard.super.onBackPressed();
                    }
                });
                builder.show();

            }
        });
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    public void showDialog(Context activity, String title,String address,ViewHolder holder,int pos){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        EditText text = (EditText) dialog.findViewById(R.id.title);
        text.setText(title);
        EditText addressnew = (EditText) dialog.findViewById(R.id.address);
        addressnew.setText(address);
        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
        addressnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation(holder);
                }





            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textaddress.setText(addressnew.getText().toString());
                holder.textView.setText(text.getText().toString());
                dialog.dismiss();
            }
        });
        Button dialogButton1 = (Button) dialog.findViewById(R.id.discard);
        dialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.90);


        dialog.getWindow().setLayout(width, 1099);

        dialog.show();

    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation(ViewHolder holder) {

            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                getgetLocationAddress(context,lat,longi,holder);
           } else {
                Toast.makeText(context, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }

    }
    public  void getgetLocationAddress(Context context,double lat,double lng,ViewHolder holder){
        Geocoder geocoder;
        List<Address> addresses;

        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            // System.out.println("SDK_DATA"+address+"..."+city +country);
            holder.textaddress.setText(address);
            //Here address set to your textview
        } catch (IOException e) {
            e.printStackTrace();
        } }
    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView,textaddress,edit,delete;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.edit = (TextView) itemView.findViewById(R.id.edit);
            this.delete = (TextView) itemView.findViewById(R.id.delete);
            textaddress=itemView.findViewById(R.id.textaddress);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }

    private void deleteItem(int position) {
        listdata.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listdata.size());
        //holder.itemView.setVisibility(View.GONE);
    }
}


