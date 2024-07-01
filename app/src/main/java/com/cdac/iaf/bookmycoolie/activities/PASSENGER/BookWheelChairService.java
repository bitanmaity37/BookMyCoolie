package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.CoolieRequestModel;
import com.cdac.iaf.bookmycoolie.models.CoolieResponseModel;
import com.cdac.iaf.bookmycoolie.models.StationAreaModel;
import com.cdac.iaf.bookmycoolie.models.StationModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.cdac.iaf.bookmycoolie.utils.TimeConversionUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookWheelChairService {


    ArrayList<StationAreaModel> stationAreaModelList;
    Call<ArrayList<StationAreaModel>> callGetSationArea;
    Call<ArrayList<StationModel>> callGetSationList;
    String authToken;
    ArrayList<StationModel> stationList = new ArrayList<>();
    AutoCompleteTextView autoCompleteStationList;
    AutoCompleteTextView autoCompleteStationAreaPickup;
    AutoCompleteTextView autoCompleteStationAreaDropAt;
    TextInputEditText startTimeInput;
    TextInputEditText endTimePicker;
    BottomSheetDialog coolieBottomSheetDialog;
    BottomSheetDialog cartBottomSheetDialog;
    StationModel selectedStation = new StationModel();
    StationAreaModel selectedStationAreaPickUp = new StationAreaModel();
    StationAreaModel selectedStationAreaDropAt = new StationAreaModel();
    CoolieRequestModel coolieRequestModel = new CoolieRequestModel();
    Context context;
    FragmentManager fragmentManager;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    public BookWheelChairService(Context context, String authToken, FragmentManager fragmentManager, SecuredSharedPreferenceUtils securedSharedPreferenceUtils) {
        this.authToken = authToken;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.securedSharedPreferenceUtils = securedSharedPreferenceUtils;
    }

    public void showCoolieBottomSheet() {
        coolieBottomSheetDialog = new BottomSheetDialog(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.book_chair_layout, null);
        coolieBottomSheetDialog.setContentView(view1);
        coolieBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        coolieBottomSheetDialog.show();

        getAllStation();
        setTentativeStartTimePicker();
        setTentativeEndTimePicker();
        submitBookCoolieForm();

        //getStationAreaByStationId();
    }

    public void getAllStation() {

        callGetSationList = RestClient.getRetrofitClient().create(RestInterface.class).getSationList(authToken);

        callGetSationList.enqueue(new Callback<ArrayList<StationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StationModel>> call, Response<ArrayList<StationModel>> response) {
                if(response.isSuccessful() || response.code() == 200){
                    System.out.println("station list: " + response.body());
                    stationList = response.body();
                    setStationList();
                }else{
                    Toast.makeText(context, "No Station found!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StationModel>> call, Throwable t) {
                System.out.println("station list: error " + t.getMessage());
            }
        });

    }

    public void setStationList() {
        System.out.println("setStationList: " + stationList);
        if (autoCompleteStationList == null) {
            autoCompleteStationList = coolieBottomSheetDialog.findViewById(R.id.select_station_input);
        }
        ArrayAdapter<StationModel> adapterStationList = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, stationList);
        autoCompleteStationList.setAdapter(adapterStationList);

        autoCompleteStationList.setOnItemClickListener((parent, view, position, id) -> {
            selectedStation = (StationModel) parent.getItemAtPosition(position);
            coolieRequestModel.setStationId(selectedStation.getStationId()); //setting station id in request model
            System.out.println("stationModel: " + selectedStation.getStationId());
            getStationAreaByStationId(selectedStation.getStationId());
        });
    }

    public void getStationAreaByStationId(int stationId) {
        System.out.println("getStationAreaByStationId: " + stationId);
        callGetSationArea = RestClient.getRetrofitClient().create(RestInterface.class).getStationAreaByStationId(authToken, stationId);

        callGetSationArea.enqueue(new Callback<ArrayList<StationAreaModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StationAreaModel>> call, Response<ArrayList<StationAreaModel>> response) {
                System.out.println("station area list: " + response.body().toString());
                stationAreaModelList = response.body();
                setStationAreaPickUp(stationAreaModelList);
                setStationAreaDropAt(stationAreaModelList);
            }

            @Override
            public void onFailure(Call<ArrayList<StationAreaModel>> call, Throwable t) {

            }
        });

    }

    public void setStationAreaPickUp(ArrayList<StationAreaModel> stationAreaModelList) {

        autoCompleteStationAreaPickup = coolieBottomSheetDialog.findViewById(R.id.select_pickup_input);
        ArrayAdapter<StationAreaModel> adapterPickUpArea = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, stationAreaModelList);
        autoCompleteStationAreaPickup.setAdapter(adapterPickUpArea);

        autoCompleteStationAreaPickup.setOnItemClickListener((parent, view, position, id) -> {
            selectedStationAreaPickUp = (StationAreaModel) parent.getItemAtPosition(position);
            coolieRequestModel.setStationAreaPickupFrom(selectedStationAreaPickUp.getStationAreaMasterMappingId());
            System.out.println("selectedStationAreaPickUp: " + selectedStationAreaPickUp.getStationAreaMasterMappingId());
        });

    }

    public void setStationAreaDropAt(ArrayList<StationAreaModel> stationAreaModelList) {

        autoCompleteStationAreaDropAt = coolieBottomSheetDialog.findViewById(R.id.select_dropping_input);
        ArrayAdapter<StationAreaModel> adapterDropAtArea = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, stationAreaModelList);
        autoCompleteStationAreaDropAt.setAdapter(adapterDropAtArea);

        autoCompleteStationAreaDropAt.setOnItemClickListener((parent, view, position, id) -> {
            selectedStationAreaDropAt = (StationAreaModel) parent.getItemAtPosition(position);
            coolieRequestModel.setStationAreaDropAt(selectedStationAreaDropAt.getStationAreaMasterMappingId());
            System.out.println("selectedStationAreaDropAt: " + selectedStationAreaDropAt.getStationAreaMasterMappingId());
        });

    }

    public void setTentativeStartTimePicker() {

        startTimeInput = coolieBottomSheetDialog.findViewById(R.id.approx_start_time_input);
        Calendar calendar = Calendar.getInstance();

        startTimeInput.setOnClickListener(v -> {
            MaterialTimePicker startTime = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                    .setMinute(calendar.get(Calendar.MINUTE))
                    .setTitleText("Select Start Time").build();

            startTime.show(fragmentManager, "MATERIAL_TIME_PICKER");

            startTime.addOnPositiveButtonClickListener(v1 -> {

                // Set the hour and minute from the picker
                calendar.set(Calendar.HOUR_OF_DAY, startTime.getHour());
                calendar.set(Calendar.MINUTE, startTime.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                String tentativeStartTime = TimeConversionUtil.convertTime(calendar);
                coolieRequestModel.setBookingTentativeStartTime(tentativeStartTime);


                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                String formattedDate = dateFormat.format(calendar.getTime());

                startTimeInput.setText(formattedDate);

                //Toast.makeText(context, " formattedDate = " + formattedDate, Toast.LENGTH_SHORT).show();
            });

        });

    }

    public void setTentativeEndTimePicker() {

        endTimePicker = coolieBottomSheetDialog.findViewById(R.id.approx_end_time_input);
        Calendar calendar = Calendar.getInstance();

        endTimePicker.setOnClickListener(v -> {
            MaterialTimePicker startTime = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                    .setMinute(calendar.get(Calendar.MINUTE))
                    .setTitleText("Select End Time").build();

            startTime.show(fragmentManager, "MATERIAL_TIME_PICKER");

            startTime.addOnPositiveButtonClickListener(v1 -> {


                // Set the hour and minute from the picker
                calendar.set(Calendar.HOUR_OF_DAY, startTime.getHour());
                calendar.set(Calendar.MINUTE, startTime.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                String tentativeEndTime = TimeConversionUtil.convertTime(calendar);
                coolieRequestModel.setBookingTentativeEndTime(tentativeEndTime);

                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                String formattedDate = dateFormat.format(calendar.getTime());

                endTimePicker.setText(formattedDate);

                //Toast.makeText(context, "Timestamp tentativeStartTime =" + tentativeEndTime + " formattedDate = " + formattedDate, Toast.LENGTH_SHORT).show();
            });

        });

    }

    public void submitBookCoolieForm() {
        MaterialButton submitButton = coolieBottomSheetDialog.findViewById(R.id.submit_book_coolie_form);

        if (submitButton != null) {
            submitButton.setOnClickListener(v -> {
                TextInputEditText getNoOfChairReq = coolieBottomSheetDialog.findViewById(R.id.chair_required_input);
                TextInputEditText getNoOfBags = coolieBottomSheetDialog.findViewById(R.id.no_of_bags_input);
                TextInputEditText approxWeight = coolieBottomSheetDialog.findViewById(R.id.approx_weight_input);
                if (getNoOfBags != null) {
                    coolieRequestModel.setNoOfWheelChair(Integer.parseInt(getNoOfChairReq.getText().toString()));
                }
                if (getNoOfBags != null) {
                    coolieRequestModel.setNoOfBags(Integer.parseInt(getNoOfBags.getText().toString()));
                }
                if (approxWeight != null) {
                    coolieRequestModel.setApproxTotalWeightage(Integer.parseInt(approxWeight.getText().toString()));
                }

                Date date = new Date();
                String formattedDate = TimeConversionUtil.convertTime(date);

                coolieRequestModel.setBookingDate(formattedDate);
                coolieRequestModel.setBookingFor("John Doe");
                coolieRequestModel.setBookingType("Round Trip");
                coolieRequestModel.setRecordTracking(formattedDate);
                coolieRequestModel.setServiceType(3);
                coolieRequestModel.setRequestStatus(1);
                coolieRequestModel.setUserMaster(securedSharedPreferenceUtils.getLoginData().getUserId());

                System.out.println("coolieRequestModel: " + coolieRequestModel.toString());

                Call<CoolieResponseModel> callSubmitBookCoolieForm = RestClient.getRetrofitClient().create(RestInterface.class).submitBookCoolieForm(authToken, coolieRequestModel);

                callSubmitBookCoolieForm.enqueue(new Callback<CoolieResponseModel>() {
                    @Override
                    public void onResponse(Call<CoolieResponseModel> call, Response<CoolieResponseModel> response) {
                        System.out.println("response.body().getRequestStatus(): " + response.code());
                        System.out.println("response.body().getRequestStatus(): " + response.body().getPassengerRequestId());
                        //Toast.makeText(context, "response.body().getRequestStatus(): " + response.code(), Toast.LENGTH_SHORT).show();
                        resetFormFields();
                        coolieBottomSheetDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<CoolieResponseModel> call, Throwable t) {

                    }
                });

                Toast.makeText(context, "submitBookCoolieForm", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void resetFormFields() {
        if (coolieBottomSheetDialog != null) {
            // Reset AutoCompleteTextViews
            if (autoCompleteStationList != null) {
                autoCompleteStationList.setText("");
                autoCompleteStationList.clearListSelection();
            }
            if (autoCompleteStationAreaPickup != null) {
                autoCompleteStationAreaPickup.setText("");
                autoCompleteStationAreaPickup.clearListSelection();
            }
            if (autoCompleteStationAreaDropAt != null) {
                autoCompleteStationAreaDropAt.setText("");
                autoCompleteStationAreaDropAt.clearListSelection();
            }

            // Reset TextInputEditTexts
            if (startTimeInput != null) {
                startTimeInput.setText("");
            }
            if (endTimePicker != null) {
                endTimePicker.setText("");
            }

            TextInputEditText getNoOfChairReq = coolieBottomSheetDialog.findViewById(R.id.chair_required_input);
            TextInputEditText getNoOfBags = coolieBottomSheetDialog.findViewById(R.id.no_of_bags_input);
            TextInputEditText approxWeight = coolieBottomSheetDialog.findViewById(R.id.approx_weight_input);
            if (getNoOfChairReq != null) {
                getNoOfChairReq.setText("");
            }
            if (getNoOfBags != null) {
                getNoOfBags.setText("");
            }
            if (approxWeight != null) {
                approxWeight.setText("");
            }

            // Reset request model
            coolieRequestModel = new CoolieRequestModel();
        }
    }

}
