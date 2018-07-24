package com.prograpy.app2.appdev2.network.response.data;

import android.os.Parcel;
import android.os.Parcelable;

public class HobbyData implements Parcelable {

    private int hobby_big_num = 0;

    private int hobby_num = 0;

    private String hobby_name = "";

    protected HobbyData(Parcel in) {
        hobby_big_num = in.readInt();
        hobby_num = in.readInt();
        hobby_name = in.readString();
    }

    public static final Creator<HobbyData> CREATOR = new Creator<HobbyData>() {
        @Override
        public HobbyData createFromParcel(Parcel in) {
            return new HobbyData(in);
        }

        @Override
        public HobbyData[] newArray(int size) {
            return new HobbyData[size];
        }
    };

    public int getHobby_big_num() {
        return hobby_big_num;
    }

    public void setHobby_big_num(int hobby_big_num) {
        this.hobby_big_num = hobby_big_num;
    }

    public int getHobby_num() {
        return hobby_num;
    }

    public void setHobby_num(int hobby_num) {
        this.hobby_num = hobby_num;
    }

    public String getHobby_name() {
        return hobby_name;
    }

    public void setHobby_name(String hobby_name) {
        this.hobby_name = hobby_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hobby_big_num);
        dest.writeInt(hobby_num);
        dest.writeString(hobby_name);
    }
}
