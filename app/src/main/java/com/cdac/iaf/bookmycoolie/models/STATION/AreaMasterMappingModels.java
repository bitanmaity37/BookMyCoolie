package com.cdac.iaf.bookmycoolie.models.STATION;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AreaMasterMappingModels implements Serializable {

    @SerializedName("stationAreaMasterMappingId")
    Integer stationAreaMasterMappingId;

    @SerializedName("areaStatus")
    Boolean areaStatus;
    @SerializedName("areaDescription")
    String areaDescription;

    public AreaMasterMappingModels(Integer stationAreaMasterMappingId, Boolean areaStatus, String areaDescription) {
        this.stationAreaMasterMappingId = stationAreaMasterMappingId;
        this.areaStatus = areaStatus;
        this.areaDescription = areaDescription;
    }

    @Override
    public String toString() {
        return "AreaMasterMappingModels{" +
                "stationAreaMasterMappingId=" + stationAreaMasterMappingId +
                ", areaStatus=" + areaStatus +
                ", areaDescription='" + areaDescription + '\'' +
                '}';
    }
}
