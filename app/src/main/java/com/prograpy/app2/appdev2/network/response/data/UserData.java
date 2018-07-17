package com.prograpy.app2.appdev2.network.response.data;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("ID")
    private String ID = "";

    @SerializedName("password")
    private String password = "";

    @SerializedName("kakaoKey")
    private String kakaoKey = "";

    @SerializedName("name")
    private String name="";

    @SerializedName("gender")
    private String gender="";

    @SerializedName("age")
    private int age=0;

    @SerializedName("area")
    private String area="";

    @SerializedName("profileimage")
    private String profileimage="";

    @SerializedName("bh_number_1")
    private int bh_number_1= 0;

    @SerializedName("sh_number_1")
    private int sh_number_1= 0;

    @SerializedName("bh_number_2")
    private int bh_number_2=0;

    @SerializedName("sh_number_2")
    private int sh_number_2=0;

    @SerializedName("bh_number_3")
    private int bh_number_3=0;

    @SerializedName("sh_number_3")
    private int sh_number_3=0;

    @SerializedName("fcmKey")
    private String fcmKey="";

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKakaoKey() {
        return kakaoKey;
    }

    public void setKakaoKey(String kakaoKey) {
        this.kakaoKey = kakaoKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public int getBh_number_1() {
        return bh_number_1;
    }

    public void setBh_number_1(int bh_number_1) {
        this.bh_number_1 = bh_number_1;
    }

    public int getSh_number_1() {
        return sh_number_1;
    }

    public void setSh_number_1(int sh_number_1) {
        this.sh_number_1 = sh_number_1;
    }

    public int getBh_number_2() {
        return bh_number_2;
    }

    public void setBh_number_2(int bh_number_2) {
        this.bh_number_2 = bh_number_2;
    }

    public int getSh_number_2() {
        return sh_number_2;
    }

    public void setSh_number_2(int sh_number_2) {
        this.sh_number_2 = sh_number_2;
    }

    public int getBh_number_3() {
        return bh_number_3;
    }

    public void setBh_number_3(int bh_number_3) {
        this.bh_number_3 = bh_number_3;
    }

    public int getSh_number_3() {
        return sh_number_3;
    }

    public void setSh_number_3(int sh_number_3) {
        this.sh_number_3 = sh_number_3;
    }

    public String getFcmKey() {
        return fcmKey;
    }

    public void setFcmKey(String fcmKey) {
        this.fcmKey = fcmKey;
    }
}
