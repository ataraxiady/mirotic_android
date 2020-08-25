package com.prograpy.app2.appdev2.notice;

/**
 * Created by samsung on 2018-05-24.
 */

public class MyNoticeData {

    private long _id;
    private String noticeText;


    public MyNoticeData(long _id, String noticeText) {
        this._id=_id;
        this.noticeText = noticeText;
    }

    public String getNoticeText() {
        return noticeText;
    }

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
