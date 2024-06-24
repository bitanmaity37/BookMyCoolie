package com.cdac.iaf.bookmycoolie.adpater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.ContactUsModel;
import com.cdac.iaf.bookmycoolie.models.Operator;

import java.util.ArrayList;

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.ViewHolder> {

    private ArrayList<Operator> contactList;
    private Context context;

    public ContactUsAdapter(Context context, ArrayList<Operator> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactUsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_us_layout, parent, false);
        return new ContactUsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactUsAdapter.ViewHolder holder, int position) {
        Operator contactUsModel = contactList.get(position);
        System.out.println("contactUsModel.getStationName() = " + contactUsModel);
        holder.stationName.setText(contactUsModel.getStationName());
        holder.operatorEmailId.setText(contactUsModel.getUserEmailId());
        holder.operatorMobileNo.setText(contactUsModel.getUserMobile());

        holder.mobileNoLayout.setOnClickListener(v -> {
            String phoneNumber = holder.operatorMobileNo.getText().toString().trim();
            phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            System.out.println("in adapter phoneNumber: "+phoneNumber);
            dialIntent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(dialIntent);
        });

        holder.emailIdLayout.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + holder.operatorEmailId.getText().toString().trim()));
            context.startActivity(emailIntent);
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout emailIdLayout, mobileNoLayout;
        private TextView stationName, operatorEmailId, operatorMobileNo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mobileNoLayout = itemView.findViewById(R.id.contact_us_mobile_no_layout);
            emailIdLayout = itemView.findViewById(R.id.contact_us_email_id_layout);
            stationName = itemView.findViewById(R.id.contact_us_station_name);
            operatorEmailId = itemView.findViewById(R.id.contact_us_email_id);
            operatorMobileNo = itemView.findViewById(R.id.contact_us_mobile_no);

        }

    }

}
