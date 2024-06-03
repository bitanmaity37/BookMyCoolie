package com.cdac.iaf.bookmycoolie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdac.iaf.bookmycoolie.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

public class TrackingActivity extends AppCompatActivity {

    TextInputEditText tied_rpname;
            TextInputEditText tied_pphnsrch;
    TextInputEditText tied_reqid;
     Button btn_rqstsrch;
    TextView textView2;

    ShapeableImageView shapeableImageView3;
    LinearLayout ll_asgndcdtls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        tied_rpname = findViewById(R.id.tied_rpname);
        tied_pphnsrch = findViewById(R.id.tied_pphnsrch);
        tied_reqid = findViewById(R.id.tied_reqid);
        btn_rqstsrch = findViewById(R.id.btn_rqstsrch);
        textView2 = findViewById(R.id.textView2);
        shapeableImageView3 = findViewById(R.id.shapeableImageView3);
        ll_asgndcdtls  = findViewById(R.id.ll_asgndcdtls);

        shapeableImageView3.setVisibility(View.GONE);
        ll_asgndcdtls.setVisibility(View.GONE);

        btn_rqstsrch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tied_reqid.getText().toString().equals("100100")){
                    tied_rpname.setText("");
                    tied_pphnsrch.setText("");
                    tied_reqid.setText("");
                    textView2.setText("YOUR COOLIE IS ASSIGNED!");
                    shapeableImageView3.setVisibility(View.VISIBLE);
                    ll_asgndcdtls.setVisibility(View.VISIBLE);
                }
                else{
                    tied_rpname.setText("");
                    tied_pphnsrch.setText("");
                    tied_reqid.setText("");
                    textView2.setText("PLEASE CHECK YOUR REQUEST ID AND TRY AGAIN");
                    shapeableImageView3.setVisibility(View.GONE);
                    ll_asgndcdtls.setVisibility(View.GONE);
                }

            }
        });
    }
}