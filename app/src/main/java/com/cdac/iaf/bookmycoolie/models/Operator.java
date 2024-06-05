package com.cdac.iaf.bookmycoolie.models;

import java.io.Serializable;

public class Operator implements Serializable {
    Integer id;
    String name;
    String phone;
    String email;
    String password;
    Integer stn_id;
    String station;

    public Operator(Integer id, String name, String phone, String email, String password, Integer stn_id, String station) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.stn_id = stn_id;
        this.station = station;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStn_id() {
        return stn_id;
    }

    public void setStn_id(Integer stn_id) {
        this.stn_id = stn_id;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", stn_id=" + stn_id +
                ", station='" + station + '\'' +
                '}';
    }
}
