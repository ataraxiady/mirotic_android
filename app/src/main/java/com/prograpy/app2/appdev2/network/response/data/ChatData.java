package com.prograpy.app2.appdev2.network.response.data;

public class ChatData {

    private String chatMsg = "";

    private String chatTime = "";

    // 0 이면 나 1 이면 상대
    private int chatType = 0;

    private String chatImage = "";


    public String getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(String chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
    }
}
