package com.cdac.iaf.bookmycoolie.models.DynamicFare;

public class Fares {
    Integer stationID;
    String stationName;
    Integer rateCoolie;
    Integer rateWaiting;
    Integer rateBarrow;
    Integer rateWheelChairByTwo;
    Integer rateWheelChairByFour;

    public Integer getStationID() {
        return stationID;
    }

    public void setStationID(Integer stationID) {
        this.stationID = stationID;
    }

    public Fares(Integer stationID, String stationName, Integer rateCoolie, Integer rateWaiting, Integer rateBarrow) {
        this.stationID = stationID;
        this.stationName = stationName;
        this.rateCoolie = rateCoolie;
        this.rateWaiting = rateWaiting;
        this.rateBarrow = rateBarrow;
    }

    public Fares(Integer rateWheelChairByTwo, Integer rateWheelChairByFour) {
        this.rateWheelChairByTwo = rateWheelChairByTwo;
        this.rateWheelChairByFour = rateWheelChairByFour;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Integer getRateCoolie() {
        return rateCoolie;
    }

    public void setRateCoolie(Integer rateCoolie) {
        this.rateCoolie = rateCoolie;
    }

    public Integer getRateWaiting() {
        return rateWaiting;
    }

    public void setRateWaiting(Integer rateWaiting) {
        this.rateWaiting = rateWaiting;
    }

    public Integer getRateBarrow() {
        return rateBarrow;
    }

    public void setRateBarrow(Integer rateBarrow) {
        this.rateBarrow = rateBarrow;
    }

    public Integer getRateWheelChairByTwo() {
        return rateWheelChairByTwo;
    }

    public void setRateWheelChairByTwo(Integer rateWheelChairByTwo) {
        this.rateWheelChairByTwo = rateWheelChairByTwo;
    }

    public Integer getRateWheelChairByFour() {
        return rateWheelChairByFour;
    }

    public void setRateWheelChairByFour(Integer rateWheelChairByFour) {
        this.rateWheelChairByFour = rateWheelChairByFour;
    }

    @Override
    public String toString() {
        return "Fares{" +
                "stationName='" + stationName + '\'' +
                ", rateCoolie=" + rateCoolie +
                ", rateWaiting=" + rateWaiting +
                ", rateBarrow=" + rateBarrow +
                ", rateWheelChairByTwo=" + rateWheelChairByTwo +
                ", rateWheelChairByFour=" + rateWheelChairByFour +
                '}';
    }
}
