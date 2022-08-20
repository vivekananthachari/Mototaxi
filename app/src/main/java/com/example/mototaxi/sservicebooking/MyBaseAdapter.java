package com.example.mototaxi.sservicebooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.example.mototaxi.R;

import java.util.ArrayList;

public class MyBaseAdapter extends BaseAdapter {

    public Context ba_context;
    public ArrayList<ListRowItem> listitem = new ArrayList<>();
    public LayoutInflater inflater;
    ListRowItem currentlistitem;

    public MyBaseAdapter(Context ma_context, ArrayList<ListRowItem> ma_listitem) {
        super();
        this.ba_context = ma_context;
        this.listitem = ma_listitem;

        inflater = (LayoutInflater) ba_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.listitem.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.listview_item_layout, parent, false);

        CheckBox carrier = (CheckBox) vi.findViewById(R.id.checkbox);


        currentlistitem = listitem.get(position);

        String str_carrier = currentlistitem.getCarrier();


        carrier.setText(str_carrier);


        return vi;
    }
}
