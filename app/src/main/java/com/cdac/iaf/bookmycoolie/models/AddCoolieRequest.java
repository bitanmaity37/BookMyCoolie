package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddCoolieRequest implements Serializable {

    @SerializedName("userMobile")
    String userMobile;
    @SerializedName("userName")
    String userName;
    @SerializedName("userStatus")
    Integer userStatus;
    @SerializedName("roleId")
    Integer roleId;
    @SerializedName("stationId")
    Integer stationId;
    @SerializedName("coolieBatchId")
    String coolieBatchId;
    @SerializedName("cooliePhoto")
    String cooliePhoto;

    @Override
    public String toString() {
        return "AddCoolieRequest{" +
                "userMobile='" + userMobile + '\'' +
                ", userName='" + userName + '\'' +
                ", userStatus=" + userStatus +
                ", roleId=" + roleId +
                ", stationId=" + stationId +
                ", coolieBatchId='" + coolieBatchId + '\'' +
                ", cooliePhoto='" + cooliePhoto + '\'' +
                '}';
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

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
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

    public AddCoolieRequest(String userMobile, String userName, Integer userStatus, Integer roleId, Integer stationId, String coolieBatchId, String cooliePhoto) {
        this.userMobile = userMobile;
        this.userName = userName;
        this.userStatus = userStatus;
        this.roleId = roleId;
        this.stationId = stationId;
        this.coolieBatchId = coolieBatchId;
        this.cooliePhoto = cooliePhoto;
    }
}
