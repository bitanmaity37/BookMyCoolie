package com.cdac.iaf.bookmycoolie.models;

import java.io.Serializable;

public class RequestList implements Serializable {

        Integer reqId;
        Integer reqType;
        String pName;
        Integer pId;
        String platform;

        String pPhn;


    @Override
    public String toString() {
        return "RequestList{" +
                "reqId=" + reqId +
                ", reqType=" + reqType +
                ", pName='" + pName + '\'' +
                ", pId=" + pId +
                ", platform='" + platform + '\'' +
                ", pPhn=" + pPhn +
                '}';
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public Integer getReqType() {
        return reqType;
    }

    public void setReqType(Integer reqType) {
        this.reqType = reqType;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getpPhn() {
        return pPhn;
    }

    public void setpPhn(String pPhn) {
        this.pPhn = pPhn;
    }

    public RequestList(Integer reqId, Integer reqType, String pName, Integer pId, String platform, String pPhn) {
        this.reqId = reqId;
        this.reqType = reqType;
        this.pName = pName;
        this.pId = pId;
        this.platform = platform;
        this.pPhn = pPhn;
    }
}
