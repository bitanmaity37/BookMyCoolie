package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.adpater.OrderStatusAdapter;
import com.cdac.iaf.bookmycoolie.models.OrderStatusModel;
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
import retrofit2.Retrofit;

public class PassengerOrderActivity extends AppCompatActivity {

    private String authToken;
    private ArrayList<OrderStatusModel> orderStatusList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderStatusAdapter orderStatusAdapter;
    private SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_order);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(PassengerOrderActivity.this);
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
                startActivity(new Intent(PassengerOrderActivity.this, PassengerHome.class));
                //Toast.makeText(PassengerOrderActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.faq_item) {
                // Handle FAQ item click
                startActivity(new Intent(PassengerOrderActivity.this, PassengerFaqActivity.class));
                //Toast.makeText(PassengerOrderActivity.this, "FAQ Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.contact_item) {
                // Handle contact item click

                startActivity(new Intent(PassengerOrderActivity.this, PassengerContactUsActivity.class));
                //Toast.makeText(PassengerOrderActivity.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        TextView navbarTitle = findViewById(R.id.navbar_title);
        navbarTitle.setText(R.string.passenger_order_history);

        recyclerView = findViewById(R.id.order_status_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchOrderHistory();

    }

    private void fetchOrderHistory() {
        Call<ArrayList<OrderStatusModel>> orderStatusModelCall = RestClient.getRetrofitClient()
                .create(RestInterface.class)
                .getOrderHistoryByPassengerId(authToken, securedSharedPreferenceUtils.getLoginData().getUserId());
        orderStatusModelCall.enqueue(new Callback<ArrayList<OrderStatusModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderStatusModel>> call, Response<ArrayList<OrderStatusModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderStatusList.addAll(response.body());
                    setAdapter();
                    //System.out.println(orderStatusList.get(0));
                    //Toast.makeText(PassengerOrderActivity.this, orderStatusList.get(0).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderStatusModel>> call, Throwable t) {
                Toast.makeText(PassengerOrderActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {
        System.out.println("setAdapter()");
        orderStatusAdapter = new OrderStatusAdapter(PassengerOrderActivity.this,orderStatusList,authToken);
        recyclerView.setAdapter(orderStatusAdapter);
        orderStatusAdapter.setOnItemClickListener((textView, reqId, status) -> {
            Intent intent = new Intent(PassengerOrderActivity.this, PassengerOrderDetailsActivity.class);
            intent.putExtra("reqId", reqId);
            intent.putExtra("status", status);
            startActivity(intent);
        });

    }


}