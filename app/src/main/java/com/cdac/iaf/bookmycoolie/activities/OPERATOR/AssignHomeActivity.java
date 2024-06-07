package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.RequestList;
import com.cdac.iaf.bookmycoolie.recyclerviews.UnassignedRequestListAdapter;

import java.util.ArrayList;

public class AssignHomeActivity extends AppCompatActivity {

    RecyclerView rcv_requestList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_home);
        rcv_requestList = findViewById(R.id.rcv_requestList);

        ArrayList<RequestList> requests = new ArrayList<>();
        requests.add(new RequestList(1001,3,"Ramesh1",30928,"1","8928923416"));
        requests.add(new RequestList(1002,2,"Ramesh2",11728,"2","8432132352"));
        requests.add(new RequestList(1003,1,"Ramesh3",11456,"1","8928923455"));
        requests.add(new RequestList(1004,1,"Ramesh4",12752,"3","8928726522"));
        requests.add(new RequestList(1005,1,"Ramesh5",92524,"7","8928123456"));
        requests.add(new RequestList(1006,2,"Ramesh6",12000,"1","8928900911"));
        requests.add(new RequestList(1007,3,"Ramesh7",11001,"8","8928977777"));
        requests.add(new RequestList(1008,2,"Ramesh8",22002,"9","7890123452"));
        requests.add(new RequestList(1009,2,"Ramesh9",34341,"1","9081235654"));
        requests.add(new RequestList(1010,2,"Ramesh10",9211,"11","6789012321"));
        requests.add(new RequestList(1011,1,"Ramesh11",92833,"11","7876712155"));
        requests.add(new RequestList(1012,1,"Ramesh12",300,"10","6786782231"));
        requests.add(new RequestList(1013,3,"Ramesh13",328,"11","7801906531"));
        requests.add(new RequestList(1014,3,"Ramesh14",318,"1","9011255244"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AssignHomeActivity.this);
        rcv_requestList.setLayoutManager(linearLayoutManager);
        UnassignedRequestListAdapter unassignedRequestListAdapter =  new UnassignedRequestListAdapter(AssignHomeActivity.this,requests);
        rcv_requestList.setAdapter(unassignedRequestListAdapter);


    }
}