package com.cdac.iaf.bookmycoolie.models;

public class OrderDetailsModel {

    private int coolieId;
    private String coolieBatchId;
    private String cooliePhoto;
    private String userName;
    private String userMobile;

    public OrderDetailsModel(int coolieId, String coolieBatchId, String cooliePhoto, String userName, String userMobile) {
        this.coolieId = coolieId;
        this.coolieBatchId = coolieBatchId;
        this.cooliePhoto = cooliePhoto;
        this.userName = userName;
        this.userMobile = userMobile;
    }

    public int getCoolieId() {
        return coolieId;
    }

    public void setCoolieId(int coolieId) {
        this.coolieId = coolieId;
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

    @Override
    public String toString() {
        return "OrderDetailsModel{" +
                "coolieId=" + coolieId +
                ", coolieBatchId='" + coolieBatchId + '\'' +
                ", cooliePhoto='" + cooliePhoto + '\'' +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                '}';
    }
}
