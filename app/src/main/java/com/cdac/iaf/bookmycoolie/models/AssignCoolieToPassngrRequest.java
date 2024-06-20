package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AssignCoolieToPassngrRequest implements Serializable {
    @SerializedName("passangerRequestId")
    Integer passangerRequestId;
    @SerializedName("coolieMasterIds")
    ArrayList<Integer> coolieMasterIds;

    public AssignCoolieToPassngrRequest(Integer passangerRequestId, ArrayList<Integer> coolieMasterIds) {
        this.passangerRequestId = passangerRequestId;
        this.coolieMasterIds = coolieMasterIds;
    }

    @Override
    public String toString() {
        return "AssignCoolieToPassngrRequest{" +
                "passangerRequestId=" + passangerRequestId +
                ", coolieMasterIds=" + coolieMasterIds +
                '}';
    }

    public Integer getPassangerRequestId() {
        return passangerRequestId;
    }

    public void setPassangerRequestId(Integer passangerRequestId) {
        this.passangerRequestId = passangerRequestId;
    }

    public ArrayList<Integer> getCoolieMasterIds() {
        return coolieMasterIds;
    }

    public void setCoolieMasterIds(ArrayList<Integer> coolieMasterIds) {
        this.coolieMasterIds = coolieMasterIds;
    }
}
