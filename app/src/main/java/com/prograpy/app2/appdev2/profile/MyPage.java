package com.prograpy.app2.appdev2.profile;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText area_EditText;
    private Spinner First_main, First_sub, Second_main, Second_sub, Third_main, Third_sub;
    private ImageView profileImage, back;
    private Button edit;
    private String name,gender,area;
    int age;
    String s;

    GetMyInfoTask datarecivetask;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        profileImage = (ImageView)findViewById(R.id.imageView);
        back = (ImageView)findViewById(R.id.back);
        edit = (Button) findViewById(R.id.editBtn);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPage.super.onBackPressed();
            }
        });


        name_TextView = findViewById(R.id.profile_name);
        area_TextView = findViewById(R.id.profile_area);
        age_TextView = findViewById(R.id.profile_age);
        First_main = findViewById(R.id.mypage_firsthobby1);
        First_sub = findViewById(R.id.mypage_firsthobby2);
        Second_main = findViewById(R.id.mypage_second_hobby1);
        Second_sub = findViewById(R.id.mypage_second_hobby2);
        Third_main = findViewById(R.id.mypage_thirdhobby1);
        Third_sub = findViewById(R.id.mypage_thirdhobby2);
        area_EditText = findViewById(R.id.profile_area_edit);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().equals("수정")){
                    area_TextView.setVisibility(View.GONE);
                    area_EditText.setVisibility(View.VISIBLE);
                    edit.setText("완료");
                }
                if(edit.getText().equals("완료")){
                    s = area_EditText.getText().toString().trim();
                    area_EditText.setText(s);
                    area_EditText.setVisibility(View.GONE);
                    area_TextView.setVisibility(View.VISIBLE);
                    edit.setText("수정");
                    //라디오,스피너도 수정된 내용으로 고정
                    //바뀐내용 서버보내기
                }

            }
        });


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
                        age_TextView.setText("("+result.getUserInfos().get(0).getAge()+")");
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
