package com.cdac.iaf.bookmycoolie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.cdac.iaf.bookmycoolie.R;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RequestCoolieActivity extends AppCompatActivity {

    TextInputEditText act_date, act_time;
    AutoCompleteTextView act_stn;
    Button btn_rqst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_coolie);

        btn_rqst = findViewById(R.id.btn_rqst);
        act_time = findViewById(R.id.act_time);

        ArrayList<String> stations = new ArrayList<>();
        stations.add("BPHB");
        stations.add("BYT");
        stations.add("DURG");
        stations.add("RAIPUR");
        stations.add("TLD");

        act_stn = findViewById(R.id.act_stn);
        ArrayAdapter<String> stationsadapter= new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,stations);
        act_stn.setThreshold(0);
        act_stn.setAdapter(stationsadapter);

        /***DATE**/
        act_date = findViewById(R.id.act_date);
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(
                        DateValidatorPointForward.now());

        MaterialDatePicker<Long> datePicker;
        datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                .build();

        act_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (datePicker.isAdded()){

                }else {
                    datePicker.show(getSupportFragmentManager(), "Date");
                }
            }
        });

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {

                Date date = new Date(selection);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                act_date.setText(simpleDateFormat.format(date));
            }
        });


        /** TIME **/
        boolean isSystem24Hour = DateFormat.is24HourFormat(this);
        int clockFormat;
        if(isSystem24Hour){

            clockFormat = TimeFormat.CLOCK_24H;

        }else{
            clockFormat = TimeFormat.CLOCK_12H;
        }

        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Time")
                .build();

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {



                if(minute == 0){
                    act_time.setText(hourOfDay+":"+minute+"0");
                }
                else {
                    act_time.setText(hourOfDay+":"+minute);
                }
            }
        }, 12, 0, false);
        timePickerDialog.setTimeInterval(1,5);

        act_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(timePickerDialog.isAdded()){

                }
                else{

                    timePickerDialog.show(getSupportFragmentManager(),"Time");
                }



            }
        });


        btn_rqst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(RequestCoolieActivity.this);
                materialAlertDialogBuilder.setTitle("THANK YOU!")
                        .setMessage("YOUR REQUEST ID 100100 WILL BE PROCESSED SHORTLY")
                        .setPositiveButton("Home", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(RequestCoolieActivity.this, HomeActivity.class));
                            }
                        })
                        .setCancelable(false)
                        .show();

            }
        });
    }
}