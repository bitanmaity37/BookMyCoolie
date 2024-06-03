package com.cdac.iaf.bookmycoolie.models;

public class CoolieLoad {
    Integer id;
    String name;
    Integer count;
    Boolean isAssigned;

    public CoolieLoad(Integer id, String name, Integer count, Boolean isAssigned) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.isAssigned = isAssigned;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getAssigned() {
        return isAssigned;
    }

    public void setAssigned(Boolean assigned) {
        isAssigned = assigned;
    }

    @Override
    public String toString() {

        return "\nID: "+id+" Name: "+name+" Counter: "+count;
    }
}
