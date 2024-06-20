package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.Coolie;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Base64;
public class CoolieListAdapter extends RecyclerView.Adapter<CoolieListAdapter.ViewHolder> {

    Context context;
    ArrayList<Coolie> coolies;

    public CoolieListAdapter(Context context, ArrayList<Coolie> coolies) {
        this.context = context;
        this.coolies = coolies;
    }

    @NonNull
    @Override
    public CoolieListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coolie,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoolieListAdapter.ViewHolder holder, int position) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            byte[] decodedBytes = Base64.getDecoder().decode(coolies.get(holder.getAdapterPosition()).getCooliePhoto());
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes,0,decodedBytes.length);
            if(decodedBitmap != null){
                holder.shpimv_coolie.setImageBitmap(decodedBitmap);
            }
        }

    }

    @Override
    public int getItemCount() {
        return coolies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

                ShapeableImageView shpimv_coolie;
                TextView tv_cname;
                TextView phn;
                TextView billa;
                Button btnmdfy;
                Button btndlt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shpimv_coolie = itemView.findViewById(R.id.shpimv_coolie);
                    tv_cname = itemView.findViewById(R.id.tv_cname);
            phn = itemView.findViewById(R.id.phn);
                    billa = itemView.findViewById(R.id.billa);
            btnmdfy = itemView.findViewById(R.id.btnmdfy);
                    btndlt = itemView.findViewById(R.id.btndlt);
        }
    }
}
