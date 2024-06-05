package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.ModifyOperatorActivity;
import com.cdac.iaf.bookmycoolie.models.Operator;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class OperatorListAdapter extends RecyclerView.Adapter<OperatorListAdapter.ViewHolder> {

    Context context;
    ArrayList<Operator> operators;

    public OperatorListAdapter(Context context, ArrayList<Operator> operators) {
        this.context = context;
        this.operators = operators;
    }


    @NonNull
    @Override
    public OperatorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_operator, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OperatorListAdapter.ViewHolder holder, int position) {
        holder.optid.setText("ID: "+operators.get(position).getId()+" NAME: "+operators.get(holder.getAdapterPosition()).getName());
        holder.phn.setText("PH: "+operators.get(holder.getAdapterPosition()).getPhone());
        holder.email.setText("MAIL: "+operators.get(holder.getAdapterPosition()).getEmail());
        holder.stn.setText("STN: "+operators.get(holder.getAdapterPosition()).getStation());
        holder.btnmdfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Operator op = new Operator(operators.get(holder.getAdapterPosition()).getId(),
                        operators.get(holder.getAdapterPosition()).getName(),
                        operators.get(holder.getAdapterPosition()).getPhone(),
                        operators.get(holder.getAdapterPosition()).getEmail(),
                        operators.get(holder.getAdapterPosition()).getPassword(),
                        operators.get(holder.getAdapterPosition()).getStn_id(),
                        operators.get(holder.getAdapterPosition()).getStation());
                Toast.makeText(context, "Modify Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ModifyOperatorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("opobj",op);
                intent.putExtra("operator", bundle);
                context.startActivity(intent);
            }
        });

        holder.btndlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
                materialAlertDialogBuilder.setCancelable(false);
                materialAlertDialogBuilder.setTitle("ALERT!!");
                materialAlertDialogBuilder.setMessage("Are You Sure You Want To Delete?");
                materialAlertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Deleting...........", Toast.LENGTH_LONG).show();

                        operators.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), operators.size());
                        dialogInterface.dismiss();
                    }
                });
                materialAlertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                materialAlertDialogBuilder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return operators.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView optid,phn,email,stn;
        Button btnmdfy,btndlt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            optid = itemView.findViewById(R.id.optid);
                    phn = itemView.findViewById(R.id.phn);
            email = itemView.findViewById(R.id.email);
                    stn = itemView.findViewById(R.id.stn);

            btnmdfy = itemView.findViewById(R.id.btnmdfy);
                    btndlt = itemView.findViewById(R.id.btndlt);
        }
    }
}
