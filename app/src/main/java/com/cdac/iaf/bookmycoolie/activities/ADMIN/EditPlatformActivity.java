package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.AssignHomeActivity;
import com.cdac.iaf.bookmycoolie.models.Coolie;
import com.cdac.iaf.bookmycoolie.models.STATION.AllStationResponse;
import com.cdac.iaf.bookmycoolie.models.STATION.AreaMasterMappingModels;
import com.cdac.iaf.bookmycoolie.models.STATION.StationAreaMasterMapping;
import com.cdac.iaf.bookmycoolie.models.StationEditAddAreaResponse;
import com.cdac.iaf.bookmycoolie.recyclerviews.AddedPlatformAdapter;
import com.cdac.iaf.bookmycoolie.recyclerviews.AddedPlatformEditAdapter;
import com.cdac.iaf.bookmycoolie.recyclerviews.StationChildAdapter;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.RegEx;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPlatformActivity extends AppCompatActivity {

    Intent intentFromRow;
    AllStationResponse stationResponse;

    TextView tied_stnname;
    TextInputLayout til_pltfrm;
    TextInputEditText tied_platform;
    Button btn_addpltfrm;
    RecyclerView rv_pltfrm_edit_old;
    RecyclerView rv_pltfrm_edit_new;
    Button btn_addoptsave;

    ArrayList<AreaMasterMappingModels> platforms;
    ArrayList<String> pnames;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_edit_platform);
        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(EditPlatformActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        intentFromRow = getIntent();
        Bundle args = intentFromRow.getBundleExtra("bundle");
        stationResponse = (AllStationResponse) args.getSerializable("selectedStation");
        System.out.println("stationresponse "+stationResponse);

        tied_stnname = findViewById(R.id.tied_stnname);
        til_pltfrm = findViewById(R.id.til_pltfrm);
        tied_platform = findViewById(R.id.tied_platform);
        btn_addpltfrm = findViewById(R.id.btn_addpltfrm);
        rv_pltfrm_edit_old = findViewById(R.id.rv_pltfrm_edit_old);
        rv_pltfrm_edit_new = findViewById(R.id.rv_pltfrm_edit_new);
        btn_addoptsave = findViewById(R.id.btn_addoptsave);
        platforms = new ArrayList<>();
        pnames = new ArrayList<>();

        tied_stnname.setText(stationResponse.getStationName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditPlatformActivity.this);
        rv_pltfrm_edit_old.setLayoutManager(linearLayoutManager);
        StationChildAdapter stationChildAdapter = new StationChildAdapter(EditPlatformActivity.this, stationResponse.getAreaMasterMappingModels(),true);
        rv_pltfrm_edit_old.setAdapter(stationChildAdapter);

        btn_addpltfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("In Activity before "+platforms.toString());
                if(tied_platform.getText().toString().matches(new RegEx().platformPattern) && !tied_platform.getText().toString().isEmpty()){
                    if(!pnames.contains(tied_platform.getText().toString())){
                        til_pltfrm.setError("");
                        platforms.add(new AreaMasterMappingModels(true,tied_platform.getText().toString()));
                        pnames.add(tied_platform.getText().toString());

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EditPlatformActivity.this);
                        rv_pltfrm_edit_new.setLayoutManager(linearLayoutManager);
                        AddedPlatformEditAdapter adapter = new AddedPlatformEditAdapter(EditPlatformActivity.this,platforms, pnames);
                        rv_pltfrm_edit_new.setAdapter(adapter);
                        tied_platform.setText("");
                        tied_platform.setSelected(false);

                        System.out.println("In Activity after "+platforms.toString());

                    }
                    else {
                        // Toast.makeText(AddStationActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
                        til_pltfrm.setError("ALREADY ADDED");
                    }
                }
                else{
                    til_pltfrm.setError("PLATFORM NAME IS INVALID OR BLANK");
                }



            }
        });

        btn_addoptsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(EditPlatformActivity.this);
                progressDialog.setTitle("PLEASE WAIT");
                progressDialog.setMessage("ADDING MORE AREAS");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Call<StationEditAddAreaResponse> call = RestClient.getRetrofitClient().create(RestInterface.class)
                        .addMoreAreaDuringEdit(securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                                new AllStationResponse(stationResponse.getStationId(),platforms));
                call.enqueue(new Callback<StationEditAddAreaResponse>() {
                    
                    @Override
                    public void onResponse(Call<StationEditAddAreaResponse> call, Response<StationEditAddAreaResponse> response) {

                        System.out.println(response.code()+" Response bad");
                        if(response.code()==200){
                            if (response.body().getMessage().equals("Station areas are added successfully")){
                                progressDialog.dismiss();
                                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(EditPlatformActivity.this);
                                materialAlertDialogBuilder.setTitle("SUCCESS!!")
                                        .setMessage(response.body().getMessage())
                                        .setCancelable(false)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(EditPlatformActivity.this, StationListActivity.class));
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            } else if (response.body().getMessage().equals("All the Station Areas are already exist")) {
                                progressDialog.dismiss();
                                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(EditPlatformActivity.this);
                                materialAlertDialogBuilder.setTitle("FAILED!!")
                                        .setMessage(response.body().getMessage()+"\n"+response.body().getAlreadyExitList())
                                        .setCancelable(true)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            } else if (response.body().getMessage().equals("Some Station areas are added successfully")) {
                                progressDialog.dismiss();
                                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(EditPlatformActivity.this);
                                materialAlertDialogBuilder.setTitle("ALERT!!")
                                        .setMessage(response.body().getMessage()+"\n"+response.body().getAlreadyExitList())
                                        .setCancelable(false)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(EditPlatformActivity.this, StationListActivity.class));
                                                dialogInterface.dismiss();
                                            }
                                        }).show();

                            }
                        }
                        if(response.code()==401){

                            try {
                                InvalidateUser.invalidate(EditPlatformActivity.this);

                            } catch (GeneralSecurityException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<StationEditAddAreaResponse> call, Throwable t) {
                        System.out.println("Error "+t.getMessage()+" "+t);

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditPlatformActivity.this,StationListActivity.class));
        finishAffinity();
    }
}