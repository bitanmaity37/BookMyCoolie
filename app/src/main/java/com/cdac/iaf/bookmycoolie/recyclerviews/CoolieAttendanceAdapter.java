package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.AttendanceCoolieResponse;
import com.cdac.iaf.bookmycoolie.models.SaveAttendanceModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoolieAttendanceAdapter extends RecyclerView.Adapter<CoolieAttendanceAdapter.ViewHolder> {

    Context context;
    ArrayList<AttendanceCoolieResponse> coolies;

    public CoolieAttendanceAdapter(Context context, ArrayList<AttendanceCoolieResponse> coolies) {
        this.context = context;
        this.coolies = coolies;
        System.out.println("Coolies are"+coolies);
    }

    @NonNull
    @Override
    public CoolieAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_attendance,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoolieAttendanceAdapter.ViewHolder holder, int position) {

        SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(context);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        holder.cid.setText(String.valueOf(holder.getAdapterPosition()+1));
        holder.cname.setText(coolies.get(holder.getAdapterPosition()).getUserName());
        holder.cbatch.setText(coolies.get(holder.getAdapterPosition()).getCoolieBatchId());
        if(coolies.get(holder.getAdapterPosition()).getCoolieStatus()){
            holder.switch_attndnce.setChecked(true);
            holder.switch_attndnce.setText("Y");
           // holder.switch_attndnce.setTrack;
            holder.switch_attndnce.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.completed)));
        }
        if(!coolies.get(holder.getAdapterPosition()).getCoolieStatus()){
            holder.switch_attndnce.setChecked(false);
            holder.switch_attndnce.setText("N");
           // holder.switch_attndnce.setTrackResource(context.getResources().getColor(R.color.canceled));
            holder.switch_attndnce.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cancelled)));

        }
        holder.switch_attndnce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){

                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("PLEASE WAIT");
                    progressDialog.setMessage("UPDATING ATTENDANCE");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    Call<ResponseBody> call = RestClient.getRetrofitClient().create(RestInterface.class)
                            .saveAttendance(securedSharedPreferenceUtils.getLoginData().getJwtToken()
                                    ,new SaveAttendanceModel(coolies.get(holder.getAdapterPosition()).getCoolieId(),true));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if(response.code()==200 && response.body().string().equals("true")){
                                    progressDialog.dismiss();
                                    holder.switch_attndnce.setText("Y");
                                    holder.switch_attndnce.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.completed)));
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
                            } catch (IOException e) {
                                progressDialog.dismiss();
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
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

                    Call<ResponseBody> call = RestClient.getRetrofitClient().create(RestInterface.class)
                            .saveAttendance(securedSharedPreferenceUtils.getLoginData().getJwtToken(),new SaveAttendanceModel(coolies.get(holder.getAdapterPosition()).getCoolieId(),false));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if(response.code()==200 && response.body().string().equals("true")){
                                    progressDialog.dismiss();
                                    holder.switch_attndnce.setText("N");
                                    holder.switch_attndnce.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cancelled)));
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
                            } catch (IOException e) {
                                progressDialog.dismiss();
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
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
        TextView cid;
        TextView cname;
        TextView cbatch;
        SwitchCompat switch_attndnce;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cid= itemView.findViewById(R.id.cid);
            cname= itemView.findViewById(R.id.cname);
            cbatch= itemView.findViewById(R.id.cbatch);
            switch_attndnce= itemView.findViewById(R.id.switch_attndnce);



        }
    }
}
