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
import com.cdac.iaf.bookmycoolie.models.STATION.AreaMasterMappingModels;
import com.cdac.iaf.bookmycoolie.models.SaveAttendanceModel;
import com.cdac.iaf.bookmycoolie.models.SimpleResponse;
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

public class StationChildAdapter extends RecyclerView.Adapter<StationChildAdapter.ViewHolder> {

    Context context;
    ArrayList<AreaMasterMappingModels> areaMasterMappingModels;

    Boolean viewSwitch;

    public StationChildAdapter(Context context, ArrayList<AreaMasterMappingModels> areaMasterMappingModels, Boolean viewSwitch) {
        this.context = context;
        this.areaMasterMappingModels = areaMasterMappingModels;
        this.viewSwitch = viewSwitch;
    }



    @NonNull
    @Override
    public StationChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_station_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationChildAdapter.ViewHolder holder, int position) {
        SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(context);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        holder.pltfrm.setText(areaMasterMappingModels.get(holder.getAdapterPosition()).getAreaDescription().toString());
        if(viewSwitch && areaMasterMappingModels.get(holder.getAdapterPosition()).getAreaStatus()!= null){
            holder.switch_attndnce.setVisibility(View.VISIBLE);
            holder.x = !areaMasterMappingModels.get(holder.getAdapterPosition()).getAreaStatus();


            if(areaMasterMappingModels.get(holder.getAdapterPosition()).getAreaStatus()){
                holder.switch_attndnce.setChecked(true);
               // holder.switch_attndnce.setText("Y");
                // holder.switch_attndnce.setTrack;
                holder.switch_attndnce.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.completed)));
            }
            if(!areaMasterMappingModels.get(holder.getAdapterPosition()).getAreaStatus()){
                holder.switch_attndnce.setChecked(false);
               // holder.switch_attndnce.setText("N");
                // holder.switch_attndnce.setTrackResource(context.getResources().getColor(R.color.canceled));
                holder.switch_attndnce.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cancelled)));
            }


            holder.switch_attndnce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("PLEASE WAIT");
                    progressDialog.setMessage("UPDATING PLATFORM STATUS!!!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();


                    Call<SimpleResponse> call = RestClient.getRetrofitClient().create(RestInterface.class)
                            .updateStationStatus(securedSharedPreferenceUtils.getLoginData().getJwtToken()
                                    ,new AreaMasterMappingModels(areaMasterMappingModels.get(holder.getAdapterPosition()).getStationAreaMasterMappingId(), null, null));
                    call.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            System.out.println("Response Code "+response.code()+" "+response.body());

                            if(response.code()==200){
                                progressDialog.dismiss();

                                if(!holder.x){
                                    holder.switch_attndnce.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.cancelled)));
                                }
                                if(holder.x){
                                    holder.switch_attndnce.setTrackTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.completed)));
                                }
                                holder.x = !holder.x;
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
                            System.out.println("Error "+t.getMessage()+" "+t);
                        }
                    });



                    /*if(compoundButton.isChecked()){

                    }
                    else {

                    }*/
                }
            });


        }
        else if(!viewSwitch || areaMasterMappingModels.get(holder.getAdapterPosition()).getAreaStatus()!= null){
            holder.switch_attndnce.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return areaMasterMappingModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pltfrm;
        SwitchCompat switch_attndnce;
        Integer resCode;
        Boolean x;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pltfrm = itemView.findViewById(R.id.pltfrm);
            switch_attndnce = itemView.findViewById(R.id.switch_attndnce);
        }
    }
}
