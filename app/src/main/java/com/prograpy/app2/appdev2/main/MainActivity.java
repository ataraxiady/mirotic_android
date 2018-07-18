package com.prograpy.app2.appdev2.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.chatList.ChatListActivity;
import com.prograpy.app2.appdev2.environment_setting.SetActivity;
import com.prograpy.app2.appdev2.network.NetworkProgressDialog;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.result.LikeDislikeResult;
import com.prograpy.app2.appdev2.network.response.data.UserData;
import com.prograpy.app2.appdev2.network.response.result.MainMatchingResult;
import com.prograpy.app2.appdev2.profile.MyPage;
import com.prograpy.app2.appdev2.task.MainMatchingTask;
import com.prograpy.app2.appdev2.task.LikeDislikeButtonTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by samsung on 2018-03-23.
 */

public class MainActivity extends AppCompatActivity {
    private MainCustomViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FloatingActionButton floatingActionButton, floatingActionButton2, floatingActionButton3;
    private boolean isOpen = false;
    private Animation fab_open, fab_close, fabRClockwise, fabRanticlockWise;
    private Intent intent;
    ImageView imageButtonUser;
    ImageView imageButtonSettings;
    Button dislikeButton;
    Button likeButton;
    int index = 0;
    TextView textView;
    LikeDislikeButtonTask likeDislikeButtonTask;
    MainMatchingTask fragmentTask;
    private ArrayList<UserData> userDataList = new ArrayList<UserData>();


    private NetworkProgressDialog dialog;


    private View.OnClickListener likeDisLikeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (index < 5) {
                likeDislikeButtonTask = new LikeDislikeButtonTask(new LikeDislikeButtonTask.LikeDislikeResultHandler() {
                    @Override
                    public void onSuccesTask(LikeDislikeResult result) {
                        dialog.dismiss();

                        if(result.isSuccess()){
                            index++;

                            if(index < userDataList.size()){

                                likeButton.setEnabled(true);
                                dislikeButton.setEnabled(true);

                                viewPager.setCurrentItem(index);
                            }else{
                                Toast.makeText(MainActivity.this, "오늘 소개는 끝났습니다.", Toast.LENGTH_LONG).show();

                                PreferenceData.setKeyTodayIntroduce(true);

                                viewPager.setVisibility(View.GONE);
                                textView = (TextView) findViewById(R.id.textView);
                                textView.setVisibility(View.VISIBLE);

                                likeButton.setEnabled(false);
                                dislikeButton.setEnabled(false);
                            }

                        }

                        //서버에게 '싫어요'한 사람 전송해야함. 상대방 닉네임 받아오기
                    }

                    @Override
                    public void onFailTask() {
                        dialog.dismiss();

                        Toast.makeText(MainActivity.this, "서버통신실패", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelTask() {
                        dialog.dismiss();
                    }

                });

                dialog.show();

                likeDislikeButtonTask.execute(ApiValue.API_LIKEDISLIKE, PreferenceData.getKeyUserId(), userDataList.get(index).getKakaoKey(),
                        v.getId() == R.id.btn_dislike ? "F" : "T");

            } else {
                Toast.makeText(MainActivity.this, "오늘 소개는 끝났습니다.", Toast.LENGTH_LONG).show();
                viewPager.setVisibility(View.GONE);
                textView = (TextView) findViewById(R.id.textView);
                textView.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);


        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        int curTime = Integer.parseInt(format.format(date).toString());

        if(curTime > PreferenceData.getKeyLoginTime()){
            PreferenceData.setKeyTodayIntroduce(false);
        }

        PreferenceData.setKeyLoginTime(curTime);

        dialog = new NetworkProgressDialog(this);

        imageButtonUser = (ImageView) findViewById(R.id.imgbtn_user);
        imageButtonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), MyPage.class);
                startActivity(intent);
            }
        });

        imageButtonSettings = (ImageView) findViewById(R.id.imgbtn_settings);
        imageButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), SetActivity.class);
                startActivity(intent);
            }
        });

        dislikeButton = (Button) findViewById(R.id.btn_dislike);
        dislikeButton.setOnClickListener(likeDisLikeListener);

        likeButton = (Button) findViewById(R.id.btn_like);
        likeButton.setOnClickListener(likeDisLikeListener);

        viewPager = (MainCustomViewPager) findViewById(R.id.main_pager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);


        // 플로팅버튼

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRanticlockWise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.fab2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.fab3);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isOpen) {
                    floatingActionButton2.startAnimation(fab_close);
                    floatingActionButton3.startAnimation(fab_close);
                    floatingActionButton.startAnimation(fabRanticlockWise);
                    floatingActionButton2.setClickable(false);
                    floatingActionButton3.setClickable(false);
                    isOpen = false;
                } else {
                    floatingActionButton2.startAnimation(fab_open);
                    floatingActionButton3.startAnimation(fab_open);
                    floatingActionButton.startAnimation(fabRClockwise);
                    floatingActionButton2.setClickable(true);
                    floatingActionButton3.setClickable(true);
                    isOpen = true;
                }
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), ChatListActivity.class);
                startActivity(intent);
            }
        });


        if(!PreferenceData.getKeyTodayIntroduce()){

            fragmentTask = new MainMatchingTask(new MainMatchingTask.TaskResultHandler() {
                @Override
                public void onSuccesTask(MainMatchingResult result) {
                    dialog.dismiss();

                    if (result.isSuccess()) {

                        if (result.getUserInfos() != null && result.getUserInfos().size() > 0) {
                            likeButton.setEnabled(true);
                            dislikeButton.setEnabled(true);

                            userDataList = result.getUserInfos();

                        }else{
                            index = 6;
                            viewPager.setVisibility(View.GONE);
                            textView = (TextView) findViewById(R.id.textView);
                            textView.setVisibility(View.VISIBLE);

                            likeButton.setEnabled(false);
                            dislikeButton.setEnabled(false);
                        }

                    }else{
                        index = 6;
                        viewPager.setVisibility(View.GONE);
                        textView = (TextView) findViewById(R.id.textView);
                        textView.setVisibility(View.VISIBLE);

                        likeButton.setEnabled(false);
                        dislikeButton.setEnabled(false);
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailTask() {
                    dialog.dismiss();

                }

                @Override
                public void onCancelTask() {
                    dialog.dismiss();

                }
            });

            dialog.show();
            fragmentTask.execute(ApiValue.API_GET_MATCHING_INFO, PreferenceData.getKeyUserId());
        }else{

            viewPager.setVisibility(View.GONE);
            textView = (TextView) findViewById(R.id.textView);
            textView.setVisibility(View.VISIBLE);

            likeButton.setEnabled(false);
            dislikeButton.setEnabled(false);
        }

    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        String profileImage = "";
        String kakaoKey = "";
        String name = "";
        String gender = "";
        int age = 0;
        String area = "";
        Fragment fragment;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
            //string을 imageview로 받아야함.
//                        profileImage = result.getInfoList().get(position).profileImage;

            if (userDataList != null && userDataList.size() > 0) {
                name = userDataList.get(position).getName();
                gender = userDataList.get(position).getGender();
                age = userDataList.get(position).getAge();
                area = userDataList.get(position).getArea();
                profileImage = userDataList.get(position).getProfileimage();
                kakaoKey = userDataList.get(position).getKakaoKey();
            }

            //프래그먼트에 정보주기
//                    사용자의 이미지 = 서버ㄱㅏ 준 데이터 (position).사용자의 이미지;

            fragment = InfoFragment.newInstance(name, gender, age, area, profileImage, kakaoKey);
            return fragment;
        }

        @Override
        public int getCount() {
            return userDataList.size();
        }

    }
}
