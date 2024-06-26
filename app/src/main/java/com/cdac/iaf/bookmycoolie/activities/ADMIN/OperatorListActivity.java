package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.Operator;
import com.cdac.iaf.bookmycoolie.recyclerviews.OperatorListAdapter;
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

public class OperatorListActivity extends AppCompatActivity {

    RecyclerView rcv_oprtrlst;
    ArrayList<Operator> operators;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_list);

        home =findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OperatorListActivity.this, AdminHomeActivity.class));
                finishAffinity();
            }
        });

        rcv_oprtrlst = findViewById(R.id.rcv_oprtrlst);
        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(OperatorListActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        operators = new ArrayList<>();

        Call<ArrayList<Operator>> call = RestClient.getRetrofitClient()
                .create(RestInterface.class)
                .getOperators(securedSharedPreferenceUtils.getLoginData().getJwtToken());

        call.enqueue(new Callback<ArrayList<Operator>>() {
            @Override
            public void onResponse(Call<ArrayList<Operator>> call, Response<ArrayList<Operator>> response) {
                if(response.code() == 200){
                    operators = response.body();
                    System.out.println("List is "+operators);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OperatorListActivity.this);
                    rcv_oprtrlst.setLayoutManager(linearLayoutManager);
                    OperatorListAdapter operatorListAdapter = new OperatorListAdapter(OperatorListActivity.this,operators);
                    rcv_oprtrlst.setAdapter(operatorListAdapter);
                }
                if(response.code()==401){

                    try {
                        InvalidateUser.invalidate(OperatorListActivity.this);

                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Operator>> call, Throwable t) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(OperatorListActivity.this, AdminHomeActivity.class));
        finishAffinity();
    }
}