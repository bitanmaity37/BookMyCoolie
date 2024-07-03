package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateCoolieRequest implements Serializable {

    @SerializedName("userId")
    Integer userId;
    @SerializedName("userStatus")
    Integer userStatus;
    @SerializedName("userName")
    String userName;
    @SerializedName("coolieBatchId")
    String coolieBatchId;
    @SerializedName("cooliePhoto")
    String cooliePhoto;

    public UpdateCoolieRequest(Integer userId, Integer userStatus, String userName, String coolieBatchId, String cooliePhoto) {
        this.userId = userId;
        this.userStatus = userStatus;
        this.userName = userName;
        this.coolieBatchId = coolieBatchId;
        this.cooliePhoto = cooliePhoto;
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

    public String getCoolieBatchId() {
        return coolieBatchId;
    }

    public void setCoolieBatchId(String coolieBatchId) {
        this.coolieBatchId = coolieBatchId;
    }

    public String getCooliePhoto() {
        return cooliePhoto;
    }

    public void setCooliePhoto(String cooliePhoto) {
        this.cooliePhoto = cooliePhoto;
    }

    @Override
    public String toString() {
        return "UpdateCoolieRequest{" +
                "userId=" + userId +
                ", userStatus=" + userStatus +
                ", userName='" + userName + '\'' +
                ", coolieBatchId='" + coolieBatchId + '\'' +
                ", cooliePhoto='" + cooliePhoto + '\'' +
                '}';
    }
}
