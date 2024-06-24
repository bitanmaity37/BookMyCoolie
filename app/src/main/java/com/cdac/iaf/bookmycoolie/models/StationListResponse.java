package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StationListResponse implements Serializable {
    @SerializedName("stationId")
    Integer stationId;
    @SerializedName("stationCode")
    String stationCode;
    @SerializedName("stationName")
    String stationName;
    @SerializedName("noOfCarts")
    Integer noOfCarts;
    @SerializedName("noOfWheelchairs")
    Integer noOfWheelchairs;

    public StationListResponse(Integer stationId, String stationCode, String stationName, Integer noOfCarts, Integer noOfWheelchairs) {
        this.stationId = stationId;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.noOfCarts = noOfCarts;
        this.noOfWheelchairs = noOfWheelchairs;
    }

    @Override
    public String toString() {
        return "Id: " + stationId + " Code: " + stationCode + " \nName: " + stationName;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
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
}
