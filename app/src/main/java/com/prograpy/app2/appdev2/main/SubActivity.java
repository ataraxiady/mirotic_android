package com.prograpy.app2.appdev2.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.chat.ChatActivity;
import com.prograpy.app2.appdev2.chat.InfoFragment;

/**
 * Created by samsung on 2018-03-23.
 */

public class SubActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        tabLayout = (TabLayout) findViewById(R.id.main_tab);
        viewPager = (ViewPager) findViewById(R.id.main_pager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public CharSequence getPageTitle(int position) {
            String title = "";

            switch (position) {
                case 0:
                    title = "내정보";
                    break;
                case 1:
                    title = "메인";
                    break;
                case 2:
                    title = "환경설정";
                    break;
            }
            return title;

        }


        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = InfoFragment.newInstance();
                    break;
                case 1:
                    fragment = InfoFragment.newInstance();
                    break;
                case 2:
                    fragment = InfoFragment.newInstance();
                    break;
            }
            return fragment;

        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
