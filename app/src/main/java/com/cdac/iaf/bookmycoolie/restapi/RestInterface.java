package com.cdac.iaf.bookmycoolie.restapi;

import com.cdac.iaf.bookmycoolie.models.AddCoolieRequest;
import com.cdac.iaf.bookmycoolie.models.AddCoolieResponse;
import com.cdac.iaf.bookmycoolie.models.AddOperatorRequest;
import com.cdac.iaf.bookmycoolie.models.AddOperatorResponse;
import com.cdac.iaf.bookmycoolie.models.LoginRequest;
import com.cdac.iaf.bookmycoolie.models.LoginResponse;
import com.cdac.iaf.bookmycoolie.models.StationAreaModel;
import com.cdac.iaf.bookmycoolie.models.StationModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestInterface {
    @POST("auth/login")
    Call<LoginResponse> getAccess(@Body LoginRequest loginRequest);

    @POST("operator/add")
    Call<AddOperatorResponse> addOperator(@Body AddOperatorRequest addOperatorRequest,
                                          @Header("Authorization") String authorization);

    @POST("coolie/add")
    Call<AddCoolieResponse> addCoolie(@Header("Authorization") String authorization,
                                      @Body AddCoolieRequest addCoolieRequest);

    @GET("admin/getStationArea")
    Call<ArrayList<StationAreaModel>> getSationArea(@Header("Authorization") String authorization);

    @GET("admin/getAllStations")
    Call<ArrayList<StationModel>> getSationList(@Header("Authorization") String authorization);
}
