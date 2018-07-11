package com.prograpy.app2.appdev2.network.response.result;

import com.google.gson.annotations.SerializedName;

/**
 * 서버에서 전달받은 데이터를 파싱해주는 클래스
 *
 */
public class JoinResult {

    // 서버의 json 데이터중 어떤 키값의 데이터를 파싱시킬 건지 정하는 게
    // @SerializedName 부분


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
