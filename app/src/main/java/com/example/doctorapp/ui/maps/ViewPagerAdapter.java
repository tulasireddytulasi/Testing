package com.example.doctorapp.ui.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.doctorapp.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<MapDataModel> datamodels;
    Context context;


    ViewPagerAdapter(Context context, ArrayList<MapDataModel> datamodels){
        this.context = context;
        this.datamodels = datamodels;
    }

    @Override
    public int getCount() {
        return datamodels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.viewpager_item_layout, container, false);
        ImageView imageView = view.findViewById(R.id.doc_img);

        TextView titlename = view.findViewById(R.id.doctor_name);
        TextView address = view.findViewById(R.id.address);

        titlename.setText(datamodels.get(position).getDoctorname());
        address.setText(datamodels.get(position).getAddress());

        Glide.with(context)
                .load(datamodels.get(position).getPic())
                .circleCrop()
                .into(imageView);


        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
