package com.prograpy.app2.appdev2.network.response.result;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 2018-07-11.
 */

public class DataReceiveResult {
    @SerializedName("success")
    private boolean success = false;

    @SerializedName("error")
    private String error = "";
    private String name = "";
    private String gender = "";
    private String area = "";
    private int age;
    private String First_main = "";

    // 1순위 취미
    public String getFirst_main() { return First_main; }

    public void setFirst_main(String first_main) { First_main = first_main; }

    // 나이
    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }
    // 성별
    public String getGender() { return gender;}

    public void setGender(String gender) { this.gender = gender; }
    // 사는 곳
    public String getArea() { return area; }

    public void setArea(String area) { this.area = area; }
    // 이름
    public void setName(String name){
        this.name = name;
    }

    public String getName() { return name; }
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



}
