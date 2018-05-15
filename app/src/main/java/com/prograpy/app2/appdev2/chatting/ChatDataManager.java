package com.prograpy.app2.appdev2.chatting;

import java.util.ArrayList;

/**
 * Created by samsung on 2018-05-15.
 */

public class ChatDataManager {
    private ArrayList<String> subjectList;

    public ChatDataManager(){
        subjectList = new ArrayList<String>();

    }

    public ArrayList<String> getSubjectList() {
        return subjectList;
    }

    public void addData(String s){
        subjectList.add(s);
    }

}
