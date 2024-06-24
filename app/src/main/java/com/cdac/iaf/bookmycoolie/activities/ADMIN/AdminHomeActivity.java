package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.AdminLoginActivity;
import com.cdac.iaf.bookmycoolie.models.AddOperatorRequest;
import com.cdac.iaf.bookmycoolie.models.AddOperatorResponse;
import com.cdac.iaf.bookmycoolie.models.StationListResponse;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.cdac.iaf.bookmycoolie.utils.TempTokenProvider;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeActivity extends AppCompatActivity {

    Button btn_addopt, btn_modopt;

    TextInputEditText tied_empid,tied_ophn,tied_oemail,tied_opwd, tied_name;
      AutoCompleteTextView act_stndd;
    Button btn_addoptsave, logout;

    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    Integer stationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home2);

        btn_addopt = findViewById(R.id.btn_addopt);
        btn_modopt =findViewById(R.id.btn_modopt);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InvalidateUser.invalidate(AdminHomeActivity.this);
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(AdminHomeActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
                tied_name = (TextInputEditText) bottomSheetDialog.findViewById(R.id.tied_name);

                Call<ArrayList<StationListResponse>> call = RestClient.getRetrofitClient().create(RestInterface.class)
                                .getStation(securedSharedPreferenceUtils.getLoginData().getJwtToken());
                call.enqueue(new Callback<ArrayList<StationListResponse>>() {
                    @Override
                    public void onResponse(Call<ArrayList<StationListResponse>> call, Response<ArrayList<StationListResponse>> response) {
                        if(response.code()==200){
                            ArrayAdapter<StationListResponse> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,response.body());
                            act_stndd.setThreshold(0);
                            act_stndd.setAdapter(adapter);
                            act_stndd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                  stationId = response.body().get(i).getStationId();
                                }
                            });
                        }
                        if(response.code()==401){

                            try {
                                InvalidateUser.invalidate(AdminHomeActivity.this);

                            } catch (GeneralSecurityException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<StationListResponse>> call, Throwable t) {

                    }
                });
                btn_addoptsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Call<AddOperatorResponse> call = RestClient.getRetrofitClient().create(RestInterface.class)
                                                        .addOperator(new AddOperatorRequest(tied_oemail.getText().toString().trim(),
                                                            tied_empid.getText().toString().trim(),
                                                            true,
                                                            tied_ophn.getText().toString().trim(),
                                                            tied_name.getText().toString(),
                                                            tied_opwd.getText().toString(),
                                                            1,
                                                            2,
                                                            stationId),
                                                            securedSharedPreferenceUtils.getLoginData().getJwtToken());
                        call.enqueue(new Callback<AddOperatorResponse>() {
                            @Override
                            public void onResponse(Call<AddOperatorResponse> call, Response<AddOperatorResponse> response) {
                                System.out.println("CODE   ++++"+response.body()+" "+response.code());

                                if(response.code()==200){
                                    MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AdminHomeActivity.this);
                                    materialAlertDialogBuilder.setTitle("SUCCESS!!")
                                            .setMessage("Operator Added Successfully")
                                            .setCancelable(false)
                                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    bottomSheetDialog.dismiss();
                                                    dialogInterface.dismiss();
                                                }
                                            }).show();
                                }
                                if(response.code()==401){

                                    try {
                                        InvalidateUser.invalidate(AdminHomeActivity.this);

                                    } catch (GeneralSecurityException e) {
                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
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

    boolean doubleBackToExitPressedOnce = false;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
