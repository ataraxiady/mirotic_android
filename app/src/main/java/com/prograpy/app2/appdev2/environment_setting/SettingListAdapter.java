package com.prograpy.app2.appdev2.environment_setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app2.appdev2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by User on 2018-05-14.
 */

public class SettingListAdapter extends BaseAdapter {
    Context context;
    ArrayList<List_item> list_itemArrayList;

    // listitem.xml과 연결
    TextView setting_textview;
    ImageView setting_image;
    ImageView setting_image2;

    public SettingListAdapter(Context context, ArrayList<List_item>list_itemArrayList){
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }


    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int postion) {
        return list_itemArrayList.get(postion);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //순차적으로 한칸씩 화면을 구성해주는 부분
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.environment_listitem,null);
            setting_textview = (TextView)convertView.findViewById(R.id.setting_textview);
            setting_image = (ImageView)convertView.findViewById(R.id.setting_imagelogo);
            setting_image2 = (ImageView)convertView.findViewById(R.id.setting_imagenext);
        }
        setting_textview.setText(list_itemArrayList.get(position).getSetting_text());
        setting_image.setImageResource(list_itemArrayList.get(position).getSetting_image());
        setting_image2.setImageResource(list_itemArrayList.get(position).getSetting_image());
        return convertView;
    }
}
