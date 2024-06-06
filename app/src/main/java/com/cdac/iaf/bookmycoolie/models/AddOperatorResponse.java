package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddOperatorResponse implements Serializable {


        @SerializedName("userId")
        Integer userId;

    public AddOperatorResponse(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AddOperatorResponse{" +
                "userId=" + userId +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
