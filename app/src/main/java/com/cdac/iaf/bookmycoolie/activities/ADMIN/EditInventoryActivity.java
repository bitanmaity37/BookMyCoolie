package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.STATION.AllStationResponse;
import com.cdac.iaf.bookmycoolie.models.SimpleResponse;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInventoryActivity extends AppCompatActivity {

    TextView tied_stnname;
    TextView tied_stncode;
    TextInputLayout til_cartc;
    TextInputEditText tied_cartc;
    TextInputLayout til_chairc;
    TextInputEditText tied_chairc;
    Button btn_saveInventory;
    Intent intent;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(EditInventoryActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tied_stnname = findViewById(R.id.tied_stnname);
                tied_stncode = findViewById(R.id.tied_stncode);
        til_cartc = findViewById(R.id.til_cartc);
                tied_cartc = findViewById(R.id.tied_cartc);
        til_chairc = findViewById(R.id.til_chairc);
                tied_chairc = findViewById(R.id.tied_chairc);
        btn_saveInventory = findViewById(R.id.btn_saveInventory);

        intent = getIntent();
        Integer stationId = intent.getIntExtra("stationId",0);
        tied_stnname.setText(stationId.toString());
        tied_stnname.setText(intent.getStringExtra("stationCode"));

        btn_saveInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(EditInventoryActivity.this);
                progressDialog.setTitle("WAIT");
                progressDialog.setMessage("Updating Inventory");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if(validator()){
                    Call<SimpleResponse> call = RestClient.getRetrofitClient().create(RestInterface.class).addInventory(
                            securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                            new AllStationResponse(stationId,
                                    Integer.valueOf(tied_cartc.getText().toString().trim()),
                                    Integer.valueOf(tied_chairc.getText().toString().trim())));
                    call.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {

                            if(response.code()==200){
                                progressDialog.dismiss();
                                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(EditInventoryActivity.this);
                                materialAlertDialogBuilder.setTitle("SUCCESS!!")
                                        .setMessage("INVENTORY UPDATED")
                                        .setCancelable(false)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(EditInventoryActivity.this,StationListActivity.class));
                                                dialogInterface.dismiss();
                                            }
                                        }).show();

                            }

                            if(response.code()==401){
                                try {
                                    InvalidateUser.invalidate(EditInventoryActivity.this);
                                } catch (GeneralSecurityException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {

                        }
                    });

                }
            }
        });



    }

    public Boolean validator(){
        boolean isValid = true;
        if(!tied_cartc.getText().toString().isEmpty() && Integer.parseInt(tied_cartc.getText().toString().trim())<999){
            til_cartc.setError("");
        }
        else {
            isValid = false;
            til_cartc.setError("NO. OF CART ATLEAST 0");
        }

        if(!tied_chairc.getText().toString().isEmpty() && Integer.parseInt(tied_chairc.getText().toString().trim())<999){
            til_chairc.setError("");
        }
        else {
            isValid = false;
            til_chairc.setError("NO. OF CHAIR ATLEAST 0");
        }

        return isValid;

    }

}