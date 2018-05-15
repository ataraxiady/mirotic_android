package com.prograpy.app2.appdev2.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.chat.ChatActivity;
import com.prograpy.app2.appdev2.profile.ProfileActivity;
import com.prograpy.app2.appdev2.environment_setting.SetActivity;

/**
 * Created by samsung on 2018-03-23.
 */

public class SubActivity extends AppCompatActivity{
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private Intent intent;
    ImageView imageButtonUser;
    ImageView imageButtonSettings;
    Button dislikeButton;
    Button likeButton;
    int index=0;
    TextView textView;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(SubActivity.this, "헹", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);


        imageButtonUser = (ImageView)findViewById(R.id.imgbtn_user);
        imageButtonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        imageButtonSettings = (ImageView)findViewById(R.id.imgbtn_settings);
        imageButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SetActivity.class);
                startActivity(intent);
            }
        });

        dislikeButton = (Button) findViewById(R.id.btn_dislike);
        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;

                if (adapter.getCount() > index){
                    viewPager.setCurrentItem(index);}
                else {
                   Toast.makeText(SubActivity.this,"오늘 소개는 끝났습니다.",Toast.LENGTH_LONG).show();
                   viewPager.setVisibility(View.GONE);
                   textView = (TextView)findViewById(R.id.textView);
                   textView.setVisibility(View.VISIBLE);
                }
            }
        });
        likeButton = (Button) findViewById(R.id.btn_like);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;

                if (adapter.getCount() > index){
                    viewPager.setCurrentItem(index);}
                    //서버연동 시 추가
                else {
                    Toast.makeText(SubActivity.this,"오늘 소개는 끝났습니다.",Toast.LENGTH_LONG).show();
                    viewPager.setVisibility(View.GONE);
                    textView = (TextView)findViewById(R.id.textView);
                    textView.setVisibility(View.VISIBLE);
                }

            }
        });

        viewPager = (ViewPager)findViewById(R.id.main_pager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

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

        @Override
        public Fragment getItem(int position) {
//            사용자의 이미지 = 서버ㄱㅏ 준 데이터 (position).사용자의 이미지;
//            Fragment fragment = InfoFragment.newInstance(사용자의 임지ㅣ);

            Fragment fragment = InfoFragment.newInstance();


            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }
}
