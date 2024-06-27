package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FreeCoolieResponse implements Serializable {

    @SerializedName("coolieId")
    Integer coolieId;
    @SerializedName("userName")
    String userName;
    @SerializedName("coolieBatchId")
    String coolieBatchId;

    public FreeCoolieResponse(Integer coolieId, String userName, String coolieBatchId) {
        this.coolieId = coolieId;
        this.userName = userName;
        this.coolieBatchId = coolieBatchId;
    }

    @Override
    public String toString() {
        return "Name: " + userName+", Billa: "+coolieBatchId;
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
}
