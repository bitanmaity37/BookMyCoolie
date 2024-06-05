package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.Operator;
import com.cdac.iaf.bookmycoolie.recyclerviews.OperatorListAdapter;

import java.util.ArrayList;

public class OperatorListActivity extends AppCompatActivity {

    RecyclerView rcv_oprtrlst;
    ArrayList<Operator> operators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_list);

        rcv_oprtrlst = findViewById(R.id.rcv_oprtrlst);

        operators = new ArrayList<>();
        operators.add(new Operator(1001, "Rajat Agarwal", "8978976767", "abcv.kshwk@ksck.in","dhwjsnsw",3,"Durg"));
        operators.add(new Operator(1002, "Rajat Sen", "8944476767", "jjwjkj.eoepo@ksck.in","dhwjsnsw",2,"Bhillai"));
        operators.add(new Operator(1003, "Rajat Sharma", "8978123467", "123.owiuw@ksck.in","dhwjsnsw",3,"Durg"));
        operators.add(new Operator(1004, "Rajat Das", "6543976767", "cnc.fpme@ksck.in","dhwjsnsw",1,"Raipur"));
        operators.add(new Operator(1005, "Rajat Patil", "6008976767", "abcv.cbdnn@ond.in","dhwjsnsw",1,"Raipur"));
        operators.add(new Operator(1006, "Rajat Kumar", "89798098012", "bsjdkd.ekekkd@ksck.in","dhwjsnsw",3,"Durg"));
        operators.add(new Operator(1007, "Rajat Chowdhary", "7670076767", "rscvd.cncnc@ksck.in","dhwjsnsw",3,"Durg"));
        operators.add(new Operator(1008, "Rajat Shankar", "8978123767", "abchhek@ksk.in","dhwjsnsw",2,"Bhillai"));
        operators.add(new Operator(1008, "Rajat Shankar", "8978123767", "abchhek@ksk.in","dhwjsnsw",2,"Bhillai"));
        operators.add(new Operator(1008, "Rajat Shankar", "8978123767", "abchhek@ksk.in","dhwjsnsw",2,"Bhillai"));
        operators.add(new Operator(1008, "Rajat Shankar", "8978123767", "abchhek@ksk.in","dhwjsnsw",2,"Bhillai"));
        operators.add(new Operator(1008, "Rajat Shankar", "8978123767", "abchhek@ksk.in","dhwjsnsw",2,"Bhillai"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OperatorListActivity.this);
        rcv_oprtrlst.setLayoutManager(linearLayoutManager);
        OperatorListAdapter operatorListAdapter = new OperatorListAdapter(OperatorListActivity.this,operators);
        rcv_oprtrlst.setAdapter(operatorListAdapter);


    }
}