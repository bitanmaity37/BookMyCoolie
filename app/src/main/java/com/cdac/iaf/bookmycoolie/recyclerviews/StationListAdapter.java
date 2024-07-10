package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.EditInventoryActivity;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.EditPlatformActivity;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.AddCoolieActivity;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.OpHomeActivity;
import com.cdac.iaf.bookmycoolie.models.Coolie;
import com.cdac.iaf.bookmycoolie.models.STATION.AllStationResponse;

import java.util.ArrayList;

public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.ViewHolder> {

    Context context;
    ArrayList<AllStationResponse> stations;

    public StationListAdapter(Context context, ArrayList<AllStationResponse> stations) {
        this.context = context;
        this.stations = stations;
    }

    @NonNull
    @Override
    public StationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stations, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StationListAdapter.ViewHolder holder, int position) {

        holder.stname.setText(stations.get(holder.getAdapterPosition()).getStationName());
        holder.stcode.setText(stations.get(holder.getAdapterPosition()).getStationCode());
        holder.countwc.setText(stations.get(holder.getAdapterPosition()).getNoOfWheelchairs().toString());
        holder.countcart.setText(stations.get(holder.getAdapterPosition()).getNoOfCarts().toString());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rv_station_child.setLayoutManager(linearLayoutManager);
        StationChildAdapter stationChildAdapter = new StationChildAdapter(context, stations.get(holder.getAdapterPosition()).getAreaMasterMappingModels(), false);
        holder.rv_station_child.setAdapter(stationChildAdapter);

        holder.btnmdfyinventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditInventoryActivity.class);
                intent.putExtra("stationId",stations.get(holder.getAdapterPosition()).getStationId());
                intent.putExtra("stationCode",stations.get(holder.getAdapterPosition()).getStationCode());
                context.startActivity(intent);
            }
        });
        holder.btnmdfyarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditPlatformActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedStation", stations.get(holder.getAdapterPosition()));
                intent.putExtra("bundle",bundle);
                //intent.putExtra("serviceMode",1);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                stname ,stcode,
                countcart,
                countwc;
        Button btnmdfyinventory,
        btnmdfyarea;
        RecyclerView rv_station_child;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);


            stname = itemView.findViewById(R.id.stname);
            stcode = itemView.findViewById(R.id.stcode);
            countcart = itemView.findViewById(R.id.countcart);
            countwc = itemView.findViewById(R.id.countwc);
            btnmdfyinventory = itemView.findViewById(R.id.btnmdfyinventory);
            btnmdfyarea = itemView.findViewById(R.id.btnmdfyarea);
            rv_station_child = itemView.findViewById(R.id.rv_station_child);
        }
    }
}
