package com.prograpy.app2.appdev2.network.response;

import com.google.gson.annotations.SerializedName;
import com.prograpy.app2.appdev2.network.response.data.Fragment;

import java.util.ArrayList;

public class FragmentResult {

    @SerializedName("success")
    private boolean success = false;

    @SerializedName("error")
    private String error = "";

    @SerializedName("infoList")
    private ArrayList<Fragment> infoList = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<Fragment> getInfoList() {
        return infoList;
    }

    public void setInfoList(ArrayList<Fragment> infoList) {
        this.infoList = infoList;
    }
}
