package com.cdac.iaf.bookmycoolie.models;

public class StationAreaModel {

    private int areaMasterId;
    private String areaName;

    public StationAreaModel() {
    }

    @Override
    public String toString() {
        return areaName;
    }

    public int getAreaMasterId() {
        return areaMasterId;
    }

    public void setAreaMasterId(int areaMasterId) {
        this.areaMasterId = areaMasterId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
