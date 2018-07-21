package com.prograpy.app2.appdev2.network.response.result;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app2.appdev2.network.response.data.UserData;

import java.util.ArrayList;

/**
 * Created by User on 2018-07-11.
 */

public class MyInfoResult {
    @SerializedName("success")
    private boolean success = false;

    @SerializedName("error")
    private String error = "";

    @SerializedName("userInfos")
    private ArrayList<UserData> userInfos = new ArrayList<UserData>();


    // 성공했을 시
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    // 에러났을 시
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<UserData> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(ArrayList<UserData> userInfos) {
        this.userInfos = userInfos;
    }
}
