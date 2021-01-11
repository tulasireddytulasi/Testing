package com.example.doctorapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.doctorapp.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView imageView, img1, img2, img3;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        imageView = root.findViewById(R.id.profile_pic);
        img1 = root.findViewById(R.id.img1);
        img3 = root.findViewById(R.id.img3);

        Glide.with(getContext())
                .load(R.drawable.profile_pic)
                .into(imageView);

        Glide.with(getContext())
                .load(R.drawable.card)
                .into(img1);

        Glide.with(getContext())
                .load(R.drawable.water)
                .into(img3);

        return root;
    }

//    @Override
//    public void onBackPressed() {
//        //finish();
//        // finishAffinity();
//        getActivity().moveTaskToBack(true);
//    }



    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }
}