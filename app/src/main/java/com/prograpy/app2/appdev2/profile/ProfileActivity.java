package com.prograpy.app2.appdev2.profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.main.SubActivity;
import com.prograpy.app2.appdev2.network.NetworkProgressDialog;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.JoinResult;
import com.prograpy.app2.appdev2.task.JoinTask;

public class ProfileActivity extends AppCompatActivity {
    // ㅁ 조건
    // 1. 닉네임되야한다
    // 2. 중복체크가 확인
    // 3. 성별이 빈값이 아닐때


    private Button join;                    // 회원가입 버튼
    private Button nickname;                // 중복확인
    private EditText editText;              // 닉네임 입력 칸
    private RadioGroup genderradio;          // 성별 남자 radiobtn
    private RadioButton man_btn, woman_btn; // 성별 여자 radiobtn
    private String gender = "";
    private String nick = "";
    private String area = "";
    boolean namechecked = false;

    private String bh_number_1 = "";
    private String bh_number_2 = "";
    private String bh_number_3 = "";
    private String sh_number_1 = "";
    private String sh_number_2 = "";
    private String sh_number_3 = "";

    private NetworkProgressDialog networkProgressDialog;

    private ArrayAdapter<CharSequence> from_main, hobby_first, hobby_second, hobby_third;
    private Spinner spinner_from, spinner_sub, spinner_sub2, spinner_hobby1, spinner_hobby2, spinner_hobby_second1, spinner_hobby_second2,
            spinner_hobby_third1, spinner_hobby_third2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Button ImageAdd = (Button)findViewById(R.id.imageAdd);
        ImageAdd.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivity(intent);
            }
        });

        networkProgressDialog = new NetworkProgressDialog(this);

        // 성별 선택 radio 버튼
        man_btn = (RadioButton) findViewById(R.id.man);
        woman_btn = (RadioButton) findViewById(R.id.woman);
        genderradio = (RadioGroup) findViewById(R.id.gender);

        man_btn.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(man_btn.isChecked()){
                    gender = "남자";
                }
            }
        });
        woman_btn.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(woman_btn.isChecked()){
                    gender = "여자";
                }
            }
        });


        join = (Button) findViewById(R.id.join);

        nickname = (Button) findViewById(R.id.nickname_btn);
        editText = (EditText) findViewById(R.id.nameText);
        // 닉네임 중복화인 리스너
        nickname.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nick = editText.getText().toString();

                if (nick.equals("")) {
                    Toast.makeText(ProfileActivity.this, "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                    namechecked = false;
                } else {
                    Toast.makeText(ProfileActivity.this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                    namechecked = true;
                    // 중복확인이 됬는지 안됬는지 확인
                }
            }
        });

        /*
           회원가입 버튼으로 인한 메인화면으로의 화면전환
           중복환인 및 성별 체크 여부 확인
        */
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nick = editText.getText().toString();

                // String은 == 으로 비교하지말고 .equals 로 비교할것
                if(gender.equals("")) {
                    Toast.makeText(ProfileActivity.this, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(!namechecked){
                    Toast.makeText(ProfileActivity.this, "중복확인을 하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(nick.equals("")){
                    Toast.makeText(ProfileActivity.this, "닉네임을 입력 하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(bh_number_1.equals("") || bh_number_2.equals("") || bh_number_3.equals("") ||
                        sh_number_1.equals("") || sh_number_2.equals("") || sh_number_3.equals("") ){

                    Toast.makeText(ProfileActivity.this, "취미를 모두 선택 해주세요.", Toast.LENGTH_SHORT).show();
                    return;

                }

                networkProgressDialog.show();

                // 서버로 요청할 task를 만들어준다.
                // 생성할때 해당 task 생성자에 task에서 선언해준 인터페이스를 구현해줘야 하므로 해당 인터페이스의 내용도
                // 마찬가지로 선언해준다.
                JoinTask joinTask = new JoinTask(new JoinTask.JoinTaskResultHandler() {

                    @Override
                    public void onSuccessTask(JoinResult result) {

                        networkProgressDialog.dismiss();

                        if (result != null) {

                            // 서버에서 파싱한 데이터중 성공여부에 대한 데이터가 성공일때만 화면이동
                            if (result.isSuccess()) {
                                Intent i = new Intent(ProfileActivity.this, SubActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(ProfileActivity.this, result.getError(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(ProfileActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailTask() {
                        networkProgressDialog.dismiss();

                        Toast.makeText(ProfileActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelTask() {
                        networkProgressDialog.dismiss();

                        Toast.makeText(ProfileActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                    }
                });

                // execute 함수를 호출하는 순간 task의 내용들이 실행된다
                // execute 함수 안에 넘겨주는 파라미터 값들은 doinBackground에서 strings.... 에 들어가는 내용들
                joinTask.execute(ApiValue.API_JOIN, nick, gender, "0", "주소지", "이미지",
                        bh_number_1, bh_number_2, bh_number_3, sh_number_1, sh_number_2, sh_number_3, "kakao");

            }
        });


        spinner_from = (Spinner)findViewById(R.id.from_main);
        spinner_sub = (Spinner)findViewById(R.id.from_sub);
        spinner_sub2 = (Spinner)findViewById(R.id.from_sub2);
        from_main = ArrayAdapter.createFromResource(this, R.array.spinner_from, R.layout.support_simple_spinner_dropdown_item);
        spinner_from.setAdapter(from_main);
        spinner_from.setOnItemSelectedListener(spinnerSelectListener);

        spinner_hobby1 = (Spinner)findViewById(R.id.hobby_first);
        spinner_hobby2 = (Spinner)findViewById(R.id.hobby_first_sub);
        hobby_first = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby1.setAdapter(hobby_first);
        spinner_hobby1.setOnItemSelectedListener(spinnerSelectListener);

        spinner_hobby_second1 = (Spinner)findViewById(R.id.hobby_second);
        spinner_hobby_second2 = (Spinner)findViewById(R.id.hobby_second_sub);
        hobby_second = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby_second1.setAdapter(hobby_second);
        spinner_hobby_second1.setOnItemSelectedListener(spinnerSelectListener);

        // 나중에 수정 * hobby_third => hobby_first로 통일시키기
        spinner_hobby_third1 = (Spinner)findViewById(R.id.hobby_third);
        spinner_hobby_third2 = (Spinner)findViewById(R.id.hobby_third_sub);
        hobby_third = ArrayAdapter.createFromResource(this, R.array.spinner_hobby,R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby_third1.setAdapter(hobby_third);
        spinner_hobby_third1.setOnItemSelectedListener(spinnerSelectListener);
    }




    private AdapterView.OnItemSelectedListener spinnerSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            switch (adapterView.getId()){

                case R.id.from_main:

                    ArrayAdapter<CharSequence> from_sub = null;
                    ArrayAdapter<CharSequence> from_sub2 = null;

                    if(from_main.getItem(i).equals("서울특별시")) {
                        from_sub = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_seoul,android.R.layout.simple_spinner_dropdown_item);
                        from_sub2 = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_seoul_sub,android.R.layout.simple_spinner_dropdown_item);

                    }else if(from_main.getItem(i).equals("충청북도")) {
                        from_sub = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_ChungBuck,android.R.layout.simple_spinner_dropdown_item);
                        from_sub2 = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_cheongu,android.R.layout.simple_spinner_dropdown_item);
                    }
                    if(from_sub != null)
                        spinner_sub.setAdapter(from_sub);

                    if(from_sub2 != null)
                        spinner_sub2.setAdapter(from_sub2);

                    break;


                case R.id.hobby_first:
                    ArrayAdapter<CharSequence> hobby_first_adapter = null;

                    if(hobby_first.getItem(i).equals("대분류")){
                        hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_empty,android.R.layout.simple_spinner_dropdown_item);

                    } else if(hobby_first.getItem(i).equals("운동")){
                        bh_number_1 = "0";
                        hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_sport,android.R.layout.simple_spinner_dropdown_item);

                    }else if(hobby_first.getItem(i).equals("음악")) {
                        bh_number_1 = "1";
                        hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_music,android.R.layout.simple_spinner_dropdown_item);

                    }else if(hobby_first.getItem(i).equals("영화")) {
                        bh_number_1 = "2";
                        hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_movie,android.R.layout.simple_spinner_dropdown_item);

                    }

                    if(hobby_first_adapter != null){
                        spinner_hobby2.setAdapter(hobby_first_adapter);
                        spinner_hobby2.setOnItemSelectedListener(spinnerSelectListener);
                    }

                    break;

                case R.id.hobby_second:
                    ArrayAdapter<CharSequence> hobby_second_adapter = null;

                    if(hobby_second.getItem(i).equals("대분류")){
                        hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_empty,R.layout.support_simple_spinner_dropdown_item);

                    }else if(hobby_second.getItem(i).equals("운동")){
                        bh_number_2 = "0";
                        hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport,R.layout.support_simple_spinner_dropdown_item);

                    }else if(hobby_second.getItem(i).equals("음악")){
                        bh_number_2 = "1";
                        hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music,R.layout.support_simple_spinner_dropdown_item);

                    }else if(hobby_second.getItem(i).equals("영화")){
                        bh_number_2 = "2";
                        hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie,R.layout.support_simple_spinner_dropdown_item);
                    }

                    if(hobby_second_adapter != null){
                        spinner_hobby_second2.setAdapter(hobby_second_adapter);
                        spinner_hobby_second2.setOnItemSelectedListener(spinnerSelectListener);
                    }

                    break;


                case R.id.hobby_third:

                    ArrayAdapter<CharSequence> hobby_third_adapter = null;

                    if(hobby_third.getItem(i).equals("대분류")){
                        hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_empty, R.layout.support_simple_spinner_dropdown_item);

                    }else if(hobby_third.getItem(i).equals("운동")){
                        bh_number_3 = "0";
                        hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport, R.layout.support_simple_spinner_dropdown_item);

                    }else if(hobby_third.getItem(i).equals("음악")){
                        bh_number_3 = "1";
                        hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music, R.layout.support_simple_spinner_dropdown_item);

                    }else if(hobby_third.getItem(i).equals("영화")){
                        bh_number_3 = "2";
                        hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie, R.layout.support_simple_spinner_dropdown_item);

                    }

                    if(hobby_third_adapter != null){
                        spinner_hobby_third2.setAdapter(hobby_third_adapter);
                        spinner_hobby_third2.setOnItemSelectedListener(spinnerSelectListener);
                    }

                    break;



                case R.id.hobby_first_sub:
                    sh_number_1 = String.valueOf(i);
                    break;

                case R.id.hobby_second_sub:
                    sh_number_2 = String.valueOf(i);
                    break;

                case R.id.hobby_third_sub:
                    sh_number_3 = String.valueOf(i);
                    break;
            }

        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


}

