package com.example.doctorapp.ui.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doctorapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Code = 101;
    private ImageButton current_location;
    private ArrayList<MapDataModel> dataModel;
    private ViewPager viewPager;
    private int no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        current_location = view.findViewById(R.id.current_location);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setPadding(0, 0, 300, 0);
        dataModel = new ArrayList<>();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        GetLocation();


        current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLocation();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                no = position;
                MapDataModel mapDataModel = dataModel.get(position);
                double lat = mapDataModel.getLat();
                double log = mapDataModel.getLag();
                String name1 = mapDataModel.getDoctorname();
                LatLng latLng = new LatLng(lat, log);
                CurrentLocation(latLng, name1);
            }
        });

        Load_Data();
        return view;

    }

    private void Load_Data() {
        dataModel.add(new MapDataModel("https://cdn.sanity.io/images/0vv8moc6/hcplive/0ebb6a8f0c2850697532805d09d4ff10e838a74b-200x200.jpg","Dr B Murali Krishna",
                "Address: Flat 204, Rajapushpam Palace, Shirdi Sai Road, Seethammadhara," +
                        " Seethammadhara, Visakhapatnam, Andhra Pradesh 530013",17.746224, 83.320373));

        dataModel.add(new MapDataModel("https://st2.depositphotos.com/3889193/7657/i/950/depositphotos_76570869-stock-photo-confident-female-doctor-posing-in.jpg",
                "Dr. A. Gopal Rao", "Address: HB Colony Rd, Seethammadhara Junction, KRM Colony, Seethammadara, Visakhapatnam, Andhra Pradesh 530013",
                17.744947, 83.318719));

        dataModel.add(new MapDataModel("https://images.theconversation.com/files/304957/original/file-20191203-66986-im7o5.jpg",
                "Dr Chandra Kala Devi", "Address: 50-52-15/21, Seethammadhara, Seethammadhara, Visakhapatnam, Andhra Pradesh 530013",
                17.745969, 83.327179));

        dataModel.add(new MapDataModel("https://st2.depositphotos.com/3889193/7657/i/950/depositphotos_76570869-stock-photo-confident-female-doctor-posing-in.jpg",
                "Dr Someshwara Rao N", "Address: HB Colony Rd, Seethammadhara Junction, KRM Colony, Seethammadara, Visakhapatnam, Andhra Pradesh 530013",
                17.744027, 83.324538));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), dataModel);
        viewPager.setAdapter(viewPagerAdapter);

    }

    private void GetLocation() {

        Toast.makeText(getContext(), "Lat: " ,Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Code);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location !=null){
                    currentLocation = location;
                    Toast.makeText(getContext(), "Lat: "+currentLocation.getLatitude()
                            +" Longitude:"+ currentLocation.getLongitude() ,Toast.LENGTH_LONG).show();
                    LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    CurrentLocation(latLng, "I am here...");

                }
            }
        });
    }

    private void CurrentLocation(final LatLng latLngs, final String name) {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLngs));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs, 18));
                int height = 100;
                int width = 100;
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.marker);
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
                mMap.addMarker(new MarkerOptions().position(latLngs).title(name).icon(smallMarkerIcon));
            }
        });

        // ====================================

        // ====================================
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem item=menu.findItem(R.id.notification);
        if(item!=null)
            item.setVisible(false);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Code :
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    GetLocation();
                }
                break;
        }
    }



}