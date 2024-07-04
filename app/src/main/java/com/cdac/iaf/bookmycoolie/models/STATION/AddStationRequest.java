package com.cdac.iaf.bookmycoolie.models.STATION;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AddStationRequest implements Serializable {
    @SerializedName("noOfCarts")
    Integer noOfCarts;
    @SerializedName("noOfWheelchairs")
    Integer noOfWheelchairs;
    @SerializedName("stationCode")
    String stationCode;
    @SerializedName("stationName")
    String stationName;
    @SerializedName("stationStatus")
    boolean stationStatus;
    @SerializedName("stationStateId")
    Integer stationStateId;
    @SerializedName("stationDistrictId")
    Integer stationDistrictId;
    @SerializedName("stationSubDistrictId")
    Integer stationSubDistrictId;
    @SerializedName("operatorId")
    Integer operatorId;
    @SerializedName("stationAreaMasterMappings")
    ArrayList<StationAreaMasterMapping> stationAreaMasterMappings;

    @Override
    public String toString() {
        return "AddStationRequest{" +
                "noOfCarts=" + noOfCarts +
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

    public AddStationRequest(Integer noOfCarts, Integer noOfWheelchairs, String stationCode, String stationName, boolean stationStatus, Integer stationStateId, Integer stationDistrictId, Integer stationSubDistrictId, Integer operatorId, ArrayList<StationAreaMasterMapping> stationAreaMasterMappings) {
        this.noOfCarts = noOfCarts;
        this.noOfWheelchairs = noOfWheelchairs;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.stationStatus = stationStatus;
        this.stationStateId = stationStateId;
        this.stationDistrictId = stationDistrictId;
        this.stationSubDistrictId = stationSubDistrictId;
        this.operatorId = operatorId;
        this.stationAreaMasterMappings = stationAreaMasterMappings;
    }

    public Integer getNoOfCarts() {
        return noOfCarts;
    }

    public void setNoOfCarts(Integer noOfCarts) {
        this.noOfCarts = noOfCarts;
    }

    public Integer getNoOfWheelchairs() {
        return noOfWheelchairs;
    }

    public void setNoOfWheelchairs(Integer noOfWheelchairs) {
        this.noOfWheelchairs = noOfWheelchairs;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public boolean isStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(boolean stationStatus) {
        this.stationStatus = stationStatus;
    }

    public Integer getStationStateId() {
        return stationStateId;
    }

    public void setStationStateId(Integer stationStateId) {
        this.stationStateId = stationStateId;
    }

    public Integer getStationDistrictId() {
        return stationDistrictId;
    }

    public void setStationDistrictId(Integer stationDistrictId) {
        this.stationDistrictId = stationDistrictId;
    }

    public Integer getStationSubDistrictId() {
        return stationSubDistrictId;
    }

    public void setStationSubDistrictId(Integer stationSubDistrictId) {
        this.stationSubDistrictId = stationSubDistrictId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public ArrayList<StationAreaMasterMapping> getStationAreaMasterMappings() {
        return stationAreaMasterMappings;
    }

    public void setStationAreaMasterMappings(ArrayList<StationAreaMasterMapping> stationAreaMasterMappings) {
        this.stationAreaMasterMappings = stationAreaMasterMappings;
    }
}
