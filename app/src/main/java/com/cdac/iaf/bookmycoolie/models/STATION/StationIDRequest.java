package com.cdac.iaf.bookmycoolie.models.STATION;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StationIDRequest implements Serializable {
    @SerializedName("stationId")
    Integer stationId;

    public StationIDRequest(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return "StationIDRequest{" +
                "stationId=" + stationId +
                '}';
    }
}
