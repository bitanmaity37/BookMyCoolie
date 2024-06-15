package com.cdac.iaf.bookmycoolie.models;

public class StationAreaModel {

    private int stationAreaMasterMappingId;
    private String areaDescription;

    public StationAreaModel() {
    }

    @Override
    public String toString() {
        return areaDescription;
    }

    public int getStationAreaMasterMappingId() {
        return stationAreaMasterMappingId;
    }

    public void setStationAreaMasterMappingId(int stationAreaMasterMappingId) {
        this.stationAreaMasterMappingId = stationAreaMasterMappingId;
    }

    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }
}
