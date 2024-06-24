package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.AdminHomeActivity;
import com.cdac.iaf.bookmycoolie.models.AttendanceCoolieResponse;
import com.cdac.iaf.bookmycoolie.models.FreeCoolieRequest;
import com.cdac.iaf.bookmycoolie.models.GetCoolieRequest;
import com.cdac.iaf.bookmycoolie.recyclerviews.CoolieAttendanceAdapter;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.cdac.iaf.bookmycoolie.utils.TempTokenProvider;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoolieAttendanceActivity extends AppCompatActivity {


    RecyclerView rv_attndnce;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coolie_attendance);
//        materialSwitch = findViewById(R.id.present);
        rv_attndnce = findViewById(R.id.rv_attndnce);

        home =findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoolieAttendanceActivity.this, OpHomeActivity.class));
                finishAffinity();
            }
        });

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(CoolieAttendanceActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("current user id"+securedSharedPreferenceUtils.getLoginData().getUserId());

        Call<ArrayList<AttendanceCoolieResponse>> call = RestClient.getRetrofitClient().create(RestInterface.class).getCforAtndnc(
                securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                new GetCoolieRequest(securedSharedPreferenceUtils.getLoginData().getUserId()));
        call.enqueue(new Callback<ArrayList<AttendanceCoolieResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<AttendanceCoolieResponse>> call, Response<ArrayList<AttendanceCoolieResponse>> response) {
                if(response.code()==200){
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CoolieAttendanceActivity.this);
                    rv_attndnce.setLayoutManager(linearLayoutManager);
                    CoolieAttendanceAdapter coolieAttendanceAdapter = new CoolieAttendanceAdapter(CoolieAttendanceActivity.this,response.body());
                    rv_attndnce.setAdapter(coolieAttendanceAdapter);
                }
                if(response.code()==401){

                    try {
                        InvalidateUser.invalidate(CoolieAttendanceActivity.this);

                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<AttendanceCoolieResponse>> call, Throwable t) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CoolieAttendanceActivity.this, OpHomeActivity.class));
        finishAffinity();
    }
}