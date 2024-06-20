package com.cdac.iaf.bookmycoolie.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.OrderStatusModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.OrderStatusUtil;
import com.cdac.iaf.bookmycoolie.utils.ServiceTypeUtil;

import java.text.MessageFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.OrderStatusViewHolder> {

    private ArrayList<OrderStatusModel> orderStatusList;
    private Context context;
    OnItemClickListener onItemClickListener;
    String authToken;

    public OrderStatusAdapter(Context context, ArrayList<OrderStatusModel> orderStatusList, String authToken) {
        this.context = context;
        this.orderStatusList = orderStatusList;
        this.authToken = authToken;
    }

    @NonNull
    @Override
    public OrderStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_history_layout, parent, false);
        return new OrderStatusViewHolder(view);
    }

    private String getStatusString(int status) {
        switch (status) {
            case 1:
                return context.getString(R.string.pending);
            case 2:
                return context.getString(R.string.assinged);
            case 3:
                return context.getString(R.string.completed);
            case 4:
                return context.getString(R.string.canceled);
            default:
                return context.getString(R.string.pending); // default to pending if status code is unknown
        }
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusViewHolder holder, int position) {
        OrderStatusModel orderStatus = orderStatusList.get(position);
        //String requestStatus = getStatusString(orderStatus.getRequestStatus());
        holder.requestId.setText(String.valueOf(orderStatus.getPassengerRequestId()));
        holder.requestStatus.setText(OrderStatusUtil.getOrderStatus(orderStatus.getRequestStatus()));
        holder.pickUpArea.setText(orderStatus.getStationAreaPickupFromName());
        holder.dropOffArea.setText(orderStatus.getStationAreaDropAtName());
        holder.stationName.setText(orderStatus.getStationName());
        holder.noOfBags.setText(String.valueOf(orderStatus.getNoOfBags()));
        holder.serviceType.setText(ServiceTypeUtil.getServiceTypeName(orderStatus.getServiceType()));

        int colorId = OrderStatusUtil.getColor(orderStatus.getRequestStatus());
        holder.requestStatus.setTextColor(ContextCompat.getColor(context, colorId));

        // If you have a specific drawable for different statuses, you can set it here.
        holder.coolieImage.setText(MessageFormat.format("{0}.", (position + 1)));

        if (orderStatus.getRequestStatus() != 1) {
            holder.cancelButton.setVisibility(View.GONE);
        } else {
            holder.cancelButton.setVisibility(View.VISIBLE);
            holder.cancelButton.setOnClickListener(v -> {
                orderStatusList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, orderStatusList.size());
            });
        }

        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(holder.requestId, orderStatusList.get(position).getPassengerRequestId()));

        // Handle the cancel request button
        holder.cancelButton.setOnClickListener(v -> {
            /*orderStatusList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, orderStatusList.size());*/

            Call<Boolean> cancelRequestCall = RestClient.getRetrofitClient().create(RestInterface.class)
                    .cancelPassengerRequest(authToken,orderStatus.getPassengerRequestId());
            cancelRequestCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.body() == true){
                        Toast.makeText(context, "Request Canceled Successfully", Toast.LENGTH_SHORT).show();
                        ((Activity) v.getContext()).recreate();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return orderStatusList.size();
    }

    public class OrderStatusViewHolder extends RecyclerView.ViewHolder {

        TextView coolieImage;
        TextView requestId, requestStatus, pickUpArea, dropOffArea, stationName, noOfBags, serviceType;
        Button cancelButton;

        public OrderStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            coolieImage = itemView.findViewById(R.id.coolie_image);
            requestId = itemView.findViewById(R.id.request_id);
            requestStatus = itemView.findViewById(R.id.order_status);
            pickUpArea = itemView.findViewById(R.id.pickup_area);
            dropOffArea = itemView.findViewById(R.id.dropping_area);
            stationName = itemView.findViewById(R.id.station_name);
            noOfBags = itemView.findViewById(R.id.no_of_bags);
            serviceType = itemView.findViewById(R.id.service_type);
            cancelButton = itemView.findViewById(R.id.cancel_request_btn);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(TextView textView, int reqId);
    }

}