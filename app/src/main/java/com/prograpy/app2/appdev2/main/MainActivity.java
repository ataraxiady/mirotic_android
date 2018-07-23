package com.prograpy.app2.appdev2.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.prograpy.app2.appdev2.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mainPager;
    private MainViewPagerAdapter mainViewPagerAdapter;

    private ImageView tabMatch, tabMy, tabChat, tabSetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        tabMatch = (ImageView) findViewById(R.id.tab_match);
        tabMy = (ImageView) findViewById(R.id.tab_my);
        tabChat = (ImageView) findViewById(R.id.tab_chat);
        tabSetting = (ImageView) findViewById(R.id.tab_set);

        tabMatch.setOnClickListener(this);
        tabMy.setOnClickListener(this);
        tabChat.setOnClickListener(this);
        tabSetting.setOnClickListener(this);

        tabMatch.setSelected(true);

        mainViewPagerAdapter = new MainViewPagerAdapter(this.getSupportFragmentManager());

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
                        tabMatch.setSelected(true);
                        tabMy.setSelected(false);
                        tabChat.setSelected(false);
                        tabSetting.setSelected(false);
                        break;

                    case 1:
                        tabMatch.setSelected(false);
                        tabSetting.setSelected(false);
                        tabChat.setSelected(false);
                        tabMy.setSelected(true);
                        break;

                    case 2:
                        tabMatch.setSelected(false);
                        tabChat.setSelected(true);
                        tabMy.setSelected(false);
                        tabSetting.setSelected(false);
                        break;

                    case 3:
                        tabMatch.setSelected(false);
                        tabChat.setSelected(false);
                        tabSetting.setSelected(true);
                        tabMy.setSelected(false);
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

    }
}
