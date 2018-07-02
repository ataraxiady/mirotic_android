package com.prograpy.app2.appdev2.network.response;

import com.google.gson.annotations.SerializedName;

public class JoinResult {


    @SerializedName("success")
    private boolean success = false;

    @SerializedName("msg")
    private String msg = "";
}
