package com.prograpy.app2.appdev2.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by samsung on 2018-05-24.
 */

public class MyNoticeAdapter extends BaseAdapter{

    private Context context;
    private int layout;
    private ArrayList<MyNoticeData> myNoticeDataArrayList;
    private LayoutInflater layoutInflater;
    private NoticeDataManager noticeDataManager;

    public MyNoticeAdapter(Context context, int layout, ArrayList<MyNoticeData> list) {
        this.context = context;
        this.layout = layout;
        this.myNoticeDataArrayList = list;

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        noticeDataManager = new NoticeDataManager();
    }

    @Override
    public int getCount() {
        return myNoticeDataArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

//        final int pos = i;
//
//        RecyclerView.ViewHolder holder;
//
//        if (view == null){
//            view = layoutInflater.inflate(layout,viewGroup,false);
//
//
//        }


        return null;
    }
}
