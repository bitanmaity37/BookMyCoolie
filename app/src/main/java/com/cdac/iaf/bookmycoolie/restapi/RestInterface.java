package com.cdac.iaf.bookmycoolie.restapi;

import com.cdac.iaf.bookmycoolie.models.AddOperatorRequest;
import com.cdac.iaf.bookmycoolie.models.AddOperatorResponse;
import com.cdac.iaf.bookmycoolie.models.LoginRequest;
import com.cdac.iaf.bookmycoolie.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestInterface {
    @POST("auth/login")
    Call<LoginResponse> getAccess(@Body LoginRequest loginRequest);

    @POST("operator/add")
    Call<AddOperatorResponse> addOperator(@Body AddOperatorRequest addOperatorRequest,
                                          @Header("Authorization") String authorization);
}
