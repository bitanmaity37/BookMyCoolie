package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.Coolie;
import com.cdac.iaf.bookmycoolie.models.GetCoolieRequest;
import com.cdac.iaf.bookmycoolie.recyclerviews.CoolieListAdapter;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.TempTokenProvider;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoolieListActivity extends AppCompatActivity {

    RecyclerView rcv_cList;
    ArrayList<Coolie> coolies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coolie_list);
        rcv_cList = findViewById(R.id.rcv_cList);

        rcv_cList = findViewById(R.id.rcv_cList);

        Call<ArrayList<Coolie>> call = RestClient.getRetrofitClient()
                                        .create(RestInterface.class).getCoolies(new TempTokenProvider().returnToken(),
                                                                new GetCoolieRequest(16));
        call.enqueue(new Callback<ArrayList<Coolie>>() {
            @Override
            public void onResponse(Call<ArrayList<Coolie>> call, Response<ArrayList<Coolie>> response) {

                System.out.println("Response code "+response.code());


                if(response.code() == 200){
                    coolies = response.body();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoolieListActivity.this);
                    rcv_cList.setLayoutManager(linearLayoutManager);
                    CoolieListAdapter coolieListAdapter = new CoolieListAdapter(CoolieListActivity.this,coolies);
                    rcv_cList.setAdapter(coolieListAdapter);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Coolie>> call, Throwable t) {

                System.out.println("In here"+t.getMessage());
                t.printStackTrace();

            }
        });




    }
}