package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.PassengerReqResponses;
import com.cdac.iaf.bookmycoolie.utils.TimeConversionUtil;

import java.util.ArrayList;

public class FinishedRequestListAdapter extends RecyclerView.Adapter<FinishedRequestListAdapter.ViewHolder> {

    Context context;
    ArrayList<PassengerReqResponses> requests;

    String mode;


    public FinishedRequestListAdapter(Context context, ArrayList<PassengerReqResponses> requests, String mode) {
        this.context = context;
        this.requests = requests;
        this.mode = mode;
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
        System.out.println("requests: "+ requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime());

        holder.reqid.setText("Request No: "+requests.get(holder.getAdapterPosition()).getPassengerRequestId());

        // holder.reqtype.setText("Service: "+requests.get(holder.getAdapterPosition()).getServiceTypeName()+", Weight: "+requests.get(holder.getAdapterPosition()).getApproxTotalWeightage()+"Kgs. Bags: "+requests.get(holder.getAdapterPosition()).getNoOfBags());

        holder.reqtype.setText(requests.get(holder.getAdapterPosition()).getServiceTypeName()+" required for "+requests.get(holder.getAdapterPosition()).getNoOfBags()+" bags of approx. "+requests.get(holder.getAdapterPosition()).getApproxTotalWeightage()+" kgs ");

        holder.reqpsngr.setText(requests.get(holder.getAdapterPosition()).getPassengerName());
        /*holder.psngrphn.setText("DATE: "+requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime().substring(1,10)+
                                "Time: "+requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime().substring(11,16)+
                                "to "+requests.get(holder.getAdapterPosition()).getBookingTentativeEndTime().substring(11,16));*/
        String a = requests.get(holder.getAdapterPosition()).getBookingTentativeStartTime();
        String b = requests.get(holder.getAdapterPosition()).getBookingTentativeEndTime();

        if (a != null && b!= null){
            holder.psngrphn.setText(TimeConversionUtil.getFullDate(a.substring(0,10)) +" "+a.substring(11,16)+" to "+b.substring(11,16));
        } else {
            holder.psngrphn.setText("Date & Time not available");
        }

        //holder.psngrphn.setText(a.substring(6));
        holder.pltfrm.setText(requests.get(holder.getAdapterPosition()).getStationAreaPickupFromName());
        holder.pltfrmdrop.setText(requests.get(holder.getAdapterPosition()).getStationAreaDropAtName());
        switch (mode){
            case "completed":
                holder.remarks.setVisibility(View.GONE);
                holder.remarks.setTextColor(ContextCompat.getColor(context, R.color.black));
                break;
            case "cancelled":
                holder.remarks.setVisibility(View.VISIBLE);
                holder.remarks.setText("COOLIES ARE NOT AVAILABLE");
                holder.remarks.setTextColor(ContextCompat.getColor(context, R.color.red));
                break;
        }
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
        TextView pltfrm, pltfrmdrop;
        TextView remarks;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reqpsngr = itemView.findViewById(R.id.reqpsngr);
            reqid = itemView.findViewById(R.id.reqid);
            psngrphn = itemView.findViewById(R.id.psngrphn);
            reqtype = itemView.findViewById(R.id.reqtype);
            pltfrm = itemView.findViewById(R.id.pltfrm);
            remarks = itemView.findViewById(R.id.remarks);
            pltfrmdrop = itemView.findViewById(R.id.pltfrmdrop);
        }
    }
}
