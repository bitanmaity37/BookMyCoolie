package com.cdac.iaf.bookmycoolie.models;

public class ContactUsModel {

    private String stationName;
    private String userMobile;
    private String userEmailId;

    public ContactUsModel(String stationName, String userMobile, String userEmailId) {
        this.stationName = stationName;
        this.userMobile = userMobile;
        this.userEmailId = userEmailId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    @Override
    public String toString() {
        return "ContactUsModel{" +
                "stationName='" + stationName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userEmailId='" + userEmailId + '\'' +
                '}';
    }
}
