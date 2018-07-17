package com.prograpy.app2.appdev2.network.response.result;

import com.google.gson.annotations.SerializedName;

public class LikeDislikeResult {

    @SerializedName("success")
    private boolean success = false;

    @SerializedName("error")
    private String error = "";

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
}
