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

    public AreaMasterMappingModels(Boolean areaStatus, String areaDescription) {
        this.areaStatus = areaStatus;
        this.areaDescription = areaDescription;
    }

    /*public AreaMasterMappingModels(Integer stationAreaMasterMappingId) {
        this.stationAreaMasterMappingId = stationAreaMasterMappingId;
    }*/

    @Override
    public String toString() {
        return "AreaMasterMappingModels{" +
                "stationAreaMasterMappingId=" + stationAreaMasterMappingId +
                ", areaStatus=" + areaStatus +
                ", areaDescription='" + areaDescription + '\'' +
                '}';
    }

    public Integer getStationAreaMasterMappingId() {
        return stationAreaMasterMappingId;
    }

    public void setStationAreaMasterMappingId(Integer stationAreaMasterMappingId) {
        this.stationAreaMasterMappingId = stationAreaMasterMappingId;
    }

    public Boolean getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(Boolean areaStatus) {
        this.areaStatus = areaStatus;
    }

    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }
}
