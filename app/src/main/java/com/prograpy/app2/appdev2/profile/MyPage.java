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

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.network.response.result.DataReceiveResult;
import com.prograpy.app2.appdev2.task.DataReceiveTask;

public class MyPage extends AppCompatActivity {

    private TextView name_TextView, area_TextView, age_TextView;
    private Spinner First_main, First_sub, Second_main, Second_sub, Third_main, Third_sub;
    private ImageView profileImage;
    private String name,gender,area;
    private String myld = "";
    private String path = "";
    int age;

    DataReceiveTask datarecivetask;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);

        /**
         *   프로필 이미지 원형모양으로 변경
         */
        imageView.setBackground(new ShapeDrawable(new OvalShape()));
        if(Build.VERSION.SDK_INT >= 21){
            imageView.setClipToOutline(true);
        }

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
        datarecivetask = new DataReceiveTask(new DataReceiveTask.DataReceiveTaskHandler() {
            @Override
            public void onSuccesTask(DataReceiveResult result) {

                if (result != null) {
                    if (result.isSuccess()) {
                        name_TextView.setText(result.getName()); // <- 이런식으로 구현 / spinner 아직
                        area_TextView.setText(result.getArea());
                        age_TextView.setText(result.getAge());
                        First_main.setPrompt(result.getFirst_main()); // spinner 손봐야됨




                    } else {
                        Toast.makeText(MyPage.this, result.getError(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MyPage.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
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
        datarecivetask.execute(myld,path,"내 아이디", "경로");

    }
}
