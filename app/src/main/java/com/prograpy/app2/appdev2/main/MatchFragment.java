package com.prograpy.app2.appdev2.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.network.NetworkProgressDialog;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.data.UserData;
import com.prograpy.app2.appdev2.network.response.result.LikeDislikeResult;
import com.prograpy.app2.appdev2.network.response.result.MainMatchingResult;
import com.prograpy.app2.appdev2.task.LikeDislikeButtonTask;
import com.prograpy.app2.appdev2.task.MainMatchingTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by samsung on 2018-03-23.
 */

public class MatchFragment extends Fragment {
    LinearLayout end;
    private MatchCustomViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FloatingActionButton floatingActionButton, floatingActionButton2, floatingActionButton3;
    private boolean isFabOpen = false;
    private Animation fab_open, fab_close, fabRClockwise, fabRanticlockWise;
    private Intent intent,intent2;
    ImageView dislikeButton;
    ImageView likeButton;
    int index = 0;
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
                                Toast.makeText(getContext(), "오늘 소개는 끝났습니다.", Toast.LENGTH_LONG).show();

                                PreferenceData.setKeyTodayIntroduce(true);

                                viewPager.setVisibility(View.GONE);
                                end.setVisibility(View.VISIBLE);

                                likeButton.setEnabled(false);
                                dislikeButton.setEnabled(false);
                            }

                        }
                    }

                    @Override
                    public void onFailTask() {
                        dialog.dismiss();

                        Toast.makeText(getContext(), "서버통신실패", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelTask() {
                        dialog.dismiss();
                    }

                });

                dialog.show();


                likeDislikeButtonTask.execute(ApiValue.API_LIKEDISLIKE, PreferenceData.getKeyUserId(), userDataList.get(index).getID(),
                        v.getId() == R.id.btn_dislike ? "F" : "T");

            } else {
                Toast.makeText(getContext(), "오늘 소개는 끝났습니다.", Toast.LENGTH_LONG).show();
                viewPager.setVisibility(View.GONE);
                end.setVisibility(View.VISIBLE);
            }
        }
    };


    public static MatchFragment createFragment(){

        Bundle bundle = new Bundle();

        MatchFragment fragment = new MatchFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_match, container, false);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        int curTime = Integer.parseInt(format.format(date).toString());

        if(curTime > PreferenceData.getKeyLoginTime()){
            PreferenceData.setKeyTodayIntroduce(false);
        }

        PreferenceData.setKeyLoginTime(curTime);

        dialog = new NetworkProgressDialog(getContext());

        end = (LinearLayout)view.findViewById(R.id.end);

        dislikeButton = (ImageView) view.findViewById(R.id.btn_dislike);
        dislikeButton.setOnClickListener(likeDisLikeListener);

        likeButton = (ImageView) view.findViewById(R.id.btn_like);
        likeButton.setOnClickListener(likeDisLikeListener);

        viewPager = (MatchCustomViewPager) view.findViewById(R.id.main_pager);

        adapter = new ViewPagerAdapter(getFragmentManager());

        viewPager.setAdapter(adapter);


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
                            end.setVisibility(View.VISIBLE);

                            likeButton.setEnabled(false);
                            dislikeButton.setEnabled(false);
                        }

                    }else{
                        index = 6;
                        viewPager.setVisibility(View.GONE);
                        end.setVisibility(View.VISIBLE);

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
            fragmentTask.execute(ApiValue.API_GET_MATCHING_INFO, PreferenceData.getKeyUserId(), PreferenceData.getKeyUserGender());
        }else{

            viewPager.setVisibility(View.GONE);
            end.setVisibility(View.VISIBLE);

            likeButton.setEnabled(false);
            dislikeButton.setEnabled(false);
        }

        return view;
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        String profileImage = "";
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

            if (userDataList != null && userDataList.size() > 0) {
                name = userDataList.get(position).getName();
                gender = userDataList.get(position).getGender();
                age = userDataList.get(position).getAge();
                area = userDataList.get(position).getArea();
                profileImage = userDataList.get(position).getProfileimage();
            }

            fragment = InfoFragment.newInstance(name, gender, age, area, profileImage);
            return fragment;
        }

        @Override
        public int getCount() {
            return userDataList.size();
        }

    }
}
