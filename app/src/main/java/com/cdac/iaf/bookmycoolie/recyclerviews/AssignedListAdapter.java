package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.AdminHomeActivity;
import com.cdac.iaf.bookmycoolie.models.CancelReqReqest;
import com.cdac.iaf.bookmycoolie.models.PassengerReqResponses;
import com.cdac.iaf.bookmycoolie.models.SimpleResponse;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.cdac.iaf.bookmycoolie.utils.TempTokenProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignedListAdapter extends RecyclerView.Adapter<AssignedListAdapter.ViewHolder> {

    Context context;
    ArrayList<PassengerReqResponses> requests;

    public AssignedListAdapter(Context context, ArrayList<PassengerReqResponses> requests) {
        this.context = context;
        this.requests = requests;
    }


    @NonNull
    @Override
    public AssignedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_assigned_requests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignedListAdapter.ViewHolder holder, int position) {
        SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(context);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("requests assigned: "+ requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime());
        holder.reqid.setText("REQUEST NO: "+requests.get(holder.getAdapterPosition()).getPassengerRequestId());
        holder.reqtype.setText("SERVICE: "+requests.get(holder.getAdapterPosition()).getServiceTypeName()+", WEIGHT: "+requests.get(holder.getAdapterPosition()).getApproxTotalWeightage()+"KG. BAGS: "+requests.get(holder.getAdapterPosition()).getNoOfBags());
        holder.reqpsngr.setText("PASSENGER: "+requests.get(holder.getAdapterPosition()).getPassengerName());
        /*holder.psngrphn.setText("DATE: "+requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime().substring(1,10)+
                                "Time: "+requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime().substring(11,16)+
                                "to "+requests.get(holder.getAdapterPosition()).getBookingTentativeEndTime().substring(11,16));*/
        String a = requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime();
        String b = requests.get(holder.getAdapterPosition()).getBookingTentativeEndTime();

        if (a != null && b!= null){
            holder.psngrphn.setText("Date: "+a.substring(0,10)+" Time: "+a.substring(11,16));
        } else {
            holder.psngrphn.setText("Date & Time not available");
        }

        //holder.psngrphn.setText(a.substring(6));
        holder.pltfrm.setText("PICKUP: "+requests.get(holder.getAdapterPosition()).getStationAreaPickupFromName()+
                " DROP: "+requests.get(holder.getAdapterPosition()).getStationAreaDropAtName());

        holder.btncmplt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<SimpleResponse> call = RestClient.getRetrofitClient().create(RestInterface.class)
                        .completeReq(securedSharedPreferenceUtils.getLoginData().getJwtToken()
                                ,new CancelReqReqest(requests.get(holder.getAdapterPosition()).getPassengerRequestId()));
                call.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if(response.code()==200){
                            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
                            materialAlertDialogBuilder.setTitle("SUCCESS!!")
                                    .setMessage("REQUEST FINISHED!!!")
                                    .setCancelable(false)
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            requests.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                            notifyItemRangeChanged(holder.getAdapterPosition(), requests.size());
                                            notifyDataSetChanged();
                                        }
                                    }).show();
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

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reqpsngr;
        TextView reqid;
        TextView psngrphn;
        TextView reqtype;
        TextView pltfrm;
        Button btncmplt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reqpsngr = itemView.findViewById(R.id.reqpsngr);
            reqid = itemView.findViewById(R.id.reqid);
            psngrphn = itemView.findViewById(R.id.psngrphn);
            reqtype = itemView.findViewById(R.id.reqtype);
            pltfrm = itemView.findViewById(R.id.pltfrm);
            btncmplt = itemView.findViewById(R.id.btncmplt);
        }
    }
}
