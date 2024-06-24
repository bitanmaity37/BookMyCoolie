package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {

            @SerializedName("jwtToken")
            String jwtToken;
            @SerializedName("username")
            String username;

            @SerializedName("roleName")
            String roleName;
            @SerializedName("roleId")
            Integer roleId;

            @SerializedName("userId")
            Integer userId;
            @SerializedName("stationId")
            Integer stationId;

    public LoginResponse(String jwtToken, String username, String roleName, Integer roleId, Integer userId, Integer stationId) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.roleName = roleName;
        this.roleId = roleId;
        this.userId = userId;
        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "jwtToken='" + jwtToken + '\'' +
                ", username='" + username + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleId=" + roleId +
                ", userId=" + userId +
                ", stationId=" + stationId +
                '}';
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }
}
