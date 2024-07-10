package com.cdac.iaf.bookmycoolie.models;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StationEditAddAreaResponse implements Serializable {

    @SerializedName("message")
    String message;
    @SerializedName("alreadyExitList")
    ArrayList<String> alreadyExitList;

    public String getMessage() {
        return message;
    }

    public ArrayList<String> getAlreadyExitList() {
        return alreadyExitList;
    }

    public StationEditAddAreaResponse(String message, ArrayList<String> alreadyExitList) {
        this.message = message;
        this.alreadyExitList = alreadyExitList;
    }

    @Override
    public String toString() {
        return "StationEditAddAreaResponse{" +
                "message='" + message + '\'' +
                ", alreadyExitList=" + alreadyExitList +
                '}';
    }
}
