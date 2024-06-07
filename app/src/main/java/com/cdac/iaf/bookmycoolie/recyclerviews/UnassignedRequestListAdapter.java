package com.cdac.iaf.bookmycoolie.recyclerviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.ModifyOperatorActivity;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.AssignmentActivity;
import com.cdac.iaf.bookmycoolie.models.RequestList;

import java.util.ArrayList;

public class UnassignedRequestListAdapter extends RecyclerView.Adapter<UnassignedRequestListAdapter.ViewHolder> {

    public UnassignedRequestListAdapter(Context context, ArrayList<RequestList> requests) {
        this.context = context;
        this.requests = requests;
    }

    Context context;
    ArrayList<RequestList> requests;
    @NonNull
    @Override
    public UnassignedRequestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_unassigned_requests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnassignedRequestListAdapter.ViewHolder holder, int position) {

        holder.reqid.setText("REQUEST NO: "+requests.get(holder.getAdapterPosition()).getReqId());
        switch (requests.get(holder.getAdapterPosition()).getReqType()){
            case 1:
                holder.reqtype.setText("SERVICE: Coolie Only");
                break;
            case 2:
                holder.reqtype.setText("SERVICE: Coolie and Cart");
                break;
            case 3:
                holder.reqtype.setText("SERVICE: Coolie , Chair and Cart");
                break;
        }
        holder.reqpsngr.setText("PASSENGER: "+requests.get(holder.getAdapterPosition()).getpName());
        holder.psngrphn.setText("PHONE:"+requests.get(holder.getAdapterPosition()).getpPhn());
        holder.pltfrm.setText("PLATFORM: "+requests.get(holder.getAdapterPosition()).getPlatform());

        holder.btnassgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestList request = new RequestList(requests.get(holder.getAdapterPosition()).getReqId(),
                        requests.get(holder.getAdapterPosition()).getReqType(),
                        requests.get(holder.getAdapterPosition()).getpName(),
                        requests.get(holder.getAdapterPosition()).getpId(),
                        requests.get(holder.getAdapterPosition()).getPlatform(),
                        requests.get(holder.getAdapterPosition()).getpPhn());
                Intent intent = new Intent(context, AssignmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("reqobj",request);
                intent.putExtra("operator", bundle);
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
