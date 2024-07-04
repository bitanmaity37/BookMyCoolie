package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.STATION.AddStationRequest;
import com.cdac.iaf.bookmycoolie.models.STATION.StationAreaMasterMapping;
import com.cdac.iaf.bookmycoolie.models.SimpleResponse;
import com.cdac.iaf.bookmycoolie.recyclerviews.AddedPlatformAdapter;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStationActivity extends AppCompatActivity {

    Button btn_addpltfrm, btn_addoptsave;
    TextInputEditText tied_platform, tied_stnname, tied_stncode, tied_cartc, tied_chairc;

    ArrayList<StationAreaMasterMapping> platforms;
    ArrayList<String> pnames;
    RecyclerView rv_pltfrm;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(AddStationActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tied_platform = findViewById(R.id.tied_platform);
        btn_addpltfrm = findViewById(R.id.btn_addpltfrm);
        rv_pltfrm = findViewById(R.id.rv_pltfrm);
        btn_addoptsave = findViewById(R.id.btn_addoptsave);
        tied_stnname = findViewById(R.id.tied_stnname);
        tied_stncode = findViewById(R.id.tied_stncode);
        tied_cartc = findViewById(R.id.tied_cartc);
        tied_chairc = findViewById(R.id.tied_chairc);



        platforms = new ArrayList<>();
        pnames = new ArrayList<>();

        btn_addpltfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("In Activity before "+platforms.toString());
                if(!pnames.contains(tied_platform.getText().toString())){
                    platforms.add(new StationAreaMasterMapping(tied_platform.getText().toString()));
                    pnames.add(tied_platform.getText().toString());

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddStationActivity.this);
                    rv_pltfrm.setLayoutManager(linearLayoutManager);
                    AddedPlatformAdapter adapter = new AddedPlatformAdapter(AddStationActivity.this,platforms, pnames);
                    rv_pltfrm.setAdapter(adapter);
                    tied_platform.setText("");
                    tied_platform.setSelected(false);

                    System.out.println("In Activity after "+platforms.toString());
                }
                else {
                    Toast.makeText(AddStationActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_addoptsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddStationActivity.this, "In Save "+platforms.toString(), Toast.LENGTH_LONG).show();

                Call<SimpleResponse> call = RestClient.getRetrofitClient().create(RestInterface.class).addStation(securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                        new AddStationRequest(Integer.valueOf(tied_cartc.getText().toString()),
                                Integer.valueOf(tied_chairc.getText().toString()),
                                tied_stncode.getText().toString(),
                                tied_stnname.getText().toString(),
                                true,
                                1,1,1,1,
                                platforms));
                call.enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                        if(response.code()==200){
                            Toast.makeText(AddStationActivity.this, "STATION ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                            clearFields();
                            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AddStationActivity.this);
                            materialAlertDialogBuilder.setTitle("SUCCESS!!")
                                    .setMessage("STATION ADDED")
                                    .setCancelable(true)
                                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            startActivity(new Intent(AddStationActivity.this,AdminHomeActivity.class));
                                            dialogInterface.dismiss();
                                        }
                                    }).show();
                        }
                        if(response.code()==400){
                            Toast.makeText(AddStationActivity.this, "400", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {
                        System.out.println("In Error "+ t.getMessage());
                    }
                });
            }
        });


    }

    void clearFields(){
        tied_stncode.setPressed(false);
        tied_stncode.setSelected(false);
        tied_stncode.setText("");

        tied_stnname.setPressed(false);
        tied_stnname.setSelected(false);
        tied_stnname.setText("");

        tied_cartc.setPressed(false);
        tied_cartc.setSelected(false);
        tied_cartc.setText("");

        tied_chairc.setPressed(false);
        tied_chairc.setSelected(false);
        tied_chairc.setText("");
        rv_pltfrm.setAdapter(null);
    }
}