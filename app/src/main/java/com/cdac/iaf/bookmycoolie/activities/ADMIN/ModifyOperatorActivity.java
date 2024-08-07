package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.OpHomeActivity;
import com.cdac.iaf.bookmycoolie.models.EditOpRequest;
import com.cdac.iaf.bookmycoolie.models.Operator;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyOperatorActivity extends AppCompatActivity {
    Intent intentFromRow;

    TextInputEditText tied_name;
    TextInputEditText tied_ophn;
    TextInputLayout til_name, til_ophn;

    Button btn_oprtrupdt;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
    Operator operator;

    TextView opid, stn, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_operator);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(ModifyOperatorActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tied_name = findViewById(R.id.tied_name);
        tied_ophn = findViewById(R.id.tied_ophn);
        til_name = findViewById(R.id.til_name);
                til_ophn = findViewById(R.id.til_ophn);

        btn_oprtrupdt = findViewById(R.id.btn_oprtrupdt);

        opid = findViewById(R.id.opid);
                stn = findViewById(R.id.stn);
        email = findViewById(R.id.email);

        intentFromRow = getIntent();


        Bundle args = intentFromRow.getBundleExtra("operator");
        operator=(Operator) args.getSerializable("opobj");
        System.out.println("Editing +++++++++++++"+operator);

        tied_name.setText(operator.getUserName());
        tied_ophn.setText(operator.getUserMobile());
        opid.setText(operator.getUserId().toString());
        stn.setText(operator.getStationName());
        email.setText(operator.getUserEmailId());

        btn_oprtrupdt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(validator()){
                    Call<SimpleResponse> call = RestClient.getRetrofitClient().create(RestInterface.class)
                            .modOps(securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                                    new EditOpRequest(operator.getUserId(),
                                            1,
                                            tied_name.getText().toString().trim(),
                                            tied_ophn.getText().toString().trim()));
                    ProgressDialog progressDialog = new ProgressDialog(ModifyOperatorActivity.this);
                    progressDialog.setTitle("PLEASE WAIT");
                    progressDialog.setMessage("UPDATING OPERATOR");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    call.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {

                            if(response.code()==200){
                                progressDialog.dismiss();
                                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ModifyOperatorActivity.this);
                                materialAlertDialogBuilder.setTitle("SUCCESS!!")
                                        .setMessage("OPERATOR DETAILS UPDATED")
                                        .setCancelable(false)
                                        .setNegativeButton("Return", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(ModifyOperatorActivity.this, AdminHomeActivity.class));
                                                dialogInterface.dismiss();
                                            }
                                        }).show();

                            }
                            if(response.code()==401){

                                progressDialog.dismiss();
                                try {
                                    InvalidateUser.invalidate(ModifyOperatorActivity.this);

                                } catch (GeneralSecurityException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    public Boolean validator(){
        boolean isValid = true;

        if(tied_ophn.getText().toString().matches(new RegEx().mobilePattern) && !tied_ophn.getText().toString().isEmpty()){
            til_ophn.setError("");
        }
        else{
            isValid = false;
            til_ophn.setError("PHONE NUMBER IS INVALID OR BLANK");
        }


        if(!tied_name.getText().toString().isEmpty() && tied_name.getText().toString().matches(new RegEx().namePattern)){
            til_name.setError("");
        }
        else {
            isValid = false;
            til_name.setError("NAME ID IS INVALID OR BLANK");
        }

        return isValid;

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ModifyOperatorActivity.this,AdminHomeActivity.class));
        finishAffinity();
    }
}