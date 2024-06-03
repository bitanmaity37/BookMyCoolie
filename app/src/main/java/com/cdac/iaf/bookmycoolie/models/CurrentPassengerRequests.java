package com.cdac.iaf.bookmycoolie.models;

public class CurrentPassengerRequests {
    Integer requestId;
    String passengerName;
    String date;
    String time;
    Integer requirementNo;

    public CurrentPassengerRequests(Integer requestId, String passengerName, String date, String time, Integer requirementNo) {
        this.requestId = requestId;
        this.passengerName = passengerName;
        this.date = date;
        this.time = time;
        this.requirementNo = requirementNo;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRequirementNo() {
        return requirementNo;
    }

    public void setRequirementNo(Integer requirementNo) {
        this.requirementNo = requirementNo;
    }

    @Override
    public String toString() {
        return String.valueOf(requestId);
    }
}
