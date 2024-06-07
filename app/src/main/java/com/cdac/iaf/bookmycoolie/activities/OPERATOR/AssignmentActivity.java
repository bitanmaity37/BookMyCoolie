package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

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
import com.cdac.iaf.bookmycoolie.activities.AdminLoginActivity;
import com.cdac.iaf.bookmycoolie.models.CoolieLoad;
import com.cdac.iaf.bookmycoolie.models.CurrentPassengerRequests;
import com.cdac.iaf.bookmycoolie.models.Operator;
import com.cdac.iaf.bookmycoolie.models.RequestList;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AssignmentActivity extends AppCompatActivity {

    AutoCompleteTextView act_dd;
    ArrayList<Integer> selectedCoolieIDs = new ArrayList<>();
    Integer selectedRequestID;
    TextView tv_showreqid, tv_showreqdet, tv_selectedIDs;

    Button btn_assigncr;
    Intent intentFromRow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        tv_showreqid = findViewById(R.id.tv_showreqid);
        tv_showreqdet = findViewById(R.id.tv_showreqdet);
        tv_selectedIDs = findViewById(R.id.tv_selectedIDs);
        btn_assigncr = findViewById(R.id.btn_assigncr);




        act_dd = findViewById(R.id.act_dd);

        RequestList request;

        intentFromRow = getIntent();
        Bundle args = intentFromRow.getBundleExtra("operator");
        request=(RequestList) args.getSerializable("reqobj");

        tv_showreqid.setText("REQUEST ID: "+request.getReqId());





        ArrayList<CoolieLoad> coolies = new ArrayList<>();
        coolies.add(new CoolieLoad(1,"Ravi1", 5, false));
        coolies.add(new CoolieLoad(2,"Ravi2", 6, false));
        coolies.add(new CoolieLoad(3,"Ravi3", 1, false));
        coolies.add(new CoolieLoad(4,"Ravi4", 3, false));
        coolies.add(new CoolieLoad(5,"Vishnu1", 0, false));
        coolies.add(new CoolieLoad(6,"Vishnu2", 8, false));








        ArrayAdapter<CoolieLoad> coolieLoadArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,coolies);
        act_dd.setThreshold(0);
        act_dd.setAdapter(coolieLoadArrayAdapter);

        act_dd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                finishAffinity();
                                startActivity(new Intent(AssignmentActivity.this, AdminLoginActivity.class));
                            }
                        }).show();
            }
        });


    }
}