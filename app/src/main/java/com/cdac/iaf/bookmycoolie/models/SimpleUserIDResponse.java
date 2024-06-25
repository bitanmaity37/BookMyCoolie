package com.cdac.iaf.bookmycoolie.models;

public class SimpleUserIDResponse {

    Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public SimpleUserIDResponse(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AddCoolieResponse{" +
                "userId=" + userId +
                '}';
    }
}
