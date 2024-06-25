package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.adpater.ContactUsAdapter;
import com.cdac.iaf.bookmycoolie.models.ContactUsModel;
import com.cdac.iaf.bookmycoolie.models.Operator;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerContactUsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Operator> contactList = new ArrayList<>();
    String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_contact_us);

        SharedPreferences sharedPreferences = getSharedPreferences("jwt_token", MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth_token", null);
        TextView navbarTitle = findViewById(R.id.navbar_title);
        navbarTitle.setText(R.string.passenger_contact);

        Call<ArrayList<Operator>> getOperatorListCall = RestClient.getRetrofitClient().create(RestInterface.class)
                .getOperators(authToken);

        getOperatorListCall.enqueue(new Callback<ArrayList<Operator>>() {
            @Override
            public void onResponse(Call<ArrayList<Operator>> call, Response<ArrayList<Operator>> response) {
                contactList = response.body();
                recyclerView = findViewById(R.id.contact_us_recycler_view);
                ContactUsAdapter adapter = new ContactUsAdapter(PassengerContactUsActivity.this,contactList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Operator>> call, Throwable t) {

            }
        });


    }
}