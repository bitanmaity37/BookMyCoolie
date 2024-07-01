package com.cdac.iaf.bookmycoolie.adpater;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    @Override
    public void onBindViewHolder(@NonNull OrderStatusViewHolder holder, int position) {
        OrderStatusModel orderStatus = orderStatusList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Date bookingDate = new Date(orderStatus.getBookingDate().getTime());
        Date recordDate = new Date(orderStatus.getRecordTracking().getTime());
        //String requestStatus = getStatusString(orderStatus.getRequestStatus());
        holder.requestId.setText(String.format("ID:%s", String.valueOf(orderStatus.getPassengerRequestId())));
        holder.requestStatus.setText(OrderStatusUtil.getOrderStatus(orderStatus.getRequestStatus()));
        holder.pickUpArea.setText(orderStatus.getStationAreaPickupFromName());
        holder.dropOffArea.setText(orderStatus.getStationAreaDropAtName());
        holder.stationName.setText(orderStatus.getStationName());
        holder.noOfBags.setText(String.format("%s bags",String.valueOf(orderStatus.getNoOfBags())));
        holder.serviceType.setText(ServiceTypeUtil.getServiceTypeName(orderStatus.getServiceType()));
        holder.bookedAt.setText(String.format("Booked on: %s", sdf.format(bookingDate)));
        //holder.recordTracking.setText(sdf.format(recordDate));

        /*switch (OrderStatusUtil.getOrderStatus(orderStatus.getRequestStatus()).toLowerCase()){

            case "pending":
                holder.recordTrackingLayout.setVisibility(View.GONE);
                holder.cancelButton.setVisibility(View.VISIBLE);
                holder.cancelButton.setOnClickListener(v -> {
                    orderStatusList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, orderStatusList.size());
                });
                break;

            case "assigned":
                holder.cancelButton.setVisibility(View.GONE);
                holder.recordTrackingLabel.setText("Assigned At");
                break;

            case "completed":
                holder.cancelButton.setVisibility(View.GONE);
                holder.recordTrackingLabel.setText("Completed At");
                break;

            case "cancelled":
                holder.cancelButton.setVisibility(View.GONE);
                holder.recordTrackingLabel.setText("Cancelled At");
                break;

            default:
                    break;

        }*/

        int colorId = OrderStatusUtil.getColor(orderStatus.getRequestStatus());
        holder.requestStatus.setTextColor(ContextCompat.getColor(context, colorId));

        // If you have a specific drawable for different statuses, you can set it here.
        holder.coolieImage.setText(MessageFormat.format("{0}.", (position + 1)));

        if (OrderStatusUtil.getOrderStatus(orderStatus.getRequestStatus()).equalsIgnoreCase("pending")) {
            holder.cancelButton.setVisibility(View.VISIBLE);
            holder.cancelButton.setOnClickListener(v -> {
                orderStatusList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, orderStatusList.size());
            });
        } else {
            holder.cancelButton.setVisibility(View.GONE);
        }

        System.out.println("Request Status: " + orderStatus.getRequestStatus());

        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(holder.requestId, orderStatusList.get(position).getPassengerRequestId(), orderStatus.getRequestStatus()));

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
        LinearLayout recordTrackingLayout;
        TextView requestId, requestStatus, pickUpArea, dropOffArea,
                stationName, noOfBags, serviceType, recordTrackingLabel, bookedAt, recordTracking;
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
            //recordTrackingLabel = itemView.findViewById(R.id.record_tracking_label);
            bookedAt = itemView.findViewById(R.id.booked_at);
            //recordTracking = itemView.findViewById(R.id.record_tracking);
            recordTrackingLayout = itemView.findViewById(R.id.linear_layout_record_tracking);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(TextView textView, int reqId, int status);
    }

}
