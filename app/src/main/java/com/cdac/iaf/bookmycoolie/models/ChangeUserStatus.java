package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChangeUserStatus implements Serializable {
    @SerializedName("userId")
    Integer userId;
    @SerializedName("userStatus")
    Integer userStatus;

    public ChangeUserStatus(Integer userId, Integer userStatus) {
        this.userId = userId;
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "ChangeUserStatus{" +
                "userId=" + userId +
                ", userStatus=" + userStatus +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}
