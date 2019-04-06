package com.logistic.logic.e_saloon.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.logistic.logic.e_saloon.R;

import java.util.ArrayList;

public class MyImageSlidderAdapter extends PagerAdapter {

    Context context;
    private ArrayList<Integer> images;
    private LayoutInflater inflater;

    public MyImageSlidderAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       View myImageLayout =inflater.inflate(R.layout.image_slider_layout
               ,container,false);
        ImageView myImage=myImageLayout.findViewById(R.id.image);
       myImage.setImageResource(images.get(position));
        container.addView(myImageLayout,0);
        return  myImageLayout;


    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }
}
