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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerOrderDetailsActivity extends AppCompatActivity {

    String authToken;
    Integer reqId;
    ArrayList<OrderDetailsModel> orderDetailsList;
    RecyclerView orderDetailsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        SharedPreferences sharedPreferences = getSharedPreferences("jwt_token", MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth_token", null);
        TextView navbarTitle = findViewById(R.id.navbar_title);
        navbarTitle.setText(R.string.passenger_order_details);

        reqId = getIntent().getIntExtra("reqId", 0);
        Intent  intent = getIntent();
        Toast.makeText(this, "reqId" + intent.getSerializableExtra("reqId"), Toast.LENGTH_SHORT).show();

        fetchOrderDetailsByReqId();

    }

    public void fetchOrderDetailsByReqId(){
        System.out.println(reqId);
        Call<ArrayList<OrderDetailsModel>> orderDetailsModel = RestClient.getRetrofitClient().create(RestInterface.class)
                .getOrderDetailsByRequestId(authToken,reqId);

        orderDetailsModel.enqueue(new Callback<ArrayList<OrderDetailsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderDetailsModel>> call, Response<ArrayList<OrderDetailsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("response.body().get(0): "+response.body().get(0));
                    orderDetailsList = response.body();
                    setAdapters();
                    Toast.makeText(PassengerOrderDetailsActivity.this, orderDetailsList.get(0).toString(), Toast.LENGTH_SHORT).show();
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
        OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(this, orderDetailsList);
        orderDetailsRecyclerView.setAdapter(orderDetailsAdapter);
    }
}