package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coolie implements Serializable {

    Integer coolieId;
    String cooliePhoto;
    String userMobile;
    String userName;
    String coolieBatchId;

    @Override
    public String toString() {
        return "Coolie{" +
                "coolieId=" + coolieId +
                ", cooliePhoto='" + cooliePhoto + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userName='" + userName + '\'' +
                ", coolieBatchId=" + coolieBatchId +
                '}';
    }

    public Coolie() {
    }

    public Coolie(Integer coolieId, String cooliePhoto, String userMobile, String userName, String coolieBatchId) {
        this.coolieId = coolieId;
        this.cooliePhoto = cooliePhoto;
        this.userMobile = userMobile;
        this.userName = userName;
        this.coolieBatchId = coolieBatchId;
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
}
