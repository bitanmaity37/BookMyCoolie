package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PassengerReqResponses implements Serializable {


        @SerializedName("passengerRequestId")
        Integer passengerRequestId;
        @SerializedName("approxTotalWeightage")
        Integer approxTotalWeightage;
        @SerializedName("bookingDate")
        String bookingDate;
        @SerializedName("bookingFor")
        String bookingFor;
        @SerializedName("bookingTentativeStartTime")
        String bookingTentativeStartTime;
        @SerializedName("bookingType")
        String bookingType;
        @SerializedName("noOfBags")
        Integer noOfBags;
        @SerializedName("stationAreaDropAt")
        Integer stationAreaDropAt;
        @SerializedName("stationAreaPickupFrom")
        Integer stationAreaPickupFrom;
        @SerializedName("stationAreaDropAtName")
        String stationAreaDropAtName;
        @SerializedName("stationAreaPickupFromName")
        String stationAreaPickupFromName;
        @SerializedName("serviceTypeName")
        String serviceTypeName;
        @SerializedName("passengerName")
        String passengerName;

        @SerializedName("bookingTentativeEndTime")
        String bookingTentativeEndTime;

    @Override
    public String toString() {
        return "PassengerReqResponses{" +
                "passengerRequestId=" + passengerRequestId +
                ", approxTotalWeightage=" + approxTotalWeightage +
                ", bookingDate='" + bookingDate + '\'' +
                ", bookingFor='" + bookingFor + '\'' +
                ", bookingTentativeStartTime='" + bookingTentativeStartTime + '\'' +
                ", bookingType='" + bookingType + '\'' +
                ", noOfBags=" + noOfBags +
                ", stationAreaDropAt=" + stationAreaDropAt +
                ", stationAreaPickupFrom=" + stationAreaPickupFrom +
                ", stationAreaDropAtName='" + stationAreaDropAtName + '\'' +
                ", stationAreaPickupFromName='" + stationAreaPickupFromName + '\'' +
                ", serviceTypeName='" + serviceTypeName + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", bookingTentativeEndTime='" + bookingTentativeEndTime + '\'' +
                '}';
    }

    public String getBookingTentativeEndTime() {
        return bookingTentativeEndTime;
    }

    public void setBookingTentativeEndTime(String bookingTentativeEndTime) {
        this.bookingTentativeEndTime = bookingTentativeEndTime;
    }

    public PassengerReqResponses(Integer passengerRequestId, Integer approxTotalWeightage, String bookingDate, String bookingFor, String bookingTentativeStartTime, String bookingType, Integer noOfBags, Integer stationAreaDropAt, Integer stationAreaPickupFrom, String stationAreaDropAtName, String stationAreaPickupFromName, String serviceTypeName, String passengerName, String bookingTentativeEndTime) {
        this.passengerRequestId = passengerRequestId;
        this.approxTotalWeightage = approxTotalWeightage;
        this.bookingDate = bookingDate;
        this.bookingFor = bookingFor;
        this.bookingTentativeStartTime = bookingTentativeStartTime;
        this.bookingType = bookingType;
        this.noOfBags = noOfBags;
        this.stationAreaDropAt = stationAreaDropAt;
        this.stationAreaPickupFrom = stationAreaPickupFrom;
        this.stationAreaDropAtName = stationAreaDropAtName;
        this.stationAreaPickupFromName = stationAreaPickupFromName;
        this.serviceTypeName = serviceTypeName;
        this.passengerName = passengerName;
        this.bookingTentativeEndTime = bookingTentativeEndTime;
    }

    public Integer getPassengerRequestId() {
        return passengerRequestId;
    }

    public void setPassengerRequestId(Integer passengerRequestId) {
        this.passengerRequestId = passengerRequestId;
    }

    public Integer getApproxTotalWeightage() {
        return approxTotalWeightage;
    }

    public void setApproxTotalWeightage(Integer approxTotalWeightage) {
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

    public Integer getNoOfBags() {
        return noOfBags;
    }

    public void setNoOfBags(Integer noOfBags) {
        this.noOfBags = noOfBags;
    }

    public Integer getStationAreaDropAt() {
        return stationAreaDropAt;
    }

    public void setStationAreaDropAt(Integer stationAreaDropAt) {
        this.stationAreaDropAt = stationAreaDropAt;
    }

    public Integer getStationAreaPickupFrom() {
        return stationAreaPickupFrom;
    }

    public void setStationAreaPickupFrom(Integer stationAreaPickupFrom) {
        this.stationAreaPickupFrom = stationAreaPickupFrom;
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

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
}
