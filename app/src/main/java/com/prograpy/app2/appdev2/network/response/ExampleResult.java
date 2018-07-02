package com.prograpy.app2.appdev2.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app2.appdev2.network.response.data.UserInfoList;

import java.util.ArrayList;

/**
 * Created by samsung on 2018-06-01.
 */

public class ExampleResult {

    @SerializedName("success")
    private boolean success = false;

    @SerializedName("userInfoList")
    private ArrayList<UserInfoList> userInfoList = null;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public ArrayList<UserInfoList> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(ArrayList<UserInfoList> userInfoList) {
        this.userInfoList = userInfoList;
    }
}
