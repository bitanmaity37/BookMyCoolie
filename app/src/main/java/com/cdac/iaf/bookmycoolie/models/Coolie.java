package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coolie implements Serializable {


    @SerializedName("userId")
    Integer userId;
    @SerializedName("coolieId")
    Integer coolieId;
    @SerializedName("cooliePhoto")
    String cooliePhoto;
    @SerializedName("userMobile")
    String userMobile;
    @SerializedName("userName")
    String userName;
    @SerializedName("coolieBatchId")
    String coolieBatchId;

    @SerializedName("userStatus")
    Integer userStatus;



    public Coolie() {
    }


    public Coolie(Integer userId, Integer coolieId, String cooliePhoto, String userMobile, String userName, String coolieBatchId, Integer userStatus) {
        this.userId = userId;
        this.coolieId = coolieId;
        this.cooliePhoto = cooliePhoto;
        this.userMobile = userMobile;
        this.userName = userName;
        this.coolieBatchId = coolieBatchId;
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "Coolie{" +
                "userId=" + userId +
                ", coolieId=" + coolieId +
                ", cooliePhoto='" + cooliePhoto + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userName='" + userName + '\'' +
                ", coolieBatchId='" + coolieBatchId + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCoolieId() {
        return coolieId;
    }

    public void setCoolieId(Integer coolieId) {
        this.coolieId = coolieId;
    }

    public String getCooliePhoto() {
        return cooliePhoto;
    }

    public void setCooliePhoto(String cooliePhoto) {
        this.cooliePhoto = cooliePhoto;
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

    public String getCoolieBatchId() {
        return coolieBatchId;
    }

    public void setCoolieBatchId(String coolieBatchId) {
        this.coolieBatchId = coolieBatchId;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }
}
