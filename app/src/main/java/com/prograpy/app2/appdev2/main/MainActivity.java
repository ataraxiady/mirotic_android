package com.prograpy.app2.appdev2.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.network.response.data.HobbyData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mainPager;
    private MainViewPagerAdapter mainViewPagerAdapter;


    private ArrayList<HobbyData> bigHobbyList = new ArrayList<HobbyData>();
    private ArrayList<HobbyData> smallHobbyList = new ArrayList<HobbyData>();

    private RelativeLayout tabMatch, tabMy, tabChat, tabSetting;
    private ImageView tabMatchSel, tabMySel, tabChatSel, tabSettingSel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bigHobbyList = getIntent().getParcelableArrayListExtra("bigHobby");
        smallHobbyList = getIntent().getParcelableArrayListExtra("smallHobby");


        tabMatch = (RelativeLayout) findViewById(R.id.tab_match);
        tabMy = (RelativeLayout) findViewById(R.id.tab_my);
        tabChat = (RelativeLayout) findViewById(R.id.tab_chat);
        tabSetting = (RelativeLayout) findViewById(R.id.tab_set);

        tabMatchSel = (ImageView) findViewById(R.id.tab_match_sel);
        tabMySel = (ImageView) findViewById(R.id.tab_my_sel);
        tabChatSel = (ImageView) findViewById(R.id.tab_chat_sel);
        tabSettingSel = (ImageView) findViewById(R.id.tab_set_sel);

        tabMatch.setOnClickListener(this);
        tabMy.setOnClickListener(this);
        tabChat.setOnClickListener(this);
        tabSetting.setOnClickListener(this);

        tabMatch.setSelected(true);

        mainViewPagerAdapter = new MainViewPagerAdapter(this.getSupportFragmentManager());
        mainViewPagerAdapter.setHobbyList(bigHobbyList, smallHobbyList);

        mainPager = (ViewPager) findViewById(R.id.main_viewpager);
        mainPager.setOffscreenPageLimit(4);
        mainPager.setAdapter(mainViewPagerAdapter);
        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tabMatchSel.setVisibility(View.VISIBLE);
                        tabMySel.setVisibility(View.GONE);
                        tabChatSel.setVisibility(View.GONE);
                        tabSettingSel.setVisibility(View.GONE);
                        break;

                    case 1:
                        tabMatchSel.setVisibility(View.GONE);
                        tabMySel.setVisibility(View.VISIBLE);
                        tabChatSel.setVisibility(View.GONE);
                        tabSettingSel.setVisibility(View.GONE);
                        break;

                    case 2:
                        tabMatchSel.setVisibility(View.GONE);
                        tabMySel.setVisibility(View.GONE);
                        tabChatSel.setVisibility(View.VISIBLE);
                        tabSettingSel.setVisibility(View.GONE);
                        break;

                    case 3:
                        tabMatchSel.setVisibility(View.GONE);
                        tabMySel.setVisibility(View.GONE);
                        tabChatSel.setVisibility(View.GONE);
                        tabSettingSel.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @Override
    public void onClick(View v) {
        int position = 0;

        switch (v.getId()){

            case R.id.tab_match:
                tabMatchSel.setVisibility(View.VISIBLE);
                tabMySel.setVisibility(View.GONE);
                tabChatSel.setVisibility(View.GONE);
                tabSettingSel.setVisibility(View.GONE);
                position = 0;
                break;

            case R.id.tab_my:
                tabMatchSel.setVisibility(View.GONE);
                tabMySel.setVisibility(View.VISIBLE);
                tabChatSel.setVisibility(View.GONE);
                tabSettingSel.setVisibility(View.GONE);
                position = 1;
                break;

            case R.id.tab_chat:
                tabMatchSel.setVisibility(View.GONE);
                tabMySel.setVisibility(View.GONE);
                tabChatSel.setVisibility(View.VISIBLE);
                tabSettingSel.setVisibility(View.GONE);
                position = 2;
                break;

            case R.id.tab_set:

                tabMatchSel.setVisibility(View.GONE);
                tabMySel.setVisibility(View.GONE);
                tabChatSel.setVisibility(View.GONE);
                tabSettingSel.setVisibility(View.VISIBLE);
                position = 3;
                break;
        }

        mainPager.setCurrentItem(position, true);
    }
}
