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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements MapInteface{

    private GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Code = 101;
    private ImageButton current_location;
    private ArrayList<MapDataModel> dataModel;
  //  private ViewPager viewPager;
    private ChipGroup chipGroup;
   // private ViewPagerAdapter viewPagerAdapter;
    private EditText editText;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyAdapter2 myAdapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        current_location = view.findViewById(R.id.current_location);
        editText = view.findViewById(R.id.searchbar);
      //  viewPager = view.findViewById(R.id.viewpager);
        chipGroup = view.findViewById(R.id.chipGroup);
        dataModel = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
      //  viewPager.setPadding(0, 0, 300, 0);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        GetLocation();

        current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLocation();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0){
                  //  viewPagerAdapter.getFilter().filter(s.toString().toLowerCase());
                    myAdapter2.getFilter().filter(s.toString().toLowerCase());
                }else {
                    DoctorsList();
                }

            }
        });

//        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
//            @Override
//            public void onPageSelected(int position) {
//                MapDataModel mapDataModel = dataModel.get(position);
//                double lat = mapDataModel.getLat();
//                double log = mapDataModel.getLag();
//                String name1 = mapDataModel.getDoctorname();
//                LatLng latLng = new LatLng(lat, log);
//                CurrentLocation(latLng, name1);
//            }
//        });

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                switch (i){
                    case R.id.chip : DoctorsList();
                        break;
                    case R.id.chip2: HospitalsLit();
                        break;
                    case R.id.chip3: LabsList();
                        break;
                    case R.id.chip4: dataModel.clear();
                        break;
                    case R.id.chip5: dataModel.clear();
                        break;
                    default: DoctorsList();
                             break;
                }
            }
        });
        DoctorsList();
        return view;

    }

    private void DoctorsList() {
        dataModel.clear();
        dataModel.add(new MapDataModel("https://cdn.sanity.io/images/0vv8moc6/hcplive/0ebb6a8f0c2850697532805d09d4ff10e838a74b-200x200.jpg","Dr B Murali Krishna",
                "MBBS, MD (MRCP) (FRCP) Raditaion oncologist",
                "Address: Flat 204, Rajapushpam Palace, Shirdi Sai Road, Seethammadhara," +
                        " Seethammadhara, Visakhapatnam, Andhra Pradesh 530013",17.746224, 83.320373));

        dataModel.add(new MapDataModel("https://st2.depositphotos.com/3889193/7657/i/950/depositphotos_76570869-stock-photo-confident-female-doctor-posing-in.jpg",
                "Dr. A. Gopal Rao",
                "MBBS, MD (MRCP) (FRCP) Raditaion oncologist",
                "Address: HB Colony Rd, Seethammadhara Junction, KRM Colony, Seethammadara, Visakhapatnam, Andhra Pradesh 530013",
                17.744947, 83.318719));

        dataModel.add(new MapDataModel("https://images.theconversation.com/files/304957/original/file-20191203-66986-im7o5.jpg",
                "Dr Chandra Kala Devi",
                "MBBS, MD (MRCP) (FRCP) Raditaion oncologist",
                "Address: 50-52-15/21, Seethammadhara, Seethammadhara, Visakhapatnam, Andhra Pradesh 530013",
                17.745969, 83.327179));

        dataModel.add(new MapDataModel("https://st2.depositphotos.com/3889193/7657/i/950/depositphotos_76570869-stock-photo-confident-female-doctor-posing-in.jpg",
                "Dr. P. Sudha Malini",
                "MBBS, MD (MRCP) (FRCP) Raditaion oncologist",
                "Address: D.No.55-14-101, HB Colony Rd, Hill View Doctors Colony, Seethammadara, Visakhapatnam, Andhra Pradesh 530013\n",
                17.7437817, 83.3211354));

       // viewPagerAdapter = new ViewPagerAdapter(getContext(), dataModel);
       // viewPager.setAdapter(viewPagerAdapter);

        myAdapter2 = new MyAdapter2(getContext(), dataModel, this);
        recyclerView.setAdapter(myAdapter2);

    }

    private void HospitalsLit() {
        dataModel.clear();
        dataModel.add(new MapDataModel("https://lh5.googleusercontent.com/p/AF1QipO517GTzGpogdIbcSiTLOqNHSO53COhlhUjf3QW=s1031-k-no","Apollo Hospitals Ramnagar Vizag",
                "",
                "Address: Door No 10, Executive Court, 50-80, Waltair Main Rd, opp. Daspalla, Ram Nagar, Visakhapatnam, Andhra Pradesh 530002",17.746224, 83.320373));

        dataModel.add(new MapDataModel("https://www.medicoverhospitals.in/wp-content/uploads/2019/09/simhapuri-hospitals.jpg",
                "Medicover Hospitals Vizag | Best Hospitals in Vizag", "",
                "Address: 15-2-9, Gokhale Rd, Krishna Nagar, Maharani Peta, Visakhapatnam, Andhra Pradesh 530002",
                17.744947, 83.318719));

        dataModel.add(new MapDataModel("https://content.jdmagicbox.com/comp/visakhapatnam/dc/0891p8924.8924.090807100027.w3b3dc/catalogue/mgr-hospitals-pedawaltair-visakhapatnam-hospitals-tn4s8jgvhl.jpg",
                "MGR Hospital", "",
                "Address: 8-4-32, Doctors Colony, Near Visakha Eye Hospital, Pedawaltair, Visakhapatnam, Andhra Pradesh 530017",
                17.745969, 83.327179));

        dataModel.add(new MapDataModel("https://www.medicoverhospitals.in/wp-content/uploads/2019/09/simhapuri-hospitals.jpg",
                "M.V.P. Hospital", "",
                "Address: Plot No 6, Sector 7, Near MVP Rythu Bazaar, MVP Colony, Visakhapatnam, Andhra Pradesh 530017",
                17.746817,83.330776));

        // viewPagerAdapter = new ViewPagerAdapter(getContext(), dataModel);
        // viewPager.setAdapter(viewPagerAdapter);

        myAdapter2 = new MyAdapter2(getContext(), dataModel, this);
        recyclerView.setAdapter(myAdapter2);
    }

    private void LabsList() {
        dataModel.clear();
        dataModel.add(new MapDataModel("https://marvelwallpapers.000webhostapp.com/upload/lab1.jpg",
                "Sri Sadguru Medical Lab",
                "",
                "Address: Shop No: 43-35-28/1, Akkayyapalem Main Rd, Near Baroda Bank, Akkayyapalem," +
                        " Visakhapatnam, Andhra Pradesh 530016",17.746224, 83.320373));

        dataModel.add(new MapDataModel("https://marvelwallpapers.000webhostapp.com/upload/lab2.jpg",
                "Sri Sai Clinical Lab",
                "",
                "Address: Shop No.55-14-102, HB Colony Rd, Near Venkateswara Swamy Temple,Near P & T Colony, Hill View Doctors Colony, Seethammadara, Visakhapatnam, Andhra Pradesh 530013\n",
                17.744947, 83.318719));

        dataModel.add(new MapDataModel("https://marvelwallpapers.000webhostapp.com/upload/lab3.jpg",
                "Mamata Laboratory",
                "",
                "Address: Rajeev Nagar, Shivaji Nagar, Rajiv Palem, Maddilapalem, Visakhapatnam, Andhra Pradesh 530003",
                17.745969, 83.327179));

        dataModel.add(new MapDataModel("https://marvelwallpapers.000webhostapp.com/upload/lab3.jpg",
                "TRIMS NANDI",
                "",
                "Address: 9-7-8/1, Sivajipalem Rd, Shivaji Palem, Pithapuram Colony, Maddilapalem, Visakhapatnam, Andhra Pradesh 530017",
                17.7437817, 83.3211354));

        myAdapter2 = new MyAdapter2(getContext(), dataModel, this);
        recyclerView.setAdapter(myAdapter2);

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

    @Override
    public void MapInterface(final MapDataModel dataModel, MyAdapter2.MyViewHolder holder, int position) {

        Glide.with(getContext())
                .load(dataModel.getPic())
                .circleCrop()
                .into(holder.imageView);

        holder.name.setText(dataModel.getDoctorname());
        holder.address.setText(dataModel.getAddress());
        holder.designation.setText(dataModel.getDesignation());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(dataModel.getLat(), dataModel.getLag());
                CurrentLocation(latLng, dataModel.getDoctorname());
            }
        });

    }

}