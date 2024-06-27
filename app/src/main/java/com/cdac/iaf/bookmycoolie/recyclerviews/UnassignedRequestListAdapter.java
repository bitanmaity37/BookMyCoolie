package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.AssignmentActivity;
import com.cdac.iaf.bookmycoolie.models.PassengerReqResponses;

import java.util.ArrayList;

public class UnassignedRequestListAdapter extends RecyclerView.Adapter<UnassignedRequestListAdapter.ViewHolder> {

    public UnassignedRequestListAdapter(Context context, ArrayList<PassengerReqResponses> requests, Integer serviceMode) {
        this.context = context;
        this.requests = requests;
        this.serviceMode = serviceMode;
        System.out.println("In const.........."+requests);
    }

    Context context;
    ArrayList<PassengerReqResponses> requests;
    Integer serviceMode;
    @NonNull
    @Override
    public UnassignedRequestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_unassigned_requests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnassignedRequestListAdapter.ViewHolder holder, int position) {

        System.out.println("requests: "+ requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime());

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
                                "\nDROP: "+requests.get(holder.getAdapterPosition()).getStationAreaDropAtName());

        holder.btnassgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AssignmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("reqobj",new PassengerReqResponses(
                        requests.get(holder.getAdapterPosition()).getPassengerRequestId(),
                        requests.get(holder.getAdapterPosition()).getApproxTotalWeightage(),
                        requests.get(holder.getAdapterPosition()).getBookingDate(),
                        requests.get(holder.getAdapterPosition()).getBookingFor(),
                        requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime(),
                        requests.get(holder.getAdapterPosition()).getBookingType(),
                        requests.get(holder.getAdapterPosition()).getNoOfBags(),
                        requests.get(holder.getAdapterPosition()).getStationAreaDropAt(),
                        requests.get(holder.getAdapterPosition()).getStationAreaPickupFrom(),
                        requests.get(holder.getAdapterPosition()).getStationAreaDropAtName(),
                        requests.get(holder.getAdapterPosition()).getStationAreaPickupFromName(),
                        requests.get(holder.getAdapterPosition()).getServiceTypeName(),
                        requests.get(holder.getAdapterPosition()).getPassengerName(),
                        requests.get(holder.getAdapterPosition()).getBookingTentativeEndTime()
                        ));
                intent.putExtra("operator", bundle);
                intent.putExtra("serviceMode",serviceMode);
                Toast.makeText(context, "serviceMode in RV"+serviceMode, Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
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
                Button btnassgn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             reqpsngr = itemView.findViewById(R.id.reqpsngr);
             reqid = itemView.findViewById(R.id.reqid);
             psngrphn = itemView.findViewById(R.id.psngrphn);
             reqtype = itemView.findViewById(R.id.reqtype);
             pltfrm = itemView.findViewById(R.id.pltfrm);
             btnassgn = itemView.findViewById(R.id.btnassgn);


        }
    }
}
