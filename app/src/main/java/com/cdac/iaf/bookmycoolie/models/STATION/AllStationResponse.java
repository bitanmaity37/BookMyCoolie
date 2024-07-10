package com.cdac.iaf.bookmycoolie.models.STATION;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AllStationResponse extends AddStationRequest implements Serializable {

    @SerializedName("stationId")
    Integer stationId;

    @SerializedName("areaMasterMappingModels")
    ArrayList<AreaMasterMappingModels> areaMasterMappingModels;

    public AllStationResponse(Integer noOfCarts, Integer noOfWheelchairs, String stationCode, String stationName, boolean stationStatus, Integer stationStateId, Integer stationDistrictId, Integer stationSubDistrictId, Integer operatorId, ArrayList<StationAreaMasterMapping> stationAreaMasterMappings, Integer stationId, ArrayList<AreaMasterMappingModels> areaMasterMappingModels) {
        super(noOfCarts, noOfWheelchairs, stationCode, stationName, stationStatus, stationStateId, stationDistrictId, stationSubDistrictId, operatorId, stationAreaMasterMappings);
        this.stationId = stationId;
        this.areaMasterMappingModels = areaMasterMappingModels;
    }

    public AllStationResponse(Integer stationId, ArrayList<AreaMasterMappingModels> areaMasterMappingModels) {
        this.stationId = stationId;
        this.areaMasterMappingModels = areaMasterMappingModels;
    }

    public AllStationResponse(Integer stationId, Integer noOfCarts, Integer noOfWheelchairs) {
        super(noOfCarts, noOfWheelchairs);
        this.stationId = stationId;
    }

    public ArrayList<AreaMasterMappingModels> getAreaMasterMappingModels() {
        return areaMasterMappingModels;
    }

    public void setAreaMasterMappingModels(ArrayList<AreaMasterMappingModels> areaMasterMappingModels) {
        this.areaMasterMappingModels = areaMasterMappingModels;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return "AllStationResponse{" +
                "stationId=" + stationId +
                ", areaMasterMappingModels=" + areaMasterMappingModels +
                ", noOfCarts=" + noOfCarts +
                ", noOfWheelchairs=" + noOfWheelchairs +
                ", stationCode='" + stationCode + '\'' +
                ", stationName='" + stationName + '\'' +
                ", stationStatus=" + stationStatus +
                ", stationStateId=" + stationStateId +
                ", stationDistrictId=" + stationDistrictId +
                ", stationSubDistrictId=" + stationSubDistrictId +
                ", operatorId=" + operatorId +
                ", stationAreaMasterMappings=" + stationAreaMasterMappings +
                '}';
    }
}
