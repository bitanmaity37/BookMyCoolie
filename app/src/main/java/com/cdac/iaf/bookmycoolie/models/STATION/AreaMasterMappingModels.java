package com.cdac.iaf.bookmycoolie.models.STATION;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AreaMasterMappingModels implements Serializable {

    @SerializedName("stationAreaMasterMappingId")
    Integer stationAreaMasterMappingId;
    @SerializedName("areaDescription")
    String areaDescription;

    public AreaMasterMappingModels(Integer stationAreaMasterMappingId, String areaDescription) {
        this.stationAreaMasterMappingId = stationAreaMasterMappingId;
        this.areaDescription = areaDescription;
    }

    @Override
    public String toString() {
        return "AreaMasterMappingModels{" +
                "stationAreaMasterMappingId=" + stationAreaMasterMappingId +
                ", areaDescription='" + areaDescription + '\'' +
                '}';
    }

    public Integer getStationAreaMasterMappingId() {
        return stationAreaMasterMappingId;
    }

    public void setStationAreaMasterMappingId(Integer stationAreaMasterMappingId) {
        this.stationAreaMasterMappingId = stationAreaMasterMappingId;
    }

    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }
}
