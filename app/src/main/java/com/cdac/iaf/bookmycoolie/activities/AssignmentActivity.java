package com.cdac.iaf.bookmycoolie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.AdminHomeActivity;
import com.cdac.iaf.bookmycoolie.models.CoolieLoad;
import com.cdac.iaf.bookmycoolie.models.CurrentPassengerRequests;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AssignmentActivity extends AppCompatActivity {

    MultiAutoCompleteTextView mact_dd;
    AutoCompleteTextView act_dd;
    ArrayList<Integer> selectedCoolieIDs = new ArrayList<>();
    Integer selectedRequestID;
    TextView tv_showreqid, tv_showreqdet, tv_selectedIDs;

    Button btn_assigncr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        tv_showreqid = findViewById(R.id.tv_showreqid);
        tv_showreqdet = findViewById(R.id.tv_showreqdet);
        tv_selectedIDs = findViewById(R.id.tv_selectedIDs);
        btn_assigncr = findViewById(R.id.btn_assigncr);


        mact_dd = findViewById(R.id.mact_dd);
        mact_dd.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer()); // Set tokenizer

        act_dd = findViewById(R.id.act_dd);


        ArrayList<CoolieLoad> coolies = new ArrayList<>();
        coolies.add(new CoolieLoad(1,"Ravi1", 5, false));
        coolies.add(new CoolieLoad(2,"Ravi2", 6, false));
        coolies.add(new CoolieLoad(3,"Ravi3", 1, false));
        coolies.add(new CoolieLoad(4,"Ravi4", 3, false));
        coolies.add(new CoolieLoad(5,"Vishnu1", 0, false));
        coolies.add(new CoolieLoad(6,"Vishnu2", 8, false));
        System.out.println("All Details: "+coolies.toString());

        ArrayList<CurrentPassengerRequests> crequests = new ArrayList<>();
        crequests.add(new CurrentPassengerRequests(1000,"John","02/June/2024","16:45:00",2));
        crequests.add(new CurrentPassengerRequests(1001,"Harry","05/June/2024","13:30:00",1));
        crequests.add(new CurrentPassengerRequests(1002,"Brown","11/June/2024","11:10:00",3));
        crequests.add(new CurrentPassengerRequests(1003,"Roy","21/June/2024","07:05:00",1));
        System.out.println("All Requests: "+crequests);

        ArrayAdapter<CurrentPassengerRequests> psngreqAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,crequests);
        act_dd.setThreshold(0);
        act_dd.setAdapter(psngreqAdapter);

        act_dd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRequestID = crequests.get(i).getRequestId();
                tv_showreqid.setText("REQUEST ID: "+selectedRequestID);
                tv_showreqdet.setText("Passenger: "+crequests.get(i).getPassengerName()+
                                        "\nDate: "+crequests.get(i).getDate()+" Time: "+crequests.get(i).getTime()+
                                        "\nRequirements: "+crequests.get(i).getRequirementNo());
            }
        });





        ArrayAdapter<CoolieLoad> coolieLoadArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,coolies);
        mact_dd.setThreshold(0);
        mact_dd.setAdapter(coolieLoadArrayAdapter);

        mact_dd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Integer selectedId = coolies.get(i).getId();
                if (!selectedCoolieIDs.contains(selectedId)) { // Prevent duplicates
                    selectedCoolieIDs.add(selectedId);
                    tv_selectedIDs.setText("Above request with "+selectedCoolieIDs.toString()+" will be mapped");
                   // Toast.makeText(AssignmentActivity.this, "Selected Coolie(s): " + selectedCoolieIDs, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AssignmentActivity.this, "COOLIE ALREADY ADDED", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_assigncr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder m = new MaterialAlertDialogBuilder(AssignmentActivity.this);
                m.setTitle("Alert!!!")
                        .setMessage("COOLIE ASSIGNMENT DONE WITH REQUEST ID")
                        .setPositiveButton("RETURN", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(AssignmentActivity.this, AdminHomeActivity.class));
                            }
                        }).show();
            }
        });


    }
}