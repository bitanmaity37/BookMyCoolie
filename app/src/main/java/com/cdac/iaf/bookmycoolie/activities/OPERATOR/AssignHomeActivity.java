package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.PassengerReqResponses;
import com.cdac.iaf.bookmycoolie.models.PassengerRequestsModel;
import com.cdac.iaf.bookmycoolie.recyclerviews.AssignedListAdapter;
import com.cdac.iaf.bookmycoolie.recyclerviews.FinishedRequestListAdapter;
import com.cdac.iaf.bookmycoolie.recyclerviews.UnassignedRequestListAdapter;
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

public class AssignHomeActivity extends AppCompatActivity {

    RecyclerView rcv_requestList;

    TextView tv_pending,tv_ongoing, tv_finished,tv_cancelled;

    Integer rvMode;

    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
    Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_home);
        rcv_requestList = findViewById(R.id.rcv_requestList);

        tv_pending = findViewById(R.id.tv_pending);
        tv_ongoing = findViewById(R.id.tv_ongoing);
        tv_finished = findViewById(R.id.tv_finished);
        tv_cancelled = findViewById(R.id.tv_cancelled);

        home =findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AssignHomeActivity.this, OpHomeActivity.class));
                finishAffinity();
            }
        });

        Intent intent = getIntent();
        Integer callmode = intent.getIntExtra("callmode",1);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(AssignHomeActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        rvPendingLoad(callmode);
        c1();



        tv_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rvPendingLoad(callmode);
                c1();
            }
        });
        tv_ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvOngoingLoad(callmode);
                c2();



            }
        });
        tv_finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c3();
                rvCancelledCompletedLoad(callmode,3);

            }
        });
        tv_cancelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c4();
                rvCancelledCompletedLoad(callmode,4);

            }
        });

    }

    void rvPendingLoad(Integer callmode){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssignHomeActivity.this);
        rcv_requestList.setLayoutManager(linearLayoutManager);

        Call<ArrayList<PassengerReqResponses>> call = RestClient.getRetrofitClient().create(RestInterface.class)
                .getPReqs(securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                        new PassengerRequestsModel(securedSharedPreferenceUtils.getLoginData().getStationId(), callmode, 1));
        call.enqueue(new Callback<ArrayList<PassengerReqResponses>>() {
            @Override
            public void onResponse(Call<ArrayList<PassengerReqResponses>> call, Response<ArrayList<PassengerReqResponses>> response) {
                if(response.code()==200){


                    UnassignedRequestListAdapter unassignedRequestListAdapter =  new UnassignedRequestListAdapter(AssignHomeActivity.this,response.body());
                    rcv_requestList.setAdapter(unassignedRequestListAdapter);

                    System.out.println("Requests Unassigned :"+response.body().toString());
                }
                if(response.code()==401){

                    try {
                        InvalidateUser.invalidate(AssignHomeActivity.this);

                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<PassengerReqResponses>> call, Throwable t) {

            }
        });
    }

    void rvOngoingLoad(Integer callmode){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssignHomeActivity.this);
        rcv_requestList.setLayoutManager(linearLayoutManager);

        Call<ArrayList<PassengerReqResponses>> call = RestClient.getRetrofitClient().create(RestInterface.class)
                .getPReqs(securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                        new PassengerRequestsModel(securedSharedPreferenceUtils.getLoginData().getStationId(), callmode, 2));
        call.enqueue(new Callback<ArrayList<PassengerReqResponses>>() {
            @Override
            public void onResponse(Call<ArrayList<PassengerReqResponses>> call, Response<ArrayList<PassengerReqResponses>> response) {
                if(response.code()==200){


                    AssignedListAdapter assignedListAdapter =  new AssignedListAdapter(AssignHomeActivity.this,response.body());
                    rcv_requestList.setAdapter(assignedListAdapter);

                    System.out.println("Requests Unassigned :"+response.body().toString());
                }
                if(response.code()==401){

                    try {
                        InvalidateUser.invalidate(AssignHomeActivity.this);

                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<PassengerReqResponses>> call, Throwable t) {

            }
        });
    }

    void rvCompletedLoad(){}

    void rvCancelledLoad(){}

    void c1(){
        tv_pending.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_ongoing.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_finished.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_cancelled.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));

        tv_pending.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_ongoing.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_finished.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_cancelled.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
    }
    void c2(){
        tv_pending.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_ongoing.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_finished.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_cancelled.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));

        tv_pending.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_ongoing.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_finished.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_cancelled.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
    }
    void c3(){
        tv_pending.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_ongoing.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_finished.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_cancelled.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));

        tv_pending.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_ongoing.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_finished.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_cancelled.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));

    }
    void c4(){
        tv_pending.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_ongoing.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_finished.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));
        tv_cancelled.setTextColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));

        tv_pending.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_ongoing.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_finished.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.white));
        tv_cancelled.setBackgroundColor(ContextCompat.getColor(AssignHomeActivity.this, R.color.maincolor4));

    }

    void rvCancelledCompletedLoad(Integer callmode,Integer mode){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssignHomeActivity.this);
        rcv_requestList.setLayoutManager(linearLayoutManager);

        Call<ArrayList<PassengerReqResponses>> call = RestClient.getRetrofitClient().create(RestInterface.class)
                .getPReqs(securedSharedPreferenceUtils.getLoginData().getJwtToken(),new PassengerRequestsModel(securedSharedPreferenceUtils.getLoginData().getStationId(), callmode, mode));
        call.enqueue(new Callback<ArrayList<PassengerReqResponses>>() {
            @Override
            public void onResponse(Call<ArrayList<PassengerReqResponses>> call, Response<ArrayList<PassengerReqResponses>> response) {
                if(response.code()==200){


                    FinishedRequestListAdapter assignedListAdapter =  new FinishedRequestListAdapter(AssignHomeActivity.this,response.body());
                    rcv_requestList.setAdapter(assignedListAdapter);

                    //System.out.println("Requests Unassigned :"+response.body().toString());
                }
                if(response.code()==401){

                    try {
                        InvalidateUser.invalidate(AssignHomeActivity.this);

                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<PassengerReqResponses>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AssignHomeActivity.this, OpHomeActivity.class));
        finishAffinity();
    }
}