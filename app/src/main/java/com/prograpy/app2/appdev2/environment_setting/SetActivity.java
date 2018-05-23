package com.prograpy.app2.appdev2.environment_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;

import java.util.ArrayList;
import java.util.List;

public class SetActivity extends AppCompatActivity {
    ListView listView;
    SettingListAdapter settingListAdapter;
    ArrayList<List_item> list_itemArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        listView = (ListView)findViewById(R.id.listView);

        list_itemArrayList = new ArrayList<List_item>();

        list_itemArrayList.add(new List_item(R.mipmap.ic_launcher,"버전정보",R.mipmap.ic_launcher));
        list_itemArrayList.add(new List_item(R.mipmap.ic_launcher,"이용약관",R.mipmap.ic_launcher));
        list_itemArrayList.add(new List_item(R.mipmap.ic_launcher,"Q / A",R.mipmap.ic_launcher));
        list_itemArrayList.add(new List_item(R.mipmap.ic_launcher,"기타",R.mipmap.ic_launcher));
        list_itemArrayList.add(new List_item(R.mipmap.ic_launcher,"고객센터/도움말",R.mipmap.ic_launcher));

        settingListAdapter = new SettingListAdapter(SetActivity.this,list_itemArrayList);
        listView.setAdapter(settingListAdapter);

  }
}
