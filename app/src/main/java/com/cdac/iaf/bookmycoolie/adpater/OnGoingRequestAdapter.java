package com.cdac.iaf.bookmycoolie.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.OrderDetailsModel;
import com.cdac.iaf.bookmycoolie.models.OrderStatusModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.OrderStatusUtil;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnGoingRequestAdapter extends RecyclerView.Adapter<OnGoingRequestAdapter.ViewHolder> {

    ArrayList<OrderStatusModel> orderDetailsList;
    Map<Integer, ArrayList<OrderDetailsModel>> orderDetailsByReqId;
    Context context;
    String authToken;

    public OnGoingRequestAdapter(ArrayList<OrderStatusModel> orderDetailsList, Context context, String authToken, Map<Integer, ArrayList<OrderDetailsModel>> orderDetailsByReqId) {
        this.orderDetailsList = orderDetailsList;
        this.context = context;
        this.authToken = authToken;
        this.orderDetailsByReqId = orderDetailsByReqId;
    }

    @NonNull
    @Override
    public OnGoingRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_carousel_layout, parent, false);
        return new OnGoingRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnGoingRequestAdapter.ViewHolder holder, int position) {
        OrderStatusModel model = orderDetailsList.get(position);
        if (OrderStatusUtil.getOrderStatus(model.getRequestStatus()).equalsIgnoreCase("assigned")
                || OrderStatusUtil.getOrderStatus(model.getRequestStatus()).equalsIgnoreCase("pending")) {
            int requestId = model.getPassengerRequestId();
            holder.stationName.setText(model.getStationName());
            holder.reqId.setText(String.format("ID: %s", String.valueOf(requestId)));
            holder.pickUpArea.setText(model.getStationAreaPickupFromName());
            holder.dropAtArea.setText(model.getStationAreaDropAtName());
            System.out.println("model.getRequestStatus(): " + OrderStatusUtil.getOrderStatus(model.getRequestStatus()));
            holder.requestStatus.setText(OrderStatusUtil.getOrderStatus(model.getRequestStatus()));
            holder.coolieName.setVisibility(View.GONE);
            holder.mobileNumber.setVisibility(View.GONE);
            holder.billaNumber.setText("");

            if (OrderStatusUtil.getOrderStatus(model.getRequestStatus()).equalsIgnoreCase("assigned")) {
                System.out.println("reqId " + requestId);
                System.out.println("orderDetailsByReqId: " + orderDetailsByReqId);
                holder.coolieName.setVisibility(View.VISIBLE);
                holder.mobileNumber.setVisibility(View.VISIBLE);
                //holder.billaNumber.setVisibility(View.VISIBLE);
                try {
                    System.out.println("orderDetailsByReqId.get(0): " + orderDetailsByReqId);
                    holder.mobileNumber.setText(orderDetailsByReqId.get(model.getPassengerRequestId()).get(0).getUserMobile());
                    holder.coolieName.setText(orderDetailsByReqId.get(model.getPassengerRequestId()).get(0).getUserName());
                    holder.billaNumber.setText(orderDetailsByReqId.get(model.getPassengerRequestId()).get(0).getCoolieBatchId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            int colorId = OrderStatusUtil.getColor(model.getRequestStatus());
            holder.requestStatus.setTextColor(ContextCompat.getColor(context, colorId));
        }else{
            holder.itemView.setVisibility(View.GONE);
        }


        //holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(holder.requestId, orderStatusList.get(position).getPassengerRequestId(), orderStatus.getRequestStatus()));
    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView stationName, mobileNumber, billaNumber, coolieName, pickUpArea, dropAtArea, reqId, requestStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stationName = itemView.findViewById(R.id.station_name);
            mobileNumber = itemView.findViewById(R.id.mobile_number);
            billaNumber = itemView.findViewById(R.id.billa_number);
            coolieName = itemView.findViewById(R.id.coolie_name);
            pickUpArea = itemView.findViewById(R.id.pickup_area);
            dropAtArea = itemView.findViewById(R.id.dropping_area);
            reqId = itemView.findViewById(R.id.request_id);
            requestStatus = itemView.findViewById(R.id.request_status);

        }

    }


}
