package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.PassengerReqResponses;

import java.util.ArrayList;

public class FinishedRequestListAdapter extends RecyclerView.Adapter<FinishedRequestListAdapter.ViewHolder> {

    Context context;
    ArrayList<PassengerReqResponses> requests;


    public FinishedRequestListAdapter(Context context, ArrayList<PassengerReqResponses> requests) {
        this.context = context;
        this.requests = requests;
        System.out.println("In Here+++++++++++++++++++++++++++++++++"+requests.toString());
    }

    @NonNull
    @Override
    public FinishedRequestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_compltd_cancld_requests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinishedRequestListAdapter.ViewHolder holder, int position) {
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reqpsngr = itemView.findViewById(R.id.reqpsngr);
            reqid = itemView.findViewById(R.id.reqid);
            psngrphn = itemView.findViewById(R.id.psngrphn);
            reqtype = itemView.findViewById(R.id.reqtype);
            pltfrm = itemView.findViewById(R.id.pltfrm);
        }
    }
}
