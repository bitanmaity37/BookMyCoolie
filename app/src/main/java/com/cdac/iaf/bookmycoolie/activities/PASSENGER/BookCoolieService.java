package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.CoolieRequestModel;
import com.cdac.iaf.bookmycoolie.models.CoolieResponseModel;
import com.cdac.iaf.bookmycoolie.models.DynamicFare.FaresCalculation;
import com.cdac.iaf.bookmycoolie.models.StationAreaModel;
import com.cdac.iaf.bookmycoolie.models.StationModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
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

public class BookCoolieService {

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
    StationModel selectedStation = new StationModel();
    StationAreaModel selectedStationAreaPickUp = new StationAreaModel();
    StationAreaModel selectedStationAreaDropAt = new StationAreaModel();
    CoolieRequestModel coolieRequestModel = new CoolieRequestModel();
    Context context;
    FragmentManager fragmentManager;
    int userId;
    TextView approxAmount;
    TextInputEditText approxWeight;
    LinearLayout approxAmountLayout;
    TextInputEditText getTrainName;
    TextInputEditText getTrainNo;
    String userName;

    public BookCoolieService(Context context, String authToken, FragmentManager fragmentManager, int userId, String userName) {
        this.authToken = authToken;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.userId = userId;
        this.userName = userName;
    }

    public void showCoolieBottomSheet() {
        coolieBottomSheetDialog = new BottomSheetDialog(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.book_coolie_layout, null);
        coolieBottomSheetDialog.setContentView(view1);
        coolieBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        coolieBottomSheetDialog.show();
        approxAmount = coolieBottomSheetDialog.findViewById(R.id.show_approx_amount);
        approxAmountLayout = coolieBottomSheetDialog.findViewById(R.id.approx_amount_layout);
        approxAmountLayout.setVisibility(View.GONE);
        Button closeDialog = coolieBottomSheetDialog.findViewById(R.id.close_dialog);
        closeDialog.setOnClickListener(v -> coolieBottomSheetDialog.dismiss());


        getAllStation(); //fetching all stations
        setTentativeStartTimePicker(); //setting timepicker for start time
        setTentativeEndTimePicker(); //setting timepicker for end time
        submitBookCoolieForm(); // form submit
    }

    public void getAllStation() {
        callGetSationList = RestClient.getRetrofitClient().create(RestInterface.class).getSationList(authToken);
        callGetSationList.enqueue(new Callback<ArrayList<StationModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<StationModel>> call, @NonNull Response<ArrayList<StationModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stationList = response.body();
                    setStationList();
                } else {
                    Toast.makeText(context, "No Station found!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<StationModel>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error while fetching station!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setStationList() {
        if (autoCompleteStationList == null) {
            autoCompleteStationList = coolieBottomSheetDialog.findViewById(R.id.select_station_input);
        }
        ArrayAdapter<StationModel> adapterStationList = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, stationList);
        autoCompleteStationList.setAdapter(adapterStationList);

        autoCompleteStationList.setOnItemClickListener((parent, view, position, id) -> {
            selectedStation = (StationModel) parent.getItemAtPosition(position);
            coolieRequestModel.setStationId(selectedStation.getStationId()); //setting station id in request model
            getStationAreaByStationId(selectedStation.getStationId());

            //reset fields
            if (autoCompleteStationAreaPickup != null) {
                autoCompleteStationAreaPickup.setText("");
                autoCompleteStationAreaPickup.clearListSelection();
            }
            if (autoCompleteStationAreaDropAt != null) {
                autoCompleteStationAreaDropAt.setText("");
                autoCompleteStationAreaDropAt.clearListSelection();
            }
            if(approxWeight != null){
                approxWeight.setText("");
            }
            // Reset TextInputEditTexts
            if (startTimeInput != null) {
                startTimeInput.setText("");
            }
            if (endTimePicker != null) {
                endTimePicker.setText("");
            }
            approxAmountLayout.setVisibility(View.GONE);
        });
    }

    public void getStationAreaByStationId(int stationId) {
        callGetSationArea = RestClient.getRetrofitClient().create(RestInterface.class).getStationAreaByStationId(authToken, stationId);
        callGetSationArea.enqueue(new Callback<ArrayList<StationAreaModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<StationAreaModel>> call, @NonNull Response<ArrayList<StationAreaModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stationAreaModelList = response.body();
                    setStationAreaPickUp(stationAreaModelList);
                } else {
                    Toast.makeText(context, "No Station Area found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<StationAreaModel>> call, @NonNull Throwable t) {
                Toast.makeText(context, "Error while fetching station area!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setStationAreaPickUp(ArrayList<StationAreaModel> stationAreaModelList) {
        if (autoCompleteStationAreaPickup == null)
            autoCompleteStationAreaPickup = coolieBottomSheetDialog.findViewById(R.id.select_pickup_input);
        ArrayAdapter<StationAreaModel> adapterPickUpArea = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, stationAreaModelList);
        autoCompleteStationAreaPickup.setAdapter(adapterPickUpArea);

        autoCompleteStationAreaPickup.setOnItemClickListener((parent, view, position, id) -> {
            selectedStationAreaPickUp = (StationAreaModel) parent.getItemAtPosition(position);
            coolieRequestModel.setStationAreaPickupFrom(selectedStationAreaPickUp.getStationAreaMasterMappingId());
            setStationAreaDropAt(stationAreaModelList);
            //reset fields
            if (autoCompleteStationAreaDropAt != null) {
                autoCompleteStationAreaDropAt.setText("");
                autoCompleteStationAreaDropAt.clearListSelection();
            }
            if(approxWeight != null){
                approxWeight.setText("");
            }
            // Reset TextInputEditTexts
            if (startTimeInput != null) {
                startTimeInput.setText("");
            }
            if (endTimePicker != null) {
                endTimePicker.setText("");
            }
            approxAmountLayout.setVisibility(View.GONE);
        });
    }

    public void setStationAreaDropAt(ArrayList<StationAreaModel> stationAreaModelList) {
        //reset field
        if (autoCompleteStationAreaDropAt != null) {
            autoCompleteStationAreaDropAt.setText("");
            autoCompleteStationAreaDropAt.clearListSelection();
        }

        //upadate the field
        autoCompleteStationAreaDropAt = coolieBottomSheetDialog.findViewById(R.id.select_dropping_input);
        //removing the selected item from the list if it is already selected in pickup area
        ArrayList<StationAreaModel> filteredDropAtAreaList = new ArrayList<>(stationAreaModelList);
        if (selectedStationAreaPickUp != null) {
            filteredDropAtAreaList.remove(selectedStationAreaPickUp);
        }

        //setting the adapter
        ArrayAdapter<StationAreaModel> adapterDropAtArea = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, filteredDropAtAreaList);
        autoCompleteStationAreaDropAt.setAdapter(adapterDropAtArea);

        //setting the onclick listener
        autoCompleteStationAreaDropAt.setOnItemClickListener((parent, view, position, id) -> {
            selectedStationAreaDropAt = (StationAreaModel) parent.getItemAtPosition(position);
            coolieRequestModel.setStationAreaDropAt(selectedStationAreaDropAt.getStationAreaMasterMappingId());
            //reset fields
            if(approxWeight != null){
                approxWeight.setText("");
            }
            // Reset TextInputEditTexts
            if (startTimeInput != null) {
                startTimeInput.setText("");
            }
            if (endTimePicker != null) {
                endTimePicker.setText("");
            }
            approxAmountLayout.setVisibility(View.GONE);
            setApproxWeight();
        });
    }

    public void setApproxWeight(){
        approxWeight = coolieBottomSheetDialog.findViewById(R.id.approx_weight_input);
        approxWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action needed during text changes
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Reset TextInputEditTexts
                if (startTimeInput != null) {
                    startTimeInput.setText("");
                }
                if (endTimePicker != null) {
                    endTimePicker.setText("");
                }
                approxAmountLayout.setVisibility(View.GONE);
            }
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
                //reset fields
                if (endTimePicker != null) {
                    endTimePicker.setText("");
                }
                approxAmountLayout.setVisibility(View.GONE);
            });
        });
    }

    //setting timepicker for end time
    public void setTentativeEndTimePicker() {
        endTimePicker = coolieBottomSheetDialog.findViewById(R.id.approx_end_time_input);
        Calendar calendar = Calendar.getInstance();

        endTimePicker.setOnClickListener(v -> {
            MaterialTimePicker endTime = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                    .setMinute(calendar.get(Calendar.MINUTE))
                    .setTitleText("Select End Time").build();

            endTime.show(fragmentManager, "MATERIAL_TIME_PICKER");

            endTime.addOnPositiveButtonClickListener(v1 -> {
                // Set the hour and minute from the picker
                calendar.set(Calendar.HOUR_OF_DAY, endTime.getHour());
                calendar.set(Calendar.MINUTE, endTime.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                String tentativeEndTime = TimeConversionUtil.convertTime(calendar);
                coolieRequestModel.setBookingTentativeEndTime(tentativeEndTime);

                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                String formattedDate = dateFormat.format(calendar.getTime());
                endTimePicker.setText(formattedDate);
                approxAmountLayout.setVisibility(View.GONE);
                calculateFares();
            });
        });
    }

    public void calculateFares() {
        getTrainNo = coolieBottomSheetDialog.findViewById(R.id.train_no_input);
        getTrainName = coolieBottomSheetDialog.findViewById(R.id.train_name_input);
        //TextInputEditText getNoOfChairReq = coolieBottomSheetDialog.findViewById(R.id.chair_required_input);
        approxWeight = coolieBottomSheetDialog.findViewById(R.id.approx_weight_input);
        approxAmountLayout.setVisibility(View.VISIBLE);
        coolieRequestModel.setApproxTotalWeightage(Integer.parseInt(approxWeight.getText().toString()));

        FaresCalculation faresCalculation = new FaresCalculation();
        faresCalculation.doAll();
        //apprxWght = Integer.valueOf(approxWeight.getText().toString().trim());
        Integer a = faresCalculation.calculateFare(coolieRequestModel.getStationId(), coolieRequestModel.getBookingTentativeStartTime(), coolieRequestModel.getBookingTentativeEndTime(), coolieRequestModel.getApproxTotalWeightage(), false, false, true);
        if(a != null)
            approxAmount.setText(String.format("₹%d", a));
        Toast.makeText(context, "APPROX COST " + a, Toast.LENGTH_SHORT).show();
    }

    public void submitBookCoolieForm() {
        MaterialButton submitButton = coolieBottomSheetDialog.findViewById(R.id.submit_book_coolie_form);

        if (submitButton != null) {
            submitButton.setOnClickListener(v -> {

                TextInputEditText trainNo = coolieBottomSheetDialog.findViewById(R.id.train_no_input);
                TextInputEditText trainName = coolieBottomSheetDialog.findViewById(R.id.train_name_input);
                TextInputEditText approxWeight = coolieBottomSheetDialog.findViewById(R.id.approx_weight_input);

                if (validateInputs(trainNo, trainName, approxWeight)) {
                    coolieRequestModel.setTrainNumber(trainNo.getText().toString());
                    coolieRequestModel.setTrainName(trainName.getText().toString());
                    coolieRequestModel.setApproxTotalWeightage(Integer.parseInt(approxWeight.getText().toString()));

                    Date date = new Date();
                    String formattedDate = TimeConversionUtil.convertTime(date);
                    coolieRequestModel.setBookingDate(formattedDate);
                    coolieRequestModel.setBookingFor("John Doe");
                    coolieRequestModel.setBookingType("Round Trip");
                    coolieRequestModel.setRecordTracking(formattedDate);
                    coolieRequestModel.setServiceType(1);
                    coolieRequestModel.setRequestStatus(1);
                    coolieRequestModel.setUserMaster(userId);

                    Call<CoolieResponseModel> callSubmitBookCoolieForm = RestClient.getRetrofitClient().create(RestInterface.class).submitBookCoolieForm(authToken, coolieRequestModel);
                    callSubmitBookCoolieForm.enqueue(new Callback<CoolieResponseModel>() {
                        @Override
                        public void onResponse(@NonNull Call<CoolieResponseModel> call, @NonNull Response<CoolieResponseModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Toast.makeText(context, "Coolie booked successfully!", Toast.LENGTH_SHORT).show();
                                resetFormFields();
                                coolieBottomSheetDialog.dismiss();
                            } else
                                Toast.makeText(context, "Failed to book coolie! Please try again.", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(@NonNull Call<CoolieResponseModel> call, @NonNull Throwable t) {
                            Toast.makeText(context, "Error while booking coolie service!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private boolean validateInputs(TextInputEditText trainNo, TextInputEditText trainName, TextInputEditText approxWeight) {
        boolean isValid = true;

        if (coolieRequestModel.getStationId() == 0) {
            Toast.makeText(context, "Please select a station.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (coolieRequestModel.getStationAreaPickupFrom() == 0) {
            Toast.makeText(context, "Please select a pickup area.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (coolieRequestModel.getStationAreaDropAt() == 0) {
            Toast.makeText(context, "Please select a drop-off area.", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (startTimeInput.getText() == null || startTimeInput.getText().toString().isEmpty()) {
            startTimeInput.setError("Start time is required.");
            isValid = false;
        } else if (endTimePicker.getText() == null || endTimePicker.getText().toString().isEmpty()) {
            endTimePicker.setError("End time is required.");
            isValid = false;
        }
        else if (trainNo.getText() == null || trainNo.getText().toString().isEmpty()) {
            trainNo.setError("Train number is required.");
            isValid = false;
        }
        else if (trainName.getText() == null || trainName.getText().toString().isEmpty()) {
            trainName.setError("Train name is required.");
            isValid = false;
        }
        else if (approxWeight.getText() == null || approxWeight.getText().toString().isEmpty()) {
            approxWeight.setError("Approximate weight is required.");
            isValid = false;
        }

        return isValid;
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

            TextInputEditText trainNo = coolieBottomSheetDialog.findViewById(R.id.train_no_input);
            TextInputEditText trainName = coolieBottomSheetDialog.findViewById(R.id.train_name_input);
            TextInputEditText approxWeight = coolieBottomSheetDialog.findViewById(R.id.approx_weight_input);
            if (trainNo != null) {
                trainNo.setText("");
            }
            if (trainName != null) {
                trainName.setText("");
            }
            if (approxWeight != null) {
                approxWeight.setText("");
            }

            // Reset request model
            coolieRequestModel = new CoolieRequestModel();
        }
    }

}
