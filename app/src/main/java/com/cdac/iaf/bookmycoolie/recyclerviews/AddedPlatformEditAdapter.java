package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.STATION.AreaMasterMappingModels;
import com.cdac.iaf.bookmycoolie.models.STATION.StationAreaMasterMapping;

import java.util.ArrayList;

public class AddedPlatformEditAdapter extends RecyclerView.Adapter<AddedPlatformEditAdapter.ViewHolder> {

    Context context;
    ArrayList<AreaMasterMappingModels> platforms;
    ArrayList<String> pnames;

    public AddedPlatformEditAdapter(Context context, ArrayList<AreaMasterMappingModels> platforms, ArrayList<String> pnames) {
        this.context = context;
        this.platforms = platforms;
        this.pnames = pnames;
    }

    @NonNull
    @Override
    public AddedPlatformEditAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_platform_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddedPlatformEditAdapter.ViewHolder holder, int position) {
        holder.platform.setText(platforms.get(holder.getAdapterPosition()).getAreaDescription().toString());


    }

    @Override
    public int getItemCount() {
        return platforms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView platform;
        Button btnrmv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            platform = itemView.findViewById(R.id.platform);
            btnrmv = itemView.findViewById(R.id.btnrmv);
            btnrmv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    platforms.remove(getAdapterPosition());
                    pnames.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), platforms.size());
                    notifyItemRangeChanged(getAdapterPosition(),pnames.size());
                    notifyDataSetChanged();
                }
            });

        }
    }
}
