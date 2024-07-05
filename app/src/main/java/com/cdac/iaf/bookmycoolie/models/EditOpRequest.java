package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EditOpRequest implements Serializable {


        @SerializedName("userId")
        Integer userId;
        @SerializedName("userStatus")
        Integer userStatus;
        @SerializedName("userName")
        String userName;
        @SerializedName("userMobile")
        String userMobile;

    public EditOpRequest(Integer userId, Integer userStatus, String userName, String userMobile) {
        this.userId = userId;
        this.userStatus = userStatus;
        this.userName = userName;
        this.userMobile = userMobile;
    }


    @Override
    public String toString() {
        return "EditOpRequest{" +
                "userId=" + userId +
                ", userStatus=" + userStatus +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
