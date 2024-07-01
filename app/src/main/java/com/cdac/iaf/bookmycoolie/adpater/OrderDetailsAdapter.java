package com.cdac.iaf.bookmycoolie.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.OrderDetailsModel;
import com.cdac.iaf.bookmycoolie.utils.OrderStatusUtil;
import com.google.android.material.card.MaterialCardView;

import java.text.MessageFormat;
import java.util.ArrayList;

public class OrderDetailsAdapter extends  RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>{

    private ArrayList<OrderDetailsModel> orderDetails;
    private Context context;
    private int status;

    public OrderDetailsAdapter(Context context, ArrayList<OrderDetailsModel> orderDetails, int status) {
        this.context = context;
        this.orderDetails = orderDetails;
        this.status = status;
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_request_details, parent, false);
        return new OrderDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.ViewHolder holder, int position) {
        OrderDetailsModel orderDetailsModel = orderDetails.get(position);
        String mobileNumber = orderDetailsModel.getUserMobile();
        String maskedMobileNumber = "*******"+mobileNumber.substring(mobileNumber.length() - 3);

        holder.coolieName.setText(String.format("%s", orderDetailsModel.getUserName()));
        holder.coolieBillaNo.setText(String.format("Billa no.:  %s", orderDetailsModel.getCoolieBatchId()));
        holder.cooliePhoneNo.setText(String.format("%s", maskedMobileNumber));

        String base64Image = orderDetailsModel.getCooliePhoto();
        System.out.println("in adapter base64Image: "+base64Image);
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            System.out.println("in adapter decodedByte: "+decodedByte);
            //holder.coolieImage.setImageBitmap(decodedByte);

            Glide.with(context)
                    .load(decodedByte)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(50))) // 20 is the radius in pixels
                    .into(holder.coolieImage);

            Toast.makeText(context, ""+status, Toast.LENGTH_SHORT).show();

            holder.callButton.setVisibility(View.GONE);

            if(!OrderStatusUtil.getOrderStatus(status).equalsIgnoreCase("COMPLETED")){
                holder.callButton.setVisibility(View.VISIBLE);
                holder.cooliePhoneNo.setText(String.format("%s", mobileNumber));
                holder.callButton.setOnClickListener(v -> {
                    String phoneNumber = holder.cooliePhoneNo.getText().toString().trim();
                    phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    System.out.println("in adapter phoneNumber: "+phoneNumber);
                    dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                    context.startActivity(dialIntent);
                });
            }



        } else {
            holder.coolieImage.setImageResource(R.drawable.coolie_icon); // Set a default image if base64Image is null or empty
        }

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView coolieImage;
        TextView coolieName,coolieBillaNo,cooliePhoneNo;
        MaterialCardView orderDetailsCard;
        ImageView callButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coolieImage = itemView.findViewById(R.id.coolie_photo);
            coolieName = itemView.findViewById(R.id.coolie_name);
            coolieBillaNo = itemView.findViewById(R.id.coolie_billa_no);
            cooliePhoneNo = itemView.findViewById(R.id.coolie_phone_no);
            callButton = itemView.findViewById(R.id.coolie_phone_no_btn);
            orderDetailsCard = itemView.findViewById(R.id.order_details_card);
            orderDetailsCard.setCardElevation(8f);
        }
    }

}
