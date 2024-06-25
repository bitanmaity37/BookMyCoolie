package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetOtpRequestModel implements Serializable {
    @SerializedName("userMobile")
    String userMobile;

    public GetOtpRequestModel(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    @Override
    public String toString() {
        return "GetOtpRequestModel{" +
                "userMobile='" + userMobile + '\'' +
                '}';
    }
}
