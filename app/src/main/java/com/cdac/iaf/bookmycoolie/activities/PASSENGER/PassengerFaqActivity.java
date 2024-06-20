package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.adpater.FaqAdapter;
import com.cdac.iaf.bookmycoolie.models.FaqModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerFaqActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String authToken;
    ArrayList<FaqModel> faqModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_faq);

        SharedPreferences sharedPreferences = getSharedPreferences("jwt_token", MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth_token", null);

        Call<ArrayList<FaqModel>> getFaqListCall = RestClient.getRetrofitClient().create(RestInterface.class)
                .getFaqList(authToken);

        getFaqListCall.enqueue(new Callback<ArrayList<FaqModel>>() {
            @Override
            public void onResponse(Call<ArrayList<FaqModel>> call, Response<ArrayList<FaqModel>> response) {
                faqModels.addAll(response.body());
                setAdapter();
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