package com.cdac.iaf.bookmycoolie.models;

public class AddCoolieResponse {

    Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public AddCoolieResponse(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AddCoolieResponse{" +
                "userId=" + userId +
                '}';
    }
}
