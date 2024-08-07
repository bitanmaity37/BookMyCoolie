package com.cdac.iaf.bookmycoolie.restapi;

import com.cdac.iaf.bookmycoolie.models.AddCoolieRequest;
import com.cdac.iaf.bookmycoolie.models.ChangeUserStatus;
import com.cdac.iaf.bookmycoolie.models.EditCoolieRequest;
import com.cdac.iaf.bookmycoolie.models.EditOpRequest;
import com.cdac.iaf.bookmycoolie.models.STATION.AddStationRequest;
import com.cdac.iaf.bookmycoolie.models.STATION.AllStationResponse;
import com.cdac.iaf.bookmycoolie.models.STATION.AreaMasterMappingModels;
import com.cdac.iaf.bookmycoolie.models.STATION.StationIDRequest;
import com.cdac.iaf.bookmycoolie.models.SimpleUserIDResponse;
import com.cdac.iaf.bookmycoolie.models.AddOperatorRequest;
import com.cdac.iaf.bookmycoolie.models.AddOperatorResponse;
import com.cdac.iaf.bookmycoolie.models.AssignCoolieToPassngrRequest;
import com.cdac.iaf.bookmycoolie.models.AttendanceCoolieResponse;
import com.cdac.iaf.bookmycoolie.models.CancelReqReqest;
import com.cdac.iaf.bookmycoolie.models.Coolie;
import com.cdac.iaf.bookmycoolie.models.CoolieRequestModel;
import com.cdac.iaf.bookmycoolie.models.CoolieResponseModel;
import com.cdac.iaf.bookmycoolie.models.FaqModel;
import com.cdac.iaf.bookmycoolie.models.FreeCoolieRequest;
import com.cdac.iaf.bookmycoolie.models.FreeCoolieResponse;
import com.cdac.iaf.bookmycoolie.models.GetCoolieRequest;
import com.cdac.iaf.bookmycoolie.models.GetOtpRequestModel;
import com.cdac.iaf.bookmycoolie.models.LoginRequest;
import com.cdac.iaf.bookmycoolie.models.LoginResponse;
import com.cdac.iaf.bookmycoolie.models.Operator;
import com.cdac.iaf.bookmycoolie.models.OrderDetailsModel;
import com.cdac.iaf.bookmycoolie.models.OrderStatusModel;
import com.cdac.iaf.bookmycoolie.models.PassengerReqResponses;
import com.cdac.iaf.bookmycoolie.models.PassengerRequestsModel;
import com.cdac.iaf.bookmycoolie.models.RegisterPassengerDetailsModel;
import com.cdac.iaf.bookmycoolie.models.SaveAttendanceModel;
import com.cdac.iaf.bookmycoolie.models.SimpleResponse;
import com.cdac.iaf.bookmycoolie.models.StationAreaModel;
import com.cdac.iaf.bookmycoolie.models.StationEditAddAreaResponse;
import com.cdac.iaf.bookmycoolie.models.StationListResponse;
import com.cdac.iaf.bookmycoolie.models.StationModel;
import com.cdac.iaf.bookmycoolie.models.VerifyOtpRequestModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
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
    Call<SimpleUserIDResponse> addCoolie(@Header("Authorization") String authorization,
                                         @Body AddCoolieRequest addCoolieRequest);

    @GET("admin/getStationArea")
    Call<ArrayList<StationAreaModel>> getSationArea(@Header("Authorization") String authorization);

    @GET("admin/getAllStations")
    Call<ArrayList<StationModel>> getSationList(@Header("Authorization") String authorization);
    
    @POST("admin/getStationAreaByStationId")
    Call<ArrayList<StationAreaModel>> getStationAreaByStationId(@Header("Authorization") String authorization,@Body int stationId);

    @POST("passenger/requestSerive")
    Call<CoolieResponseModel> submitBookCoolieForm(@Header("Authorization") String authorization,
                                                   @Body CoolieRequestModel coolieRequestModel);

    @POST("passenger/getRequestsByPassengerId")
    Call<ArrayList<OrderStatusModel>> getOrderHistoryByPassengerId(@Header("Authorization") String authorization,
                                                        @Body int passengerId);

    @POST("passenger/getAssignedCoolieByRequestId")
    Call<ArrayList<OrderDetailsModel>> getOrderDetailsByRequestId(@Header("Authorization") String authorization,
                                                                  @Body int passengerRequestId);

    @POST("passenger/cancelRequest")
    Call<Boolean> cancelPassengerRequest(@Header("Authorization") String authorization,
                                         @Body int passengerRequestId);

    @GET("general/getFaq")
    Call<ArrayList<FaqModel>> getFaqList(@Header("Authorization") String authorization);

    @GET("operator/getAllOperator")
    Call<ArrayList<Operator>> getOperators(@Header("Authorization") String authorization);

    @POST("coolie/getAllCoolie")
    Call<ArrayList<Coolie>> getCoolies(@Header("Authorization") String authorization,
                                       @Body GetCoolieRequest getCoolieRequest);

    @POST("operator/getRequestByServiceTypeAndStationIdAndRequestStatus")
    Call<ArrayList<PassengerReqResponses>> getPReqs(@Header("Authorization") String authorization,
                                                    @Body PassengerRequestsModel passengerRequestsModel);

    @POST("coolie/getActiveCoolie")
    Call<ArrayList<FreeCoolieResponse>> getFCoolies(@Header("Authorization") String authorization,
                                                    @Body FreeCoolieRequest freeCoolieRequest);

    @POST("operator/assignCoolieToPassanger")
    Call<ResponseBody> mapCoolie(@Header("Authorization") String authorization, @Body AssignCoolieToPassngrRequest assignCoolieToPassngrRequest);

    @POST("passenger/cancelRequest")
    Call<ResponseBody> cancelPReq(@Header("Authorization") String authorization, @Body CancelReqReqest cancelReqReqest);

    @POST("operator/completePassangerRequest")
    Call<SimpleResponse> completeReq(@Header("Authorization") String authorization, @Body CancelReqReqest cancelReqReqest);

    @POST("coolie/getAllCoolieForAttendance")
    Call<ArrayList<AttendanceCoolieResponse>> getCforAtndnc(@Header("Authorization") String authorization,
                                                            @Body GetCoolieRequest freeCoolieRequest);

    @POST("operator/takeAttandance")
    Call<ResponseBody> saveAttendance(@Header("Authorization") String authorization,
                                      @Body SaveAttendanceModel getCoolieRequest);

    @GET("admin/getAllStations")
    Call<ArrayList<StationListResponse>> getStation(@Header("Authorization") String authorization);

    @POST("auth/getOtp")
    Call<SimpleResponse> getOTP(@Body GetOtpRequestModel getOtpRequestModel);

    @POST("auth/verifyOtp")
    Call<SimpleResponse> verifyOtp(@Body VerifyOtpRequestModel verifyOtpRequestModel);

    @POST("passenger/add")
    Call<SimpleUserIDResponse> registerPsngr(@Body RegisterPassengerDetailsModel registerPassengerDetailsModel);

    @POST("admin/addStation")
    Call<SimpleResponse> addStation(@Header("Authorization") String authorization, @Body AddStationRequest addStationRequest);

    @POST("coolie/update")
    Call<SimpleResponse> modCoolie(@Header("Authorization") String authorization, @Body EditCoolieRequest coolie);


    @POST("general/changeStatusById")
    Call<SimpleResponse> changeStatus(@Header("Authorization") String authorization, @Body ChangeUserStatus changeUserStatus);

    @POST("operator/update")
    Call<SimpleResponse> modOps(@Header("Authorization") String authorization, @Body EditOpRequest operator);

    @GET("admin/getAllStations")
    Call<ArrayList<AllStationResponse>> getStns(@Header("Authorization") String authorization);

    @POST("admin/getStationAreaByStationId")
    Call<ArrayList<AreaMasterMappingModels>> getStationAreasByID(@Header("Authorization") String authorization,
                                                                 @Body StationIDRequest stationIDRequest);
    @POST("admin/disableStationAreaMasterById")
    Call<SimpleResponse> updateStationStatus(@Header("Authorization") String authorization,
                                                @Body AreaMasterMappingModels areaMasterMappingModels);

    @POST("admin/addAreaMasterByStationId")
    Call<StationEditAddAreaResponse> addMoreAreaDuringEdit(@Header("Authorization") String authorization,
                                                           @Body AllStationResponse allStationResponse);

    @POST("operator/updateInventory")
    Call<SimpleResponse> addInventory(@Header("Authorization") String authorization,
                                    @Body AllStationResponse allStationResponse);


}
