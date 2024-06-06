package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.AddOperatorRequest;
import com.cdac.iaf.bookmycoolie.models.AddOperatorResponse;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeActivity extends AppCompatActivity {

    Button btn_addopt, btn_modopt;

    TextInputEditText tied_empid,tied_ophn,tied_oemail,tied_opwd;
      AutoCompleteTextView act_stndd;
    Button btn_addoptsave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home2);

        btn_addopt = findViewById(R.id.btn_addopt);
        btn_modopt =findViewById(R.id.btn_modopt);

        btn_modopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, OperatorListActivity.class ));
            }
        });
        btn_addopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AdminHomeActivity.this);
                View view1 = LayoutInflater.from(AdminHomeActivity.this).inflate(R.layout.operator_bottom_sheet_layout,null);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                bottomSheetDialog.show();
                tied_empid = (TextInputEditText) bottomSheetDialog.findViewById(R.id.tied_empid);
                tied_ophn = (TextInputEditText) bottomSheetDialog.findViewById(R.id.tied_ophn);
                tied_oemail = (TextInputEditText) bottomSheetDialog.findViewById(R.id.tied_oemail);
                tied_opwd = (TextInputEditText) bottomSheetDialog.findViewById(R.id.tied_opwd);
                act_stndd = (AutoCompleteTextView) bottomSheetDialog.findViewById(R.id.act_stndd);
                btn_addoptsave = (Button) bottomSheetDialog.findViewById(R.id.btn_addoptsave);

                btn_addoptsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Call<AddOperatorResponse> call = RestClient.getRetrofitClient().create(RestInterface.class)
                                                    .addOperator(new AddOperatorRequest(tied_oemail.getText().toString().trim(),
                                                            tied_empid.getText().toString().trim(),
                                                            true,
                                                            tied_ophn.getText().toString().trim(),
                                                            "Operator 1",
                                                            "12345",
                                                            1,1,1),"Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6IlJPTEVfQURNSU4iLCJzdWIiOiJrdWx2YW50a0BjZGFjLmluIiwiaWF0IjoxNzE3NjY0MjQ0LCJleHAiOjE3MTc2ODIyNDR9.-KOX0ZW6Ov6Gj2m63bMSOUcQQV1Ojmh38Vw6XNfeAfFZlv4PvAfcD9zgqUHiUgreKOtY-ZTG1LW1ftHsq8H-wQ");
                        call.enqueue(new Callback<AddOperatorResponse>() {
                            @Override
                            public void onResponse(Call<AddOperatorResponse> call, Response<AddOperatorResponse> response) {
                                System.out.println("CODE   ++++"+response.body().toString()+" "+response.code());
                            }

                            @Override
                            public void onFailure(Call<AddOperatorResponse> call, Throwable t) {

                            }
                        });
                    }
                });

                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Toast.makeText(AdminHomeActivity.this, "dismissed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}