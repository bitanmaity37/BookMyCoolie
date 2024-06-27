package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.FreeCoolieResponse;

import java.util.ArrayList;

public class SelectedCoolieAdapter extends RecyclerView.Adapter<SelectedCoolieAdapter.ViewHolder> {

    Context context;

    ArrayList<Integer> selectedCoolieIDs = new ArrayList<>();

    ArrayList<FreeCoolieResponse> freeCoolieResponses = new ArrayList<>();


    public SelectedCoolieAdapter(Context context, ArrayList<Integer> selectedCoolieIDs, ArrayList<FreeCoolieResponse> freeCoolieResponses) {
        this.context = context;
        this.selectedCoolieIDs = selectedCoolieIDs;
        this.freeCoolieResponses = freeCoolieResponses;
    }

    @NonNull
    @Override
    public SelectedCoolieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_coolie_row, parent, false);
        return new SelectedCoolieAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedCoolieAdapter.ViewHolder holder, int position) {
        holder.cname.setText(freeCoolieResponses.get(holder.getAdapterPosition()).getUserName());
        holder.cbatch.setText(freeCoolieResponses.get(holder.getAdapterPosition()).getCoolieBatchId());

    }

    @Override
    public int getItemCount() {
        return selectedCoolieIDs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView cname;
        TextView cbatch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            cname = itemView.findViewById(R.id.cname);
            cbatch = itemView.findViewById(R.id.cbatch);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    selectedCoolieIDs.remove(getAdapterPosition());
                    freeCoolieResponses.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), selectedCoolieIDs.size());
                    notifyItemRangeChanged(getAdapterPosition(), freeCoolieResponses.size());
                    notifyDataSetChanged();


                    return false;
                }
            });
        }
    }
}
