package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.AddCoolieActivity;
import com.cdac.iaf.bookmycoolie.models.ChangeUserStatus;
import com.cdac.iaf.bookmycoolie.models.Coolie;
import com.cdac.iaf.bookmycoolie.models.SaveAttendanceModel;
import com.cdac.iaf.bookmycoolie.models.SimpleResponse;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CoolieListAdapter.ViewHolder holder, int position) {

        SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(context);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            byte[] decodedBytes = Base64.getDecoder().decode(coolies.get(holder.getAdapterPosition()).getCooliePhoto());
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes,0,decodedBytes.length);
            if(decodedBitmap != null){
                holder.shpimv_coolie.setImageBitmap(decodedBitmap);
            }
        }
        holder.tv_cname.setText(coolies.get(holder.getAdapterPosition()).getUserName().trim());
        holder.billa.setText(coolies.get(holder.getAdapterPosition()).getCoolieBatchId().trim());
        holder.phn.setText(coolies.get(holder.getAdapterPosition()).getUserMobile().trim());

        if(coolies.get(holder.getAdapterPosition()).getUserStatus()==0){
            holder.switch_coolieactv.setChecked(false);
            // holder.switch_attndnce.setTrack;
            holder.switch_coolieactv.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cancelled)));
        }
        if(coolies.get(holder.getAdapterPosition()).getUserStatus()==1){
            holder.switch_coolieactv.setChecked(true);
            holder.switch_coolieactv.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.completed)));
        }

        holder.btnmdfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //context.startActivity(new Intent(context, AddCoolieActivity.class).putExtra("mode",2));
            Intent intent = new Intent(context, AddCoolieActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedCoolie", new Coolie(
                        coolies.get(holder.getAdapterPosition()).getUserId(),
                        coolies.get(holder.getAdapterPosition()).getCoolieId(),
                        coolies.get(holder.getAdapterPosition()).getCooliePhoto(),
                        coolies.get(holder.getAdapterPosition()).getUserMobile(),
                        coolies.get(holder.getAdapterPosition()).getUserName(),
                        coolies.get(holder.getAdapterPosition()).getCoolieBatchId(),
                        coolies.get(holder.getAdapterPosition()).getUserStatus()));
                intent.putExtra("bundle",bundle);
                intent.putExtra("serviceMode",2);
                context.startActivity(intent);
            }
        });
        holder.switch_coolieactv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                                    ,new ChangeUserStatus(coolies.get(holder.getAdapterPosition()).getUserId(),1));
                    call.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.code()==200){
                                progressDialog.dismiss();
                                holder.switch_coolieactv.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.completed)));
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
                                    ,new ChangeUserStatus(coolies.get(holder.getAdapterPosition()).getUserId(),0));
                    call.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            if(response.code()==200){
                                progressDialog.dismiss();

                                holder.switch_coolieactv.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cancelled)));
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
                SwitchCompat switch_coolieactv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shpimv_coolie = itemView.findViewById(R.id.shpimv_coolie);
                    tv_cname = itemView.findViewById(R.id.tv_cname);
            phn = itemView.findViewById(R.id.phn);
                    billa = itemView.findViewById(R.id.billa);
            btnmdfy = itemView.findViewById(R.id.btnmdfy);
                    switch_coolieactv = itemView.findViewById(R.id.switch_coolieactv);
        }
    }
}
