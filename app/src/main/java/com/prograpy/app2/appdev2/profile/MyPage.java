package com.prograpy.app2.appdev2.profile;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.result.MyInfoResult;
import com.prograpy.app2.appdev2.task.GetMyInfoTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

public class MyPage extends AppCompatActivity {

    private TextView name_TextView, area_TextView, age_TextView;
    private Spinner First_main, First_sub, Second_main, Second_sub, Third_main, Third_sub;
    private ImageView profileImage;
    private String name,gender,area;
    int age;

    GetMyInfoTask datarecivetask;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        profileImage = (ImageView)findViewById(R.id.imageView);


        name_TextView = findViewById(R.id.profile_name);
        area_TextView = findViewById(R.id.profile_area);
        age_TextView = findViewById(R.id.profile_age);
        First_main = findViewById(R.id.mypage_firsthobby1);
        First_sub = findViewById(R.id.mypage_firsthobby2);
        Second_main = findViewById(R.id.mypage_second_hobby1);
        Second_sub = findViewById(R.id.mypage_second_hobby2);
        Third_main = findViewById(R.id.mypage_thirdhobby1);
        Third_sub = findViewById(R.id.mypage_thirdhobby2);


        /**
         *   서버로부터 데이터를 입력받음
         */
        datarecivetask = new GetMyInfoTask(new GetMyInfoTask.TaskResultHandler() {
            @Override
            public void onSuccesTask(MyInfoResult result) {

                if (result.isSuccess()) {

                    if(result.getUserInfos() != null && result.getUserInfos().size() > 0){


                        Glide.with(MyPage.this).load(result.getUserInfos().get(0).getProfileimage()).into(profileImage);

                        name_TextView.setText(result.getUserInfos().get(0).getName()); // <- 이런식으로 구현 / spinner 아직
                        area_TextView.setText(result.getUserInfos().get(0).getArea());
                        age_TextView.setText(result.getUserInfos().get(0).getAge());
//                        First_main.setPrompt(result.getUserInfos().get(0).getBh_number_1()); // spinner 손봐야됨
                    }


                } else {
                    Toast.makeText(MyPage.this, result.getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailTask() {
                Toast.makeText(MyPage.this, "서버통신 실패", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelTask() {
                Toast.makeText(MyPage.this, "서버통신 취소", Toast.LENGTH_SHORT).show();
            }
        });

        datarecivetask.execute(ApiValue.API_GET_MY_INFO, PreferenceData.getKeyUserId());

    }
}
