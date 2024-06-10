package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.adpater.CarouselAdapter;
import com.cdac.iaf.bookmycoolie.models.StationAreaModel;
import com.cdac.iaf.bookmycoolie.models.StationModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerHome extends AppCompatActivity {

    MaterialCardView bookCoolieCard;

    RecyclerView recyclerView;

    List<StationModel> stationModelList;

    List<StationAreaModel> stationAreaModelList;

    ArrayAdapter<String> stationAdapter;

    ArrayAdapter<String> areaAdapter;

    Call<ArrayList<StationAreaModel>> callGetSationArea;

    Call<ArrayList<StationModel>> callGetSationList;

    String authToken;

    ArrayList<StationAreaModel> stationAreaList;

    ArrayList<StationModel> stationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_home);
        SharedPreferences sharedPreferences = getSharedPreferences("jwt_token", MODE_PRIVATE);
        authToken = sharedPreferences.getString("auth_token", null);

        Toast.makeText(PassengerHome.this, "token " + sharedPreferences.getString("auth_token", null), Toast.LENGTH_LONG).show();

        bookCoolieCard = findViewById(R.id.book_a_coolie);

        callGetSationArea = RestClient.getRetrofitClient().create(RestInterface.class).getSationArea(authToken);

        callGetSationArea.enqueue(new Callback<ArrayList<StationAreaModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StationAreaModel>> call, Response<ArrayList<StationAreaModel>> response) {
                System.out.println("station area list: " + response.body().toString());
                stationAreaList = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<StationAreaModel>> call, Throwable t) {
                System.out.println("station area list: error " + t.getMessage());
            }
        });

        callGetSationList = RestClient.getRetrofitClient().create(RestInterface.class).getSationList(authToken);

        callGetSationList.enqueue(new Callback<ArrayList<StationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StationModel>> call, Response<ArrayList<StationModel>> response) {
                System.out.println("station list: "+response.body());
                stationList = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<StationModel>> call, Throwable t) {
                System.out.println("station list: error " + t.getMessage());
            }
        });

        bookCoolieCard.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PassengerHome.this);
            View view1 = LayoutInflater.from(PassengerHome.this).inflate(R.layout.book_coolie_layout, null);
            bottomSheetDialog.setContentView(view1);
            bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bottomSheetDialog.show();

            AutoCompleteTextView autoCompletePickUpArea = bottomSheetDialog.findViewById(R.id.select_pickup_input);
            ArrayAdapter<StationAreaModel> adapterPickUpArea = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stationAreaList);
            autoCompletePickUpArea.setAdapter(adapterPickUpArea);

            AutoCompleteTextView autoCompleteStationList = bottomSheetDialog.findViewById(R.id.select_station_input);
            ArrayAdapter<StationModel> adapterStationList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stationList);
            autoCompleteStationList.setAdapter(adapterStationList);

        });

        recyclerView = findViewById(R.id.carousel_recycler_view);
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("https://images.unsplash.com/photo-1692528131755-d4e366b2adf0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzNXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692862582645-3b6fd47b7513?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692584927805-d4096552a5ba?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0Nnx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692854236272-cc49076a2629?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1MXx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1681207751526-a091f2c6a538?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODF8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60");
        arrayList.add("https://images.unsplash.com/photo-1692610365998-c628604f5d9f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwyODZ8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60");

        CarouselAdapter adapter = new CarouselAdapter(PassengerHome.this, arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String path) {
                startActivity(new Intent(PassengerHome.this, CarouselAdapter.class).putExtra("image", path), ActivityOptions.makeSceneTransitionAnimation(PassengerHome.this, imageView, "image").toBundle());
            }
        });

    }
}