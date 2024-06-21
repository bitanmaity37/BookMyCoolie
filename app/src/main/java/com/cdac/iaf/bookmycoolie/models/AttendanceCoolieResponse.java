package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

public class AttendanceCoolieResponse {
    @SerializedName("coolieId")
    Integer coolieId;
    @SerializedName("userName")
    String userName;
    @SerializedName("coolieBatchId")
    String coolieBatchId;
    @SerializedName("coolieStatus")
    Boolean coolieStatus;

    @Override
    public String toString() {
        return "AttendanceCoolieResponse{" +
                "coolieId=" + coolieId +
                ", userName='" + userName + '\'' +
                ", coolieBatchId='" + coolieBatchId + '\'' +
                ", coolieStatus='" + coolieStatus + '\'' +
                '}';
    }

    public Integer getCoolieId() {
        return coolieId;
    }

    public void setCoolieId(Integer coolieId) {
        this.coolieId = coolieId;
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

    public Boolean getCoolieStatus() {
        return coolieStatus;
    }

    public void setCoolieStatus(Boolean coolieStatus) {
        this.coolieStatus = coolieStatus;
    }

    public AttendanceCoolieResponse(Integer coolieId, String userName, String coolieBatchId, Boolean coolieStatus) {
        this.coolieId = coolieId;
        this.userName = userName;
        this.coolieBatchId = coolieBatchId;
        this.coolieStatus = coolieStatus;
    }
}
