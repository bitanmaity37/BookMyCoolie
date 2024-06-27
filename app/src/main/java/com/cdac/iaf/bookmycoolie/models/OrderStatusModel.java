package com.cdac.iaf.bookmycoolie.models;

import java.sql.Timestamp;

public class OrderStatusModel {

    private int passengerRequestId;
    private String stationAreaDropAtName;
    private String stationAreaPickupFromName;
    private int requestStatus;
    private int serviceType;
    private int noOfBags;
    private String stationName;
    private Timestamp bookingDate;
    private Timestamp recordTracking;

    public OrderStatusModel(int passengerRequestId, String stationAreaDropAtName,
                            String stationAreaPickupFromName, int requestStatus,
                            int serviceType, int noOfBags, String stationName,
                            Timestamp bookingDate, Timestamp recordTracking) {
        this.passengerRequestId = passengerRequestId;
        this.stationAreaDropAtName = stationAreaDropAtName;
        this.stationAreaPickupFromName = stationAreaPickupFromName;
        this.requestStatus = requestStatus;
        this.serviceType = serviceType;
        this.noOfBags = noOfBags;
        this.stationName = stationName;
        this.bookingDate = bookingDate;
        this.recordTracking = recordTracking;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Timestamp getRecordTracking() {
        return recordTracking;
    }

    public void setRecordTracking(Timestamp recordTracking) {
        this.recordTracking = recordTracking;
    }

    public int getPassengerRequestId() {
        return passengerRequestId;
    }

    public void setPassengerRequestId(int passengerRequestId) {
        this.passengerRequestId = passengerRequestId;
    }

    public String getStationAreaDropAtName() {
        return stationAreaDropAtName;
    }

    public void setStationAreaDropAtName(String stationAreaDropAtName) {
        this.stationAreaDropAtName = stationAreaDropAtName;
    }

    public String getStationAreaPickupFromName() {
        return stationAreaPickupFromName;
    }

    public void setStationAreaPickupFromName(String stationAreaPickupFromName) {
        this.stationAreaPickupFromName = stationAreaPickupFromName;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(int noOfBags) {
        this.noOfBags = noOfBags;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "OrderStatusModel{" +
                "passengerRequestId=" + passengerRequestId +
                ", stationAreaDropAtName='" + stationAreaDropAtName + '\'' +
                ", stationAreaPickupFromName='" + stationAreaPickupFromName + '\'' +
                ", requestStatus=" + requestStatus +
                ", serviceType=" + serviceType +
                ", noOfBags=" + noOfBags +
                ", stationName='" + stationName + '\'' +
                '}';
    }
}
