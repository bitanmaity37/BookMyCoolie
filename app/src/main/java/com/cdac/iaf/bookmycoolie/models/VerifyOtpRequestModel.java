package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifyOtpRequestModel implements Serializable {

    @SerializedName("userMobile")
    String userMobile;
    @SerializedName("otp")
    String otp;

    public VerifyOtpRequestModel(String userMobile, String otp) {
        this.userMobile = userMobile;
        this.otp = otp;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "VerifyOtpRequestModel{" +
                "userMobile='" + userMobile + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
