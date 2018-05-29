package com.prograpy.app2.appdev2.notice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.prograpy.app2.appdev2.R;

import java.util.ArrayList;

/**
 * Created by samsung on 2018-05-23.
 */

public class NoticeActivity extends AppCompatActivity{
    private ArrayList<MyNoticeData> myNoticeDataArrayList;
    private MyNoticeAdapter adapter;
    private ListView listView;
    private NoticeDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = new NoticeDataManager();

        myNoticeDataArrayList = dataManager.getList();

        adapter = new MyNoticeAdapter(this,R.layout.list_notice,myNoticeDataArrayList);

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
