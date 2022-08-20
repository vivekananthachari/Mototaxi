package com.example.mototaxi.teackdata;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
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

import androidx.recyclerview.widget.RecyclerView;

import com.example.mototaxi.R;
import com.example.mototaxi.vechicledata.MyListDatavechile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MyListAdaptervechilehorizonaltrack extends RecyclerView.Adapter<MyListAdaptervechilehorizonaltrack.ViewHolder> {
    private ArrayList<MyListDatavechile> listdata;
    Context context;

    // RecyclerView recyclerView;
    public MyListAdaptervechilehorizonaltrack(ArrayList<MyListDatavechile> listdata, Context context) {
        this.listdata = listdata;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.vechilelisthorizonal, parent, false);
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

//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show();
//            }
//        });
    }
    private void showPopup(View v, ViewHolder viewHolder) {
        PopupMenu popup = new PopupMenu(context, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.option_menunewww, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_7:
                        //viewHolder.primery.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.page_8:
                        shareit(viewHolder);
                       // viewHolder.primery.setVisibility(View.INVISIBLE);
                        return true;
                    default:
                        return true;
                }
            }
        });
    }
    public void shareit(ViewHolder holder)
    {
        View view =  holder.textView.findViewById(R.id.text);
        view.getRootView();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            File picDir  = new File(Environment.getExternalStorageDirectory()+ "/myPic");
            if (!picDir.exists())
            {
                picDir.mkdir();
            }
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache(true);
            Bitmap bitmap = view.getDrawingCache();
//          Date date = new Date();
            String fileName = "mylove" + ".jpg";
            File picFile = new File(picDir + "/" + fileName);
            try
            {
                picFile.createNewFile();
                FileOutputStream picOut = new FileOutputStream(picFile);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), (int)(bitmap.getHeight()/1.2));
                boolean saved = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, picOut);
                if (saved)
                {
                    //   Toast.makeText(getApplicationContext(), "Image saved to your device Pictures "+ "directory!", Toast.LENGTH_SHORT).show();
                } else
                {
                    //Error
                }
                picOut.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            view.destroyDrawingCache();

            // share via intent
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("image/jpeg");
            sharingIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=rs.srilanka.news.rslanka");//your Image Url
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(picFile.getAbsolutePath()));
            context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else {
            //Error

        }
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


