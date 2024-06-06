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
            String roleId;

    public LoginResponse(String jwtToken, String username, String roleName, String roleId) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.roleName = roleName;
        this.roleId = roleId;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "jwtToken='" + jwtToken + '\'' +
                ", username='" + username + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
