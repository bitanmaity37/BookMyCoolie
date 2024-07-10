package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.STATION.AllStationResponse;
import com.cdac.iaf.bookmycoolie.recyclerviews.OperatorListAdapter;
import com.cdac.iaf.bookmycoolie.recyclerviews.StationListAdapter;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationListActivity extends AppCompatActivity {

    RecyclerView rcv_stnlst;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list);
        rcv_stnlst = findViewById(R.id.rcv_stnlst);

        System.out.println("In Station List");

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(StationListActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Call<ArrayList<AllStationResponse>> call = RestClient.getRetrofitClient().create(RestInterface.class)
                .getStns(securedSharedPreferenceUtils.getLoginData().getJwtToken());
        call.enqueue(new Callback<ArrayList<AllStationResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<AllStationResponse>> call, Response<ArrayList<AllStationResponse>> response) {
                if(response.code()==200){
                    System.out.println("bitan "+response.body().toString());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StationListActivity.this);
                    rcv_stnlst.setLayoutManager(linearLayoutManager);
                    StationListAdapter stationListAdapter = new StationListAdapter(StationListActivity.this, response.body());
                    rcv_stnlst.setAdapter(stationListAdapter);
                }
                if(response.code()==401){
                    System.out.println("401 happnd");

                    try {
                        InvalidateUser.invalidate(StationListActivity.this);

                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<AllStationResponse>> call, Throwable t) {
                System.out.println("Error happnd"+t.getMessage()+t.toString());

            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(StationListActivity.this,AdminHomeActivity.class));
        finishAffinity();
    }
}