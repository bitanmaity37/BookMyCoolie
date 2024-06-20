package com.cdac.iaf.bookmycoolie.models;

public class PassengerRequestsModel {

            Integer stationId;
            Integer serviceType;
            Integer requestStatus;

    @Override
    public String toString() {
        return "PassengerRequestsModel{" +
                "stationId=" + stationId +
                ", serviceType=" + serviceType +
                ", requestStatus=" + requestStatus +
                '}';
    }

    public PassengerRequestsModel(Integer stationId, Integer serviceType, Integer requestStatus) {
        this.stationId = stationId;
        this.serviceType = serviceType;
        this.requestStatus = requestStatus;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }
}
