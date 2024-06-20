package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.PassengerReqResponses;
import com.cdac.iaf.bookmycoolie.models.PassengerRequestsModel;
import com.cdac.iaf.bookmycoolie.recyclerviews.UnassignedRequestListAdapter;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.TempTokenProvider;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignHomeActivity extends AppCompatActivity {

    RecyclerView rcv_requestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_home);
        rcv_requestList = findViewById(R.id.rcv_requestList);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssignHomeActivity.this);
        rcv_requestList.setLayoutManager(linearLayoutManager);

        Call<ArrayList<PassengerReqResponses>> call = RestClient.getRetrofitClient().create(RestInterface.class)
                .getPReqs(new TempTokenProvider().returnToken(),new PassengerRequestsModel(5, 1, 1));
        call.enqueue(new Callback<ArrayList<PassengerReqResponses>>() {
            @Override
            public void onResponse(Call<ArrayList<PassengerReqResponses>> call, Response<ArrayList<PassengerReqResponses>> response) {
              if(response.code()==200){


                  UnassignedRequestListAdapter unassignedRequestListAdapter =  new UnassignedRequestListAdapter(AssignHomeActivity.this,response.body());
                  rcv_requestList.setAdapter(unassignedRequestListAdapter);

                  System.out.println("Requests Unassigned :"+response.body().toString());
              }
            }

            @Override
            public void onFailure(Call<ArrayList<PassengerReqResponses>> call, Throwable t) {

            }
        });





    }
}