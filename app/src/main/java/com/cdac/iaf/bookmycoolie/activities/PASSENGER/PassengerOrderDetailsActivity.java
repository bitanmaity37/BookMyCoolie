package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.adpater.OrderDetailsAdapter;
import com.cdac.iaf.bookmycoolie.models.OrderDetailsModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerOrderDetailsActivity extends AppCompatActivity {

    String authToken;
    Integer reqId;
    int status;
    ArrayList<OrderDetailsModel> orderDetailsList;
    RecyclerView orderDetailsRecyclerView;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(PassengerOrderDetailsActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authToken = securedSharedPreferenceUtils.getLoginData().getJwtToken();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                // Handle profile item click
                startActivity(new Intent(PassengerOrderDetailsActivity.this, PassengerHome.class));
                //Toast.makeText(PassengerOrderDetailsActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.faq_item) {
                // Handle FAQ item click
                startActivity(new Intent(PassengerOrderDetailsActivity.this, PassengerFaqActivity.class));
                //Toast.makeText(PassengerOrderDetailsActivity.this, "FAQ Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.contact_item) {
                // Handle contact item click

                startActivity(new Intent(PassengerOrderDetailsActivity.this, PassengerContactUsActivity.class));
                //Toast.makeText(PassengerOrderDetailsActivity.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        TextView navbarTitle = findViewById(R.id.navbar_title);
        navbarTitle.setText(R.string.passenger_order_details);

        reqId = getIntent().getIntExtra("reqId", 0);
        status = getIntent().getIntExtra("status",0);
        Intent  intent = getIntent();
        //Toast.makeText(this, "reqId" + intent.getSerializableExtra("reqId"), Toast.LENGTH_SHORT).show();

        fetchOrderDetailsByReqId();

    }

    public void fetchOrderDetailsByReqId(){
        System.out.println(reqId);
        Call<ArrayList<OrderDetailsModel>> orderDetailsModel = RestClient.getRetrofitClient().create(RestInterface.class)
                .getOrderDetailsByRequestId(authToken,reqId);

        orderDetailsModel.enqueue(new Callback<ArrayList<OrderDetailsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderDetailsModel>> call, Response<ArrayList<OrderDetailsModel>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                    System.out.println("response.body().get(0): "+response.body().size());
                    orderDetailsList = response.body();
                    setAdapters();
                    //Toast.makeText(PassengerOrderDetailsActivity.this, orderDetailsList.get(0).toString(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PassengerOrderDetailsActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderDetailsModel>> call, Throwable t) {

            }
        });
    }

    private void setAdapters() {
        System.out.println("orderDetailsList: "+orderDetailsList);
        orderDetailsRecyclerView = findViewById(R.id.order_details_recycler_view);
        OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(this, orderDetailsList, status);
        orderDetailsRecyclerView.setAdapter(orderDetailsAdapter);
    }
}