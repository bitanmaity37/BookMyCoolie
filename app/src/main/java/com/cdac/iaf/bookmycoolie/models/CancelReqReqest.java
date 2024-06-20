package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CancelReqReqest implements Serializable {

    @SerializedName("passengerRequestId")
    Integer passengerRequestId;

    public CancelReqReqest(Integer passengerRequestId) {
        this.passengerRequestId = passengerRequestId;
    }

    public Integer getPassengerRequestId() {
        return passengerRequestId;
    }

    public void setPassengerRequestId(Integer passengerRequestId) {
        this.passengerRequestId = passengerRequestId;
    }

    @Override
    public String toString() {
        return "CancelReqReqest{" +
                "passengerRequestId=" + passengerRequestId +
                '}';
    }
}
