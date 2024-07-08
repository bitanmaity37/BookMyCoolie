package com.cdac.iaf.bookmycoolie.models;

public class CoolieRequestModel {
    int approxTotalWeightage;
    String bookingDate;
    String bookingFor;
    String bookingTentativeEndTime;
    String bookingTentativeStartTime;
    String bookingType;
    int noOfBags;
    int noOfCart=0;
    int noOfWheelChair=0;
    String recordTracking;
    int stationAreaDropAt;
    int stationAreaPickupFrom;
    int stationId;
    int serviceType;
    int requestStatus;
    int userMaster;
    String trainName;
    String trainNumber;

    @Override
    public String toString() {
        return "CoolieRequestModel{" +
                "approxTotalWeightage=" + approxTotalWeightage +
                ", bookingDate=" + bookingDate +
                ", bookingFor='" + bookingFor + '\'' +
                ", bookingTentativeEndTime=" + bookingTentativeEndTime +
                ", bookingTentativeStartTime=" + bookingTentativeStartTime +
                ", bookingType='" + bookingType + '\'' +
                ", noOfBags=" + noOfBags +
                ", recordTracking=" + recordTracking +
                ", stationAreaDropAt=" + stationAreaDropAt +
                ", stationAreaPickupFrom=" + stationAreaPickupFrom +
                ", stationId=" + stationId +
                ", serviceType=" + serviceType +
                ", requestStatus=" + requestStatus +
                ", userMaster=" + userMaster +
                '}';
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getNoOfCart() {
        return noOfCart;
    }

    public void setNoOfCart(int noOfCart) {
        this.noOfCart = noOfCart;
    }

    public int getNoOfWheelChair() {
        return noOfWheelChair;
    }

    public void setNoOfWheelChair(int noOfWheelChair) {
        this.noOfWheelChair = noOfWheelChair;
    }

    public int getApproxTotalWeightage() {
        return approxTotalWeightage;
    }

    public void setApproxTotalWeightage(int approxTotalWeightage) {
        this.approxTotalWeightage = approxTotalWeightage;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingFor() {
        return bookingFor;
    }

    public void setBookingFor(String bookingFor) {
        this.bookingFor = bookingFor;
    }

    public String getBookingTentativeEndTime() {
        return bookingTentativeEndTime;
    }

    public void setBookingTentativeEndTime(String bookingTentativeEndTime) {
        this.bookingTentativeEndTime = bookingTentativeEndTime;
    }

    public String getBookingTentativeStartTime() {
        return bookingTentativeStartTime;
    }

    public void setBookingTentativeStartTime(String bookingTentativeStartTime) {
        this.bookingTentativeStartTime = bookingTentativeStartTime;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public int getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(int noOfBags) {
        this.noOfBags = noOfBags;
    }

    public String getRecordTracking() {
        return recordTracking;
    }

    public void setRecordTracking(String recordTracking) {
        this.recordTracking = recordTracking;
    }

    public int getStationAreaDropAt() {
        return stationAreaDropAt;
    }

    public void setStationAreaDropAt(int stationAreaDropAt) {
        this.stationAreaDropAt = stationAreaDropAt;
    }

    public int getStationAreaPickupFrom() {
        return stationAreaPickupFrom;
    }

    public void setStationAreaPickupFrom(int stationAreaPickupFrom) {
        this.stationAreaPickupFrom = stationAreaPickupFrom;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public int getUserMaster() {
        return userMaster;
    }

    public void setUserMaster(int userMaster) {
        this.userMaster = userMaster;
    }
}
