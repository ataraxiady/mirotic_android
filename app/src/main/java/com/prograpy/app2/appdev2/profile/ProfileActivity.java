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

public class ProfileActivity extends AppCompatActivity {
    // ㅁ 조건
    // 1. 닉네임되야한다
    // 2. 중복체크가 확인
    // 3. 성별이 빈값이 아닐때


    private Button join;                    // 회원가입 버튼
    private Button nickname;                // 중복확인
    private EditText editText;              // 닉네임 입력 칸
    private RadioGroup genderradio;         // 성별 남자 radiobtn
    private RadioButton man_btn, woman_btn; // 성별 여자 radiobtn
    String Gender = "";
    String nick = "";
    boolean namechecked = false;

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

        // 성별 선택 radio 버튼
        man_btn = (RadioButton) findViewById(R.id.man);
        woman_btn = (RadioButton) findViewById(R.id.woman);

        man_btn.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(man_btn.isChecked()){
                    Gender = "남자";
                }
            }
        });
        woman_btn.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(woman_btn.isChecked()){
                    Gender = "여자";
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
                } else {
                    Toast.makeText(ProfileActivity.this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
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
                if (!nick.equals("")) {
                    namechecked = true;    // 중복확인이 됬는지 안됬는지 확인
                }
                if(!Gender.equals("") && !nick.equals("") && namechecked == true ) {
                    Intent i = new Intent(ProfileActivity.this, SubActivity.class);
                    startActivity(i);
                }else if(Gender.equals("") || namechecked == false || nick.equals("")){
                    if(Gender == "") {
                        Toast.makeText(ProfileActivity.this, "성별을 선택하세요.", Toast.LENGTH_SHORT).show();
                    }else if(namechecked == false){
                        Toast.makeText(ProfileActivity.this, "중복확인을 하세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ProfileActivity.this, "닉네임을 입력 하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                namechecked = false;
            }
        });

        
        // 사는지역 spinner
        final ArrayAdapter<CharSequence> from_main;

        final Spinner spinner_from = (Spinner)findViewById(R.id.from_main);
        final Spinner spinner_sub = (Spinner)findViewById(R.id.from_sub);
        final Spinner spinner_sub2 = (Spinner)findViewById(R.id.from_sub2);
        from_main = ArrayAdapter.createFromResource(this, R.array.spinner_from, R.layout.support_simple_spinner_dropdown_item);
        spinner_from.setAdapter(from_main);

        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(from_main.getItem(i).equals("서울특별시"))
                {
                    ArrayAdapter<CharSequence> from_sub = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_seoul,android.R.layout.simple_spinner_dropdown_item);
                    spinner_sub.setAdapter(from_sub);
                    ArrayAdapter<CharSequence> from_sub2 = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_seoul_sub,android.R.layout.simple_spinner_dropdown_item);
                    spinner_sub2.setAdapter(from_sub2);

                }else if(from_main.getItem(i).equals("충청북도"))
                {
                    ArrayAdapter<CharSequence> from_sub = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_ChungBuck,android.R.layout.simple_spinner_dropdown_item);
                    spinner_sub.setAdapter(from_sub);
                    ArrayAdapter<CharSequence> from_sub2 = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_cheongu,android.R.layout.simple_spinner_dropdown_item);
                    spinner_sub2.setAdapter(from_sub2);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // 취미선택 1순위 spinner
        final ArrayAdapter<CharSequence> hobby_first;
        final Spinner spinner_hobby1 = (Spinner)findViewById(R.id.hobby_first);
        final Spinner spinner_hobby2 = (Spinner)findViewById(R.id.hobby_first_sub);
        hobby_first = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby1.setAdapter(hobby_first);
        spinner_hobby1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(hobby_first.getItem(i).equals("선택하시오")){
                    ArrayAdapter<CharSequence> hobby_empty = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_empty,android.R.layout.simple_spinner_dropdown_item);
                    spinner_hobby2.setAdapter(hobby_empty);
                }
                else if(hobby_first.getItem(i).equals("운동")){
                    ArrayAdapter<CharSequence> hobby_sport = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_sport,android.R.layout.simple_spinner_dropdown_item);
                    spinner_hobby2.setAdapter(hobby_sport);
                }else if(hobby_first.getItem(i).equals("음악"))
                {
                    ArrayAdapter<CharSequence> hobby_music = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_music,android.R.layout.simple_spinner_dropdown_item);
                    spinner_hobby2.setAdapter(hobby_music);
                }else if(hobby_first.getItem(i).equals("영화"))
                {
                    ArrayAdapter<CharSequence> hobby_movie = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_movie,android.R.layout.simple_spinner_dropdown_item);
                    spinner_hobby2.setAdapter(hobby_movie);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // 취미선택 2순위 spinner
        final ArrayAdapter<CharSequence> hobby_second;
        final Spinner spinner_hobby_second1 = (Spinner)findViewById(R.id.hobby_second);
        final Spinner spinner_hobby_second2 = (Spinner)findViewById(R.id.hobby_second_sub);
        hobby_second = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby_second1.setAdapter(hobby_second);
        spinner_hobby_second1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(hobby_second.getItem(i).equals("선택하시오")){
                    ArrayAdapter<CharSequence> hobby_empty_second = ArrayAdapter.createFromResource(view.getContext(),R.array.spinner_hobby_empty,R.layout.support_simple_spinner_dropdown_item);
                    spinner_hobby_second2.setAdapter(hobby_empty_second);
                }else if(hobby_second.getItem(i).equals("운동")){
                    ArrayAdapter<CharSequence> hobby_sport_second = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport,R.layout.support_simple_spinner_dropdown_item);
                    spinner_hobby_second2.setAdapter(hobby_sport_second);
                }else if(hobby_second.getItem(i).equals("음악")){
                    ArrayAdapter<CharSequence> hobby_music_second = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music,R.layout.support_simple_spinner_dropdown_item);
                    spinner_hobby_second2.setAdapter(hobby_music_second);
                }else if(hobby_second.getItem(i).equals("영화")){
                    ArrayAdapter<CharSequence> hobby_movie_second = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie,R.layout.support_simple_spinner_dropdown_item);
                    spinner_hobby_second2.setAdapter(hobby_movie_second);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // 나중에 수정 * hobby_third => hobby_first로 통일시키기
        // 취미선택 3순위 spinner
        final  ArrayAdapter<CharSequence> hobby_third;
        final Spinner spinner_hobby_third1 = (Spinner)findViewById(R.id.hobby_third);
        final Spinner spinner_hobby_third2 = (Spinner)findViewById(R.id.hobby_third_sub);
        hobby_third = ArrayAdapter.createFromResource(this, R.array.spinner_hobby,R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby_third1.setAdapter(hobby_third);
        spinner_hobby_third1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(hobby_third.getItem(i).equals("선택하시오")){
                    ArrayAdapter<CharSequence> hobby_empty_third = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_empty, R.layout.support_simple_spinner_dropdown_item);
                    spinner_hobby_third2.setAdapter(hobby_empty_third);
                }else if(hobby_third.getItem(i).equals("운동")){
                    ArrayAdapter<CharSequence> hobby_sport_third = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport, R.layout.support_simple_spinner_dropdown_item);
                    spinner_hobby_third2.setAdapter(hobby_sport_third);
                }else if(hobby_third.getItem(i).equals("음악")){
                    ArrayAdapter<CharSequence> hobby_music_third = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music, R.layout.support_simple_spinner_dropdown_item);
                    spinner_hobby_third2.setAdapter(hobby_music_third);
                }else if(hobby_third.getItem(i).equals("영화")){
                    ArrayAdapter<CharSequence> hobby_movie_third = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie, R.layout.support_simple_spinner_dropdown_item);
                    spinner_hobby_third2.setAdapter(hobby_movie_third);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

