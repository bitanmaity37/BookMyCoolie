package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.adpater.FaqAdapter;
import com.cdac.iaf.bookmycoolie.models.FaqModel;
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

public class PassengerFaqActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String authToken;
    ArrayList<FaqModel> faqModels = new ArrayList<>();
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_faq);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(PassengerFaqActivity.this);
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
                startActivity(new Intent(PassengerFaqActivity.this, PassengerHome.class));
                Toast.makeText(PassengerFaqActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.faq_item) {
                // Handle FAQ item click
                startActivity(new Intent(PassengerFaqActivity.this, PassengerFaqActivity.class));
                Toast.makeText(PassengerFaqActivity.this, "FAQ Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.contact_item) {
                // Handle contact item click

                startActivity(new Intent(PassengerFaqActivity.this, PassengerContactUsActivity.class));
                Toast.makeText(PassengerFaqActivity.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        TextView navbarTitle = findViewById(R.id.navbar_title);
        navbarTitle.setText(R.string.passenger_faq);

        Call<ArrayList<FaqModel>> getFaqListCall = RestClient.getRetrofitClient().create(RestInterface.class)
                .getFaqList(authToken);

        getFaqListCall.enqueue(new Callback<ArrayList<FaqModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FaqModel>> call, Response<ArrayList<FaqModel>> response) {
               if(response.isSuccessful() || response.code() == 200){
                   System.out.println(response.body());
                   faqModels.addAll(response.body());
                   setAdapter();
               }else{
                   System.out.println(response.message());
                   Toast.makeText(PassengerFaqActivity.this, "No FAQs found", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<ArrayList<FaqModel>> call, Throwable t) {
                t.getMessage();
            }
        });


        //faqModels.add(new FaqModel(1, "How to find an apprenticeship?","We provide an official service to search through available apprenticeships. To get started, create an account here, specify the desired region, and your preferences. You will be able to search through all officially registered open apprenticeships."));
        // faqModels.add(new FaqModel(2, "Whom to contact?","You can contact the apprenticeship office through our official phone hotline above, or with the web-form below. We generally respond to written requests within 7-10 days."));

    }

    public void setAdapter() {
        recyclerView = findViewById(R.id.faq_recycler_view);
        FaqAdapter adapter = new FaqAdapter(this, faqModels);
        recyclerView.setAdapter(adapter);
    }
}