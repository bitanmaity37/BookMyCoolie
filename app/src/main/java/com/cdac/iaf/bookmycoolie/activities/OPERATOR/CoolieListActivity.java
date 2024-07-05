package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.Coolie;
import com.cdac.iaf.bookmycoolie.models.GetCoolieRequest;
import com.cdac.iaf.bookmycoolie.recyclerviews.CoolieListAdapter;
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

public class CoolieListActivity extends AppCompatActivity {

    RecyclerView rcv_cList;
    ArrayList<Coolie> coolies;

    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
    Button home;
    Intent getIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coolie_list);



        rcv_cList = findViewById(R.id.rcv_cList);

        rcv_cList = findViewById(R.id.rcv_cList);
        home =findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoolieListActivity.this, OpHomeActivity.class));
                finishAffinity();
            }
        });

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(CoolieListActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(securedSharedPreferenceUtils.getLoginData().getUserId());

        Call<ArrayList<Coolie>> call = RestClient.getRetrofitClient()
                                        .create(RestInterface.class).getCoolies(securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                                                                new GetCoolieRequest(securedSharedPreferenceUtils.getLoginData().getUserId()));
        call.enqueue(new Callback<ArrayList<Coolie>>() {
            @Override
            public void onResponse(Call<ArrayList<Coolie>> call, Response<ArrayList<Coolie>> response) {



                if(response.code() == 200){
                    coolies = response.body();

                    for (int i = 0; i < coolies.size(); i++) {
                        System.out.println("coolie user status "+coolies.get(i).getUserId()+" "+coolies.get(i).getUserStatus());
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoolieListActivity.this);
                    rcv_cList.setLayoutManager(linearLayoutManager);
                    CoolieListAdapter coolieListAdapter = new CoolieListAdapter(CoolieListActivity.this,coolies);
                    rcv_cList.setAdapter(coolieListAdapter);
                }
                if(response.code()==401){

                    try {
                        InvalidateUser.invalidate(CoolieListActivity.this);

                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            }

            @Override
            public void onFailure(Call<ArrayList<Coolie>> call, Throwable t) {

                System.out.println("In here"+t.getMessage());
                t.printStackTrace();

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CoolieListActivity.this, OpHomeActivity.class));
        finishAffinity();
    }
}