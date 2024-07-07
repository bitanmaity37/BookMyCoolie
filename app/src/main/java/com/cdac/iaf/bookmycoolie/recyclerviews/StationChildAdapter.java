package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.STATION.AreaMasterMappingModels;

import java.util.ArrayList;

public class StationChildAdapter extends RecyclerView.Adapter<StationChildAdapter.ViewHolder> {

    Context context;
    ArrayList<AreaMasterMappingModels> areaMasterMappingModels;

    public StationChildAdapter(Context context, ArrayList<AreaMasterMappingModels> areaMasterMappingModels) {
        this.context = context;
        this.areaMasterMappingModels = areaMasterMappingModels;
    }

    @NonNull
    @Override
    public StationChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_station_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationChildAdapter.ViewHolder holder, int position) {
        holder.pltfrm.setText(areaMasterMappingModels.get(holder.getAdapterPosition()).getAreaDescription().toString());
    }

    @Override
    public int getItemCount() {
        return areaMasterMappingModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pltfrm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pltfrm = itemView.findViewById(R.id.pltfrm);
        }
    }
}
