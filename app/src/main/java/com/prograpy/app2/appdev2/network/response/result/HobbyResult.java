package com.prograpy.app2.appdev2.network.response.result;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app2.appdev2.network.response.data.HobbyData;

import java.util.ArrayList;

public class HobbyResult {


    @SerializedName("success")
    private boolean success = false;

    @SerializedName("bigHobbyList")
    private ArrayList<HobbyData> bigHobbyList = new ArrayList<HobbyData>();

    @SerializedName("smallHobbyList")
    private ArrayList<HobbyData> smallHobbyList = new ArrayList<HobbyData>();


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<HobbyData> getBigHobbyList() {
        return bigHobbyList;
    }

    public void setBigHobbyList(ArrayList<HobbyData> bigHobbyList) {
        this.bigHobbyList = bigHobbyList;
    }

    public ArrayList<HobbyData> getSmallHobbyList() {
        return smallHobbyList;
    }

    public void setSmallHobbyList(ArrayList<HobbyData> smallHobbyList) {
        this.smallHobbyList = smallHobbyList;
    }
}
