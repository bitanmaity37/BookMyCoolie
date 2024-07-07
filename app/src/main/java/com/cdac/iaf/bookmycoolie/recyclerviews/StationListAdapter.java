package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
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

    @Override
    public void onBindViewHolder(@NonNull StationListAdapter.ViewHolder holder, int position) {

        holder.stname.setText(stations.get(holder.getAdapterPosition()).getStationName());
        holder.stcode.setText(stations.get(holder.getAdapterPosition()).getStationCode());
        holder.countwc.setText(stations.get(holder.getAdapterPosition()).getNoOfWheelchairs().toString());
        holder.countcart.setText(stations.get(holder.getAdapterPosition()).getNoOfCarts().toString());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.rv_station_child.setLayoutManager(linearLayoutManager);
        StationChildAdapter stationChildAdapter = new StationChildAdapter(context, stations.get(holder.getAdapterPosition()).getAreaMasterMappingModels());
        holder.rv_station_child.setAdapter(stationChildAdapter);

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
        Button btnmdfy;
        RecyclerView rv_station_child;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);


            stname = itemView.findViewById(R.id.stname);
            stcode = itemView.findViewById(R.id.stcode);
            countcart = itemView.findViewById(R.id.countcart);
            countwc = itemView.findViewById(R.id.countwc);
            btnmdfy = itemView.findViewById(R.id.btnmdfy);
            rv_station_child = itemView.findViewById(R.id.rv_station_child);
        }
    }
}
