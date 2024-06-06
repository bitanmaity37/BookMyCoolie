package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddOperatorRequest implements Serializable {
    @SerializedName("userEmailId")
    String userEmailId;
    @SerializedName("userEmpId")
    String userEmpId;
    @SerializedName("userIsAuthenticated")
    Boolean userIsAuthenticated;
    @SerializedName("userMobile")
    String userMobile;
    @SerializedName("userName")
    String userName;
    @SerializedName("userPassword")
    String userPassword;
    @SerializedName("userStatus")
    Integer userStatus;
    @SerializedName("roleId")
    Integer roleId;
    @SerializedName("stationId")
    Integer stationId;

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserEmpId() {
        return userEmpId;
    }

    public void setUserEmpId(String userEmpId) {
        this.userEmpId = userEmpId;
    }

    public Boolean getUserIsAuthenticated() {
        return userIsAuthenticated;
    }

    public void setUserIsAuthenticated(Boolean userIsAuthenticated) {
        this.userIsAuthenticated = userIsAuthenticated;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return "AddOperatorRequest{" +
                "userEmailId='" + userEmailId + '\'' +
                ", userEmpId='" + userEmpId + '\'' +
                ", userIsAuthenticated=" + userIsAuthenticated +
                ", userMobile='" + userMobile + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userStatus=" + userStatus +
                ", roleId=" + roleId +
                ", stationId=" + stationId +
                '}';
    }

    public AddOperatorRequest(String userEmailId, String userEmpId, Boolean userIsAuthenticated, String userMobile, String userName, String userPassword, Integer userStatus, Integer roleId, Integer stationId) {
        this.userEmailId = userEmailId;
        this.userEmpId = userEmpId;
        this.userIsAuthenticated = userIsAuthenticated;
        this.userMobile = userMobile;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
        this.roleId = roleId;
        this.stationId = stationId;
    }
}
