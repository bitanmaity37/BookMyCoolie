package com.cdac.iaf.bookmycoolie.models;

public class GetCoolieRequest {

    Integer userId;

    @Override
    public String toString() {
        return "GetCoolieRequest{" +
                "userId=" + userId +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GetCoolieRequest(Integer userId) {
        this.userId = userId;
    }
}
