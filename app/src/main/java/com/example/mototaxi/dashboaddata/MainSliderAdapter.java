package com.example.mototaxi.dashboaddata;

import java.util.ArrayList;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {
    ArrayList<String> url;

    public MainSliderAdapter(ArrayList<String> url) {
        this.url = url;

    }

    @Override
    public int getItemCount() {
        return url.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        viewHolder.bindImageSlide(url.get(position));

                
//        switch (position) {
//            case 0:
//                break;
//            case 1:
//                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg");
//                break;
//            case 2:
//                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
//                break;
//        }
    }
}
