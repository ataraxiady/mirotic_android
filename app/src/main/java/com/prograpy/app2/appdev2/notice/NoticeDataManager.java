package com.prograpy.app2.appdev2.notice;

import java.util.ArrayList;

/**
 * Created by samsung on 2018-05-24.
 */

public class NoticeDataManager {

    private ArrayList<MyNoticeData> list;

    public NoticeDataManager() {
        list = new ArrayList<MyNoticeData>();

        list.add(new MyNoticeData(1,"강호동님께서 좋아요를 눌렀습니다.대화를 시작하시겠습니까?"));
        list.add(new MyNoticeData(2,"이수근님께서 좋아요를 눌렀습니다.대화를 시작하시겠습니까?"));
    }

    public ArrayList<MyNoticeData> getList() {return list;}
}
