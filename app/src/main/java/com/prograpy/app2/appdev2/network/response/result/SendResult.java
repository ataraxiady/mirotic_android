package com.prograpy.app2.appdev2.network.response.result;

import com.google.gson.annotations.SerializedName;

public class SendResult {


    @SerializedName("success")
    private boolean success = false;

    @SerializedName("msg")
    private String msg = "";

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
}
