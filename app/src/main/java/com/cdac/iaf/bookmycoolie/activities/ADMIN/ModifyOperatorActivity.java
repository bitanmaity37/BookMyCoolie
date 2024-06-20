package com.cdac.iaf.bookmycoolie.activities.ADMIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.Operator;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class ModifyOperatorActivity extends AppCompatActivity {
    Intent intentFromRow;

    TextInputEditText tied_name;
            TextInputEditText tied_ophn;
    TextInputEditText tied_oemail;
            AutoCompleteTextView act_stn;
    Button btn_oprtrupdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_operator);

        tied_name = findViewById(R.id.tied_name);
                tied_ophn = findViewById(R.id.tied_ophn);
        tied_oemail = findViewById(R.id.tied_oemail);
                act_stn = findViewById(R.id.act_stn);
        btn_oprtrupdt = findViewById(R.id.btn_oprtrupdt);

        intentFromRow = getIntent();

        Operator operator;
        Bundle args = intentFromRow.getBundleExtra("operator");
        operator=(Operator) args.getSerializable("opobj");
        System.out.println("Editing +++++++++++++"+operator);

        tied_name.setText(operator.getUserName());
        tied_oemail.setText(operator.getUserEmailId());
        tied_ophn.setText(operator.getUserMobile());
        act_stn.setText(operator.getStationName());

        btn_oprtrupdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(ModifyOperatorActivity.this);
                materialAlertDialogBuilder.setTitle("SUCCESS!!!")
                        .setMessage("OPERATOR DETAILS UPDATED SUCCESSFULLY")
                        .setCancelable(false)
                        .setPositiveButton("HOME", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finishAffinity();
                                startActivity(new Intent(ModifyOperatorActivity.this, AdminHomeActivity.class));
                            }
                        }).show();
            }
        });

    }
}