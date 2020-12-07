package com.example.doctorapp.ui.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> implements Filterable {

    private Context context;
    private ArrayList<MapDataModel> mapDataModels;
    private ArrayList<MapDataModel> doctorslist;
    private MapInteface mapInteface;

    public MyAdapter2(Context context, ArrayList<MapDataModel> mapDataModels, MapInteface mapInteface) {
        this.context = context;
        this.mapDataModels = mapDataModels;
        this.mapInteface = mapInteface;
        this.doctorslist = new ArrayList<>(mapDataModels);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MapDataModel model = mapDataModels.get(position);
//        Glide.with(context)
//                .load(model.getPic())
//                .circleCrop()
//                .into(holder.imageView);
//        holder.name.setText(model.getDoctorname());
//        holder.address.setText(model.getAddress());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, model.getDoctorname(), Toast.LENGTH_SHORT).show();
//            }
//        });

        if (!model.equals(null)){
            mapInteface.MapInterface(model, holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return mapDataModels.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String search = constraint.toString().toLowerCase();
            List<MapDataModel> filterlist = new ArrayList<>();

            if (search.isEmpty() || search.length() == 0){
              filterlist.addAll(doctorslist);
            }else{
                for (MapDataModel doctorlist: doctorslist){
                  if(doctorlist.getDoctorname().toLowerCase().contains(search)){
                     filterlist.add(doctorlist);
                  }
                }
            }


            FilterResults filterResults = new FilterResults();
            filterResults.values = filterlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

           mapDataModels.clear();
           mapDataModels.addAll((Collection<? extends MapDataModel>) results.values);
           notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView name, designation, address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cards);
            imageView = itemView.findViewById(R.id.doc_img);
            name = itemView.findViewById(R.id.doctor_name);
            address = itemView.findViewById(R.id.address);
            designation = itemView.findViewById(R.id.designation);
        }
    }
}
