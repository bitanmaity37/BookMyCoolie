package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.AssignCoolieToPassngrRequest;
import com.cdac.iaf.bookmycoolie.models.CancelReqReqest;
import com.cdac.iaf.bookmycoolie.models.FreeCoolieRequest;
import com.cdac.iaf.bookmycoolie.models.FreeCoolieResponse;
import com.cdac.iaf.bookmycoolie.models.PassengerReqResponses;
import com.cdac.iaf.bookmycoolie.recyclerviews.SelectedCoolieAdapter;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.cdac.iaf.bookmycoolie.utils.TimeConversionUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentActivity extends AppCompatActivity {

    AutoCompleteTextView mact_dd;
    ArrayList<Integer> selectedCoolieIDs = new ArrayList<>();
    ArrayList<String> selectedOpts = new ArrayList<>();
    Integer selectedRequestID;
    TextView noCoolietv;

    Button btn_assigncr;
    Intent intentFromRow;
    String abc = null;
    ArrayList<FreeCoolieResponse> coolies = new ArrayList<>();
    ArrayList<FreeCoolieResponse> newCoolie = new ArrayList<>();
    RecyclerView rvcoolie;

    Button btn_getCoolie, btn_cancel, home;
    Integer serviceMode;

    TextView reqid, reqtype, frmdt, todt,frmtime,totime,pickup,
            drop, bags, weight, name, bookfor, bookdt, price, pnr, trnno, trnname;

    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
    ImageView markcoolie, markcart, markwc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        home =findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AssignmentActivity.this, OpHomeActivity.class));
                finishAffinity();
            }
        });

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(AssignmentActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        markcoolie = findViewById(R.id.markcoolie);
                markcart = findViewById(R.id.markcart);
        markwc = findViewById(R.id.markwc);
        reqid = findViewById(R.id.reqid);
        reqtype = findViewById(R.id.reqtype);
        frmdt = findViewById(R.id.frmdt);
        todt = findViewById(R.id.todt);
        frmtime = findViewById(R.id.frmtime);
        totime = findViewById(R.id.totime);
        pickup = findViewById(R.id.pickup);
        drop = findViewById(R.id.drop);
        bags = findViewById(R.id.bags);
        weight = findViewById(R.id.weight);
        name = findViewById(R.id.name);
        bookfor = findViewById(R.id.bookfor);
        price = findViewById(R.id.price);


        pnr = findViewById(R.id.pnr);
                trnno = findViewById(R.id.trnno);
        trnname = findViewById(R.id.trnname);

        btn_assigncr = findViewById(R.id.btn_assigncr);
        rvcoolie = findViewById(R.id.rvcoolie);
        noCoolietv = findViewById(R.id.noCoolietv);
        mact_dd = findViewById(R.id.mact_dd);
        btn_cancel = findViewById(R.id.btn_cancel);
        /**
         * Data coming from previous activity**/
        PassengerReqResponses request;
        intentFromRow = getIntent();
        Bundle args = intentFromRow.getBundleExtra("operator");
        request=(PassengerReqResponses) args.getSerializable("reqobj");
        serviceMode = intentFromRow.getIntExtra("serviceMode",0);

        /**
         * Decoration**/
        String a = request.getBookingTentativeStartTime();
        String b = request.getBookingTentativeEndTime();

        if (a != null && b!= null){
            /*a="From Date: "+a.substring(0,10)+" Time: "+a.substring(11,16);
            b="To Date "+b.substring(0,10)+" Time: "+b.substring(11,16);*/
            frmdt.setText(TimeConversionUtil.getFullDate2(a.substring(0,10)));
            todt.setText(TimeConversionUtil.getFullDate2(b.substring(0,10)));
            frmtime.setText(a.substring(11,16));
            totime.setText(b.substring(11,16));
        } else {
            a="TO DATE NOT AVAILABLE";
            b="FROM DATE NOT AVAILABLE";

            frmdt.setText("NA");
            todt.setText("NA");
            frmtime.setText("NA");
            totime.setText("NA");

        }

        /*String details=     "DETAILS OF REQUEST:"+
                            "\n\nSERVICE TYPE: "+request.getServiceTypeName()+
                            "\n"+a+
                            "\n"+b+
                            "\nPICK UP FROM: "+request.getStationAreaPickupFromName()+
                            "\nDROP AT: "+request.getStationAreaDropAtName()+
                            "\nBAGS: "+request.getNoOfBags()+" WEIGHT: "+request.getApproxTotalWeightage()+" kgs "+
                            "\nBOOKING DATE: "+request.getBookingDate().substring(0,10);*/

        reqid.setText(request.getPassengerRequestId().toString());
        reqtype.setText(request.getServiceTypeName());
        pickup.setText(request.getStationAreaPickupFromName());
        drop.setText(request.getStationAreaDropAtName());
        bags.setText(request.getNoOfBags().toString());
        weight.setText(request.getApproxTotalWeightage().toString());
        name.setText(request.getPassengerName());
        bookfor.setText(request.getBookingFor());

        price.setText("200 approx.");

        pnr.setText("503848929294848");
                trnno.setText("12312");
        trnname.setText("AZAD HIND EXP.");

        switch (request.getServiceTypeName()){
            case "COOLIE":
                markcoolie.setVisibility(View.VISIBLE);
                markcart.setVisibility(View.GONE);
                markwc.setVisibility(View.GONE);
                break;
            case "CART":
                markcoolie.setVisibility(View.GONE);
                markcart.setVisibility(View.VISIBLE);
                markwc.setVisibility(View.GONE);
                break;
            case "WHEEL CHAIR":
                markcoolie.setVisibility(View.GONE);
                markcart.setVisibility(View.GONE);
                markwc.setVisibility(View.VISIBLE);
                break;
        }





        /**
         * Call to get Free Coolies **/
        Call<ArrayList<FreeCoolieResponse>> call = RestClient.getRetrofitClient().create(RestInterface.class).getFCoolies(
                                            securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                new FreeCoolieRequest(securedSharedPreferenceUtils.getLoginData().getStationId()));

        call.enqueue(new Callback<ArrayList<FreeCoolieResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<FreeCoolieResponse>> call, Response<ArrayList<FreeCoolieResponse>> response) {

                System.out.println("response code "+response.code());
                if(response.code()==200){

                    if(response.body().size()==0){

                        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AssignmentActivity.this);
                        materialAlertDialogBuilder.setTitle("ALERT!")
                                        .setCancelable(false)
                                                .setMessage("NO COOLIE AVAILABLE, TRY AFTER SOME TIME")
                                                        .setNegativeButton("RETURN", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                startActivity(new Intent(AssignmentActivity.this, OpHomeActivity.class));
                                                                dialogInterface.dismiss();
                                                            }
                                                        }).show();
                    }

                    System.out.println("Coolies are "+response.body().toString()+" "+response.body().size());
                    coolies = response.body();
                    ArrayAdapter<FreeCoolieResponse> coolieLoadArrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,coolies);
                    mact_dd.setThreshold(0);
                    mact_dd.setAdapter(coolieLoadArrayAdapter);
                    mact_dd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            System.out.println("after click "+i+" "+l);
                            Integer selectedId = coolies.get(i).getCoolieId();
                            String s = coolies.get(i).toString();
                            if (!selectedCoolieIDs.contains(selectedId)) { // Prevent duplicates
                                selectedCoolieIDs.add(selectedId);
                                newCoolie.add(coolies.get(i));
                              //  tv_selectedIDs.setText("Above request with Coolie IDs --> "+selectedCoolieIDs.toString()+" will be mapped");
                                if (selectedCoolieIDs.size() == 0 && newCoolie.size() == 0){
                                    noCoolietv.setVisibility(View.VISIBLE);

                                } else {
                                    noCoolietv.setVisibility(View.GONE);
                                }

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssignmentActivity.this);
                                rvcoolie.setLayoutManager(linearLayoutManager);
                                SelectedCoolieAdapter selectedCoolieAdapter = new SelectedCoolieAdapter(AssignmentActivity.this,selectedCoolieIDs, newCoolie);
                                rvcoolie.setAdapter(selectedCoolieAdapter);

                            }
                            else{
                                Toast.makeText(AssignmentActivity.this, "COOLIE ALREADY ADDED", Toast.LENGTH_SHORT).show();
                            }
                            //updateMultiAutoCompleteTextView();
                        }
                    });

                    btn_assigncr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(selectedCoolieIDs.size()==0){
                                Toast.makeText(AssignmentActivity.this, "PLEASE SELECT A COOLIE", Toast.LENGTH_LONG).show();
                            }
                            else {
                                System.out.println("Selected IDs: --------" + selectedCoolieIDs.toString());
                                Call<ResponseBody> call1 = RestClient.getRetrofitClient()
                                        .create(RestInterface.class).mapCoolie(securedSharedPreferenceUtils.getLoginData().getJwtToken(),
                                                new AssignCoolieToPassngrRequest(request.getPassengerRequestId(), selectedCoolieIDs));
                                call1.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        System.out.println("Response code after map " + response.code());
                                        try {
                                            System.out.println("Response Body " + response.body().string());

                                            MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AssignmentActivity.this);
                                            materialAlertDialogBuilder.setMessage("PASSENGER REQUEST IS MAPPED TO COOLIES")
                                                    .setTitle("SUCCESS!!")
                                                    .setCancelable(false)
                                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            startActivity(new Intent(AssignmentActivity.this, AssignHomeActivity.class).putExtra("serviceMode",serviceMode));
                                                            dialogInterface.dismiss();
                                                        }
                                                    }).show();

                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        if(response.code()==401){

                                            try {
                                                InvalidateUser.invalidate(AssignmentActivity.this);

                                            } catch (GeneralSecurityException e) {
                                                throw new RuntimeException(e);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        System.out.println("Response error after map: "+ t);

                                    }
                                });
                            }
                        }
                    });
                }
                if(response.code()==401){

                    try {
                        InvalidateUser.invalidate(AssignmentActivity.this);

                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<FreeCoolieResponse>> call, Throwable t) {
                System.out.println("In Failure "+t.toString());
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> call2 = RestClient.getRetrofitClient().create(RestInterface.class)
                        .cancelPReq(securedSharedPreferenceUtils.getLoginData().getJwtToken(),new CancelReqReqest(request.getPassengerRequestId()));
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200 || response.code()==202){
                            try {
                                System.out.println("Response code after delete "+response.code()+" "+response.body().string());
                                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AssignmentActivity.this);
                                materialAlertDialogBuilder.setMessage("PASSENGER REQUEST IS CANCELLED")
                                        .setTitle("ALERT!!")
                                        .setCancelable(false)
                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                startActivity(new Intent(AssignmentActivity.this, AssignHomeActivity.class).putExtra("serviceMode",serviceMode));
                                                dialogInterface.dismiss();
                                            }
                                        }).show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        if(response.code()==401){

                            try {
                                InvalidateUser.invalidate(AssignmentActivity.this);

                            } catch (GeneralSecurityException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AssignmentActivity.this, OpHomeActivity.class));
        finishAffinity();
    }
}