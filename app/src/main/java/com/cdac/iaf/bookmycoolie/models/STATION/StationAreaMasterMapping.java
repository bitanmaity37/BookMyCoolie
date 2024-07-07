package com.cdac.iaf.bookmycoolie.models.STATION;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StationAreaMasterMapping implements Serializable {

    @SerializedName("areaDescription")
    String areaDescription;

    public StationAreaMasterMapping(String areaDescription) {
        this.areaDescription = areaDescription;
    }

    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }

    @Override
    public String toString() {
        return "StationAreaMasterMapping{" +
                "areaDescription='" + areaDescription + '\'' +
                '}';
    }
}
