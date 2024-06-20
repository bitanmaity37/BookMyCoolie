package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FreeCoolieRequest implements Serializable {
    @SerializedName("stationId")
    Integer stationId;

    public FreeCoolieRequest(Integer stationId) {
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
        return "FreeCoolieRequest{" +
                "stationId=" + stationId +
                '}';
    }
}
