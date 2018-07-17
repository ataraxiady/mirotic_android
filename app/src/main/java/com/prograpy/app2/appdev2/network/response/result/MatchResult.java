package com.prograpy.app2.appdev2.network.response.result;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app2.appdev2.network.response.data.MatchUserData;
import com.prograpy.app2.appdev2.network.response.data.UserData;

import java.util.ArrayList;

public class MatchResult {

    @SerializedName("success")
    private boolean success = false;

    @SerializedName("msg")
    private String msg = "";

    @SerializedName("matchUsers")
    private ArrayList<MatchUserData> matchUsers = new ArrayList<MatchUserData>();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<MatchUserData> getMatchUsers() {
        return matchUsers;
    }

    public void setMatchUsers(ArrayList<MatchUserData> matchUsers) {
        this.matchUsers = matchUsers;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
