package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.ModifyOperatorActivity;
import com.cdac.iaf.bookmycoolie.models.ChangeUserStatus;
import com.cdac.iaf.bookmycoolie.models.Operator;
import com.cdac.iaf.bookmycoolie.models.SimpleResponse;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(context);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        holder.optid.setText(operators.get(position).getUserEmpId());
        holder.phn.setText(operators.get(holder.getAdapterPosition()).getUserMobile());
        holder.email.setText(operators.get(holder.getAdapterPosition()).getUserEmailId());
        holder.stn.setText(operators.get(holder.getAdapterPosition()).getStationName());
        holder.optname.setText(operators.get(holder.getAdapterPosition()).getUserName());

        if(operators.get(holder.getAdapterPosition()).getUserStatus()==0){
            holder.switch_oprtractv.setChecked(false);
            // holder.switch_attndnce.setTrack;
            holder.switch_oprtractv.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.canceled)));
        }
        if(operators.get(holder.getAdapterPosition()).getUserStatus()==1){
            holder.switch_oprtractv.setChecked(true);
            holder.switch_oprtractv.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.completed)));
        }

        holder.switch_oprtractv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){

                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("PLEASE WAIT");
                    progressDialog.setMessage("UPDATING ATTENDANCE");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    Call<SimpleResponse> call = RestClient.getRetrofitClient().create(RestInterface.class)
                            .changeStatus(securedSharedPreferenceUtils.getLoginData().getJwtToken()
                                    ,new ChangeUserStatus(operators.get(holder.getAdapterPosition()).getUserId(),1));
                    call.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.code()==200){
                                progressDialog.dismiss();
                                holder.switch_oprtractv.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.completed)));
                                //  holder.switch_attndnce.setTrackResource(context.getResources().getColor(R.color.completed));
                                System.out.println("SAVED ATTENDNC TRUE");
                            }

                            if(response.code()==401){

                                try {
                                    InvalidateUser.invalidate(context);

                                } catch (GeneralSecurityException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });

                }
                else {

                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("PLEASE WAIT");
                    progressDialog.setMessage("UPDATING ATTENDANCE");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    Call<SimpleResponse> call = RestClient.getRetrofitClient().create(RestInterface.class)
                            .changeStatus(securedSharedPreferenceUtils.getLoginData().getJwtToken()
                                    ,new ChangeUserStatus(operators.get(holder.getAdapterPosition()).getUserId(),0));
                    call.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.code()==200){
                                progressDialog.dismiss();

                                holder.switch_oprtractv.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.canceled)));
                                //   holder.switch_attndnce.setTrackResource(context.getResources().getColor(R.color.canceled));
                                System.out.println("SAVED ATTENDNC False");
                            }
                            if(response.code()==401){

                                try {
                                    InvalidateUser.invalidate(context);

                                } catch (GeneralSecurityException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {
                            progressDialog.dismiss();

                        }
                    });


                }
            }
        });

        holder.btnmdfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Operator op2 = operators.get(holder.getAdapterPosition());

                Toast.makeText(context, "Modify Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ModifyOperatorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("opobj",op2);
                intent.putExtra("operator", bundle);
                context.startActivity(intent);
            }
        });

        /*holder.btndlt.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    @Override
    public int getItemCount() {
        return operators.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView optid,phn,email,stn, optname;
        Button btnmdfy;

        SwitchCompat switch_oprtractv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            optid = itemView.findViewById(R.id.optid);
                    phn = itemView.findViewById(R.id.phn);
            email = itemView.findViewById(R.id.email);
                    stn = itemView.findViewById(R.id.stn);
            optname = itemView.findViewById(R.id.optname);

            btnmdfy = itemView.findViewById(R.id.btnmdfy);
            switch_oprtractv = itemView.findViewById(R.id.switch_oprtractv);


        }
    }
}
