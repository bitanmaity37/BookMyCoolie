package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.adpater.OnGoingRequestAdapter;
import com.cdac.iaf.bookmycoolie.models.OrderStatusModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnGoingRequest {

    Context context;
    String authToken;
    ArrayList<OrderStatusModel> orderStatusModels;
    int userId;
    AppCompatActivity appCompatActivity;

    public OnGoingRequest(Context context, String authToken, int userId, AppCompatActivity appCompatActivity) {
        this.context = context;
        this.authToken = authToken;
        this.userId = userId;
        this.appCompatActivity = appCompatActivity;
    }



}
