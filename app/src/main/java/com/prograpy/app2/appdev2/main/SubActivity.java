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
import com.prograpy.app2.appdev2.network.response.ApiValue;

import com.prograpy.app2.appdev2.network.response.FragmentResult;
import com.prograpy.app2.appdev2.network.response.LikeDislikeResult;
import com.prograpy.app2.appdev2.profile.MyPage;
import com.prograpy.app2.appdev2.task.FragmentTask;
import com.prograpy.app2.appdev2.task.LikeDislikeButtonTask;

/**
 * Created by samsung on 2018-03-23.
 */

public class SubActivity extends AppCompatActivity{
    private MainCustomViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FloatingActionButton floatingActionButton,floatingActionButton2,floatingActionButton3;
    private  boolean isOpen = false;
    private Animation fab_open, fab_close,fabRClockwise, fabRanticlockWise;
    private Intent intent;
    ImageView imageButtonUser;
    ImageView imageButtonSettings;
    Button dislikeButton;
    Button likeButton;
    int index=0;
    TextView textView;
    LikeDislikeButtonTask likeDislikeButtonTask;
    FragmentTask fragmentTask;

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
                intent = new Intent(getApplicationContext(), MyPage.class);
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
                if(index < 5 ){
                    likeDislikeButtonTask = new LikeDislikeButtonTask(new LikeDislikeButtonTask.LikeDislikeResultHandler() {
                        @Override
                        public void onSuccesTask(LikeDislikeResult result) {
                            index++;
                            viewPager.setCurrentItem(index);
                            //서버에게 '싫어요'한 사람 전송해야함. 상대방 닉네임 받아오기
                            likeDislikeButtonTask.execute(ApiValue.APT_LIKEDISLIKE,"F","young");
                        }

                        @Override
                        public void onFailTask() {
                            Toast.makeText(SubActivity.this, "서버통신실패", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelTask() {

                        }

                    });
                }else{
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
                if(index < 5 ){
                    likeDislikeButtonTask = new LikeDislikeButtonTask(new LikeDislikeButtonTask.LikeDislikeResultHandler() {
                        @Override
                        public void onSuccesTask(LikeDislikeResult result) {
                            index++;
                            viewPager.setCurrentItem(index);
                            //서버에게 '좋아요'한 사람 전송. '좋아요'를 받은사람에게 알림이 가게 해야함.
                            //상대방 닉네임 받아오기.
                            likeDislikeButtonTask.execute(ApiValue.APT_LIKEDISLIKE,"T","young");
                        }

                            @Override
                            public void onFailTask() {
                                Toast.makeText(SubActivity.this, "서버통신실패", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelTask() {

                            }

                        });
                    }else{
                        Toast.makeText(SubActivity.this,"오늘 소개는 끝났습니다.",Toast.LENGTH_LONG).show();
                        viewPager.setVisibility(View.GONE);
                        textView = (TextView)findViewById(R.id.textView);
                        textView.setVisibility(View.VISIBLE);
                    }

            }
        });

        viewPager = (MainCustomViewPager)findViewById(R.id.main_pager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);


        // 플로팅버튼

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRanticlockWise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton2 = (FloatingActionButton)findViewById(R.id.fab2);
        floatingActionButton3 = (FloatingActionButton)findViewById(R.id.fab3);

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
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
                intent = new Intent(getApplicationContext(),ChatListActivity.class);
                startActivity(intent);
            }
        });

    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        ImageView profileImage;
        String name;
        String gender;
        int age;
        String area;
        Fragment fragment;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {

            fragmentTask = new FragmentTask(new FragmentTask.FragmentResultHandler() {
                @Override
                public void onSuccesTask(FragmentResult result) {
                    if (result.isSuccess()){
                        //string을 imageview로 받아야함.
//                        profileImage = result.getInfoList().get(position).profileImage;
                        name = result.getInfoList().get(position).name;
                        gender = result.getInfoList().get(position).gender;
                        age = result.getInfoList().get(position).age;
                        area = result.getInfoList().get(position).area;

                        //프래그먼트에 정보주기
//                    사용자의 이미지 = 서버ㄱㅏ 준 데이터 (position).사용자의 이미지;
                        fragment = InfoFragment.newInstance(name,gender,age,area);
                    }

                }

                @Override
                public void onFailTask() {
                    //실패했다는 이미지 주기.

                }

                @Override
                public void onCancelTask() {

                }
            });
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

    }
}
