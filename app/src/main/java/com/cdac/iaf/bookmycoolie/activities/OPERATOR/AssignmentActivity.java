package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.AssignCoolieToPassngrRequest;
import com.cdac.iaf.bookmycoolie.models.FreeCoolieRequest;
import com.cdac.iaf.bookmycoolie.models.FreeCoolieResponse;
import com.cdac.iaf.bookmycoolie.models.PassengerReqResponses;
import com.cdac.iaf.bookmycoolie.recyclerviews.SelectedCoolieAdapter;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.TempTokenProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentActivity extends AppCompatActivity {

    AutoCompleteTextView mact_dd;
    ArrayList<Integer> selectedCoolieIDs = new ArrayList<>();
    ArrayList<String> selectedOpts = new ArrayList<>();
    Integer selectedRequestID;
    TextView tv_showreqid, tv_showreqdet,noCoolietv;

    Button btn_assigncr;
    Intent intentFromRow;
    String abc = null;
    ArrayList<FreeCoolieResponse> coolies = new ArrayList<>();
    ArrayList<FreeCoolieResponse> newCoolie = new ArrayList<>();
    RecyclerView rvcoolie;

    Button btn_getCoolie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        tv_showreqid = findViewById(R.id.tv_showreqid);
        tv_showreqdet = findViewById(R.id.tv_showreqdet);
        btn_assigncr = findViewById(R.id.btn_assigncr);
        rvcoolie = findViewById(R.id.rvcoolie);
        noCoolietv = findViewById(R.id.noCoolietv);
        mact_dd = findViewById(R.id.mact_dd);
        /**
         * Data coming from previous activity**/
        PassengerReqResponses request;
        intentFromRow = getIntent();
        Bundle args = intentFromRow.getBundleExtra("operator");
        request=(PassengerReqResponses) args.getSerializable("reqobj");

        /**
         * Decoration**/
        String a = request.getBookingTentativeStartTime();
        String b = request.getBookingTentativeEndTime();

        if (a != null && b!= null){
            a="From Date: "+a.substring(0,10)+" Time: "+a.substring(11,16);
            b="To Date "+b.substring(0,10)+" Time: "+b.substring(11,16);
        } else {
            a="TO DATE NOT AVAILABLE";
            b="FROM DATE NOT AVAILABLE";
        }

        String details=     "DETAILS OF REQUEST:"+
                            "\n\nSERVICE TYPE: "+request.getServiceTypeName()+
                            "\n"+a+
                            "\n"+b+
                            "\nPICK UP FROM: "+request.getStationAreaPickupFromName()+
                            "\nDROP AT: "+request.getStationAreaDropAtName()+
                            "\nBAGS: "+request.getNoOfBags()+" WEIGHT: "+request.getApproxTotalWeightage()+" kgs "+
                            "\nBOOKING DATE: "+request.getBookingDate().substring(0,10);

        tv_showreqid.setText("REQUEST ID: "+request.getPassengerRequestId()+" \nNAME: "+request.getPassengerName()+" FOR: "+request.getBookingFor());
        tv_showreqdet.setText(details);




        /**
         * Call to get Free Coolies **/
        Call<ArrayList<FreeCoolieResponse>> call = RestClient.getRetrofitClient().create(RestInterface.class).getFCoolies(
                                            new TempTokenProvider().returnToken(),new FreeCoolieRequest(1));

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
                                        .create(RestInterface.class).mapCoolie(new TempTokenProvider().returnToken(),
                                                new AssignCoolieToPassngrRequest(request.getPassengerRequestId(), selectedCoolieIDs));

                                call1.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        System.out.println("Response code after map " + response.code());
                                        try {
                                            System.out.println("Response Body " + response.body().string());
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
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
            }

            @Override
            public void onFailure(Call<ArrayList<FreeCoolieResponse>> call, Throwable t) {
                System.out.println("In Failure "+t.toString());
            }
        });

    }




    /*MaterialAlertDialogBuilder m = new MaterialAlertDialogBuilder(AssignmentActivity.this);
                                            m.setTitle("Alert!!!")
                                            .setMessage("COOLIE ASSIGNMENT DONE WITH REQUEST ID")
                                            .setPositiveButton("RETURN", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                    finishAffinity();
                                                    startActivity(new Intent(AssignmentActivity.this, AdminLoginActivity.class));
                                                    }
                                              }).show();*/

}