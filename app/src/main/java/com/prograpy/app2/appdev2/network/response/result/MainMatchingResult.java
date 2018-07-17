package com.prograpy.app2.appdev2.network.response.result;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app2.appdev2.network.response.data.UserData;

import java.util.ArrayList;

public class MainMatchingResult {


    @SerializedName("success")
    private boolean success = false;

    @SerializedName("msg")
    private String msg = "";

    @SerializedName("userInfos")
    private ArrayList<UserData> userInfos = new ArrayList<UserData>();


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<UserData> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(ArrayList<UserData> userInfos) {
        this.userInfos = userInfos;
    }
}
