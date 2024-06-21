package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaveAttendanceModel implements Serializable {

    @SerializedName("coolieId")
    Integer coolieId;
    @SerializedName("inOut")
    Boolean inOut;

    public SaveAttendanceModel(Integer coolieId, Boolean inOut) {
        this.coolieId = coolieId;
        this.inOut = inOut;
    }

    public Integer getCoolieId() {
        return coolieId;
    }

    public void setCoolieId(Integer coolieId) {
        this.coolieId = coolieId;
    }

    public Boolean getInOut() {
        return inOut;
    }

    public void setInOut(Boolean inOut) {
        this.inOut = inOut;
    }

    @Override
    public String toString() {
        return "SaveAttendanceModel{" +
                "coolieId=" + coolieId +
                ", inOut=" + inOut +
                '}';
    }
}
