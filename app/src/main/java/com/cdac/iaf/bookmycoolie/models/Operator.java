package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Operator implements Serializable {
    @SerializedName("stationName")
    String stationName;
    @SerializedName("userId")
    Integer userId;
    @SerializedName("userEmailId")
    String userEmailId;
    @SerializedName("userEmpId")
    String userEmpId;
    @SerializedName("userMobile")
    String userMobile;
    @SerializedName("userName")
    String userName;

    public Operator(String stationName, Integer userId, String userEmailId, String userEmpId, String userMobile, String userName) {
        this.stationName = stationName;
        this.userId = userId;
        this.userEmailId = userEmailId;
        this.userEmpId = userEmpId;
        this.userMobile = userMobile;
        this.userName = userName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserEmpId() {
        return userEmpId;
    }

    public void setUserEmpId(String userEmpId) {
        this.userEmpId = userEmpId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "stationName='" + stationName + '\'' +
                ", userId=" + userId +
                ", userEmailId='" + userEmailId + '\'' +
                ", userEmpId='" + userEmpId + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
