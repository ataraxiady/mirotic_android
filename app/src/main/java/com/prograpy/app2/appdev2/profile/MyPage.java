package com.prograpy.app2.appdev2.profile;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

    private TextView name_TextView = null;
    private TextView area_TextView = null;
    private TextView age_TextView = null;
    private TextView first_main = null;
    private TextView first_sub = null;
    private TextView second_main = null;
    private TextView second_sub = null;
    private TextView third_main = null;
    private TextView third_sub = null;
    private ImageView profileImage;
    private Button edit_btn;
    private EditText area_EditText = null;
    private RadioButton man_btn, woman_btn;
    private Spinner spinner_hobby1_edit, spinner_hobby2_edit, spinner_hobby_second1_edit,
            spinner_hobby_second2_edit, spinner_hobby_third1_edit, spinner_hobby_third2_edit;

    private ArrayAdapter<CharSequence> hobby_first_edit, hobby_second_edit, hobby_third_edit;

    private String bh_number_1 = "";
    private String bh_number_2 = "";
    private String bh_number_3 = "";
    private String sh_number_1 = "";
    private String sh_number_2 = "";
    private String sh_number_3 = "";


    GetMyInfoTask datarecivetask;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        final String[] genderdata = {""};
        // 프로필 정보
        profileImage = (ImageView) findViewById(R.id.imageView);
        name_TextView = findViewById(R.id.profile_name);
        area_TextView = findViewById(R.id.profile_area);
        age_TextView = findViewById(R.id.profile_age);
        first_main = findViewById(R.id.mypage_firsthobby1);
        first_sub = findViewById(R.id.mypage_firsthobby2);
        second_main = findViewById(R.id.mypage_second_hobby1);
        second_sub = findViewById(R.id.mypage_second_hobby2);
        third_main = findViewById(R.id.mypage_thirdhobby1);
        third_sub = findViewById(R.id.mypage_thirdhobby2);
        // 수정 정보
        edit_btn = findViewById(R.id.edit);
        area_EditText = findViewById(R.id.profile_area_edit);
        man_btn = (RadioButton) findViewById(R.id.man);
        woman_btn = (RadioButton) findViewById(R.id.woman);

        spinner_hobby1_edit = (Spinner) findViewById(R.id.hobby_first_edit);
        spinner_hobby2_edit = (Spinner) findViewById(R.id.hobby_first_sub_edit);
        hobby_first_edit = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby1_edit.setAdapter(hobby_first_edit);
        spinner_hobby1_edit.setOnItemSelectedListener(editspinnerSelectListener);

        spinner_hobby_second1_edit = (Spinner) findViewById(R.id.hobby_second_edit);
        spinner_hobby_second2_edit = (Spinner) findViewById(R.id.hobby_second_sub_edit);
        hobby_second_edit = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby_second1_edit.setAdapter(hobby_second_edit);
        spinner_hobby_second1_edit.setOnItemSelectedListener(editspinnerSelectListener);

        spinner_hobby_third1_edit = (Spinner) findViewById(R.id.hobby_third_edit);
        spinner_hobby_third2_edit = (Spinner) findViewById(R.id.hobby_third_sub_edit);
        hobby_third_edit = ArrayAdapter.createFromResource(this, R.array.spinner_hobby, R.layout.support_simple_spinner_dropdown_item);
        spinner_hobby_third1_edit.setAdapter(hobby_third_edit);
        spinner_hobby_third1_edit.setOnItemSelectedListener(editspinnerSelectListener);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_btn.getText().equals("수정")) {
                    area_TextView.setVisibility(View.GONE);
                    area_EditText.setVisibility(View.VISIBLE);
                    first_main.setVisibility(View.GONE);
                    spinner_hobby1_edit.setVisibility(View.VISIBLE);
                    first_sub.setVisibility(View.GONE);
                    spinner_hobby2_edit.setVisibility(View.VISIBLE);
                    second_main.setVisibility(View.GONE);
                    spinner_hobby_second1_edit.setVisibility(View.VISIBLE);
                    second_sub.setVisibility(View.GONE);
                    spinner_hobby_second2_edit.setVisibility(View.VISIBLE);
                    third_main.setVisibility(View.GONE);
                    spinner_hobby_third1_edit.setVisibility(View.VISIBLE);
                    third_sub.setVisibility(View.GONE);
                    spinner_hobby_third2_edit.setVisibility(View.VISIBLE);
                    edit_btn.setText("완료");
                } else if (edit_btn.getText().equals("완료")) {
                    Toast.makeText(MyPage.this, bh_number_2, Toast.LENGTH_SHORT).show();
                    area_TextView.setText(area_EditText.getText().toString().trim());
                    area_EditText.setVisibility(View.GONE);
                    area_TextView.setVisibility(View.VISIBLE);
                    first_main.setVisibility(View.VISIBLE);
                    spinner_hobby1_edit.setVisibility(View.GONE);
                    first_sub.setVisibility(View.VISIBLE);
                    spinner_hobby2_edit.setVisibility(View.GONE);
                    second_main.setVisibility(View.VISIBLE);
                    spinner_hobby_second1_edit.setVisibility(View.GONE);
                    second_sub.setVisibility(View.VISIBLE);
                    spinner_hobby_second2_edit.setVisibility(View.GONE);
                    third_main.setVisibility(View.VISIBLE);
                    spinner_hobby_third1_edit.setVisibility(View.GONE);
                    third_sub.setVisibility(View.VISIBLE);
                    spinner_hobby_third2_edit.setVisibility(View.GONE);
                    edit_btn.setText("수정");
                    //라디오,스피너도 수정된 내용으로 고정
                    //바뀐내용 서버보내기
                    return;
                }

            }
    });


    /**
     *   서버로부터 데이터를 입력받음
     */
    datarecivetask =new

    GetMyInfoTask(new GetMyInfoTask.TaskResultHandler() {
        @Override
        public void onSuccesTask (MyInfoResult result){

            if (result.isSuccess()) {

                if (result.getUserInfos() != null && result.getUserInfos().size() > 0) {


                    Glide.with(MyPage.this).load(result.getUserInfos().get(0).getProfileimage()).into(profileImage);

                    name_TextView.setText(result.getUserInfos().get(0).getName());
                    area_TextView.setText(result.getUserInfos().get(0).getArea());
                    age_TextView.setText("(" + result.getUserInfos().get(0).getAge() + ")");
                    first_main.setText(result.getUserInfos().get(0).getBh_number_1());
                    first_sub.setText(result.getUserInfos().get(0).getSh_number_1());
                    second_sub.setText(result.getUserInfos().get(0).getBh_number_2());
                    second_sub.setText(result.getUserInfos().get(0).getSh_number_2());
                    third_main.setText(result.getUserInfos().get(0).getBh_number_3());
                    third_sub.setText(result.getUserInfos().get(0).getSh_number_3());
                    // 성별 radio
                    genderdata[0] = result.getUserInfos().get(0).getGender();

                    if (genderdata.equals("남자")) {
                        man_btn.isChecked();
                    } else {
                        woman_btn.isChecked();
                    }
                }


            } else {
                Toast.makeText(MyPage.this, result.getError(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailTask () {
            Toast.makeText(MyPage.this, "서버통신 실패", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelTask () {
            Toast.makeText(MyPage.this, "서버통신 취소", Toast.LENGTH_SHORT).show();
        }
    });

        datarecivetask.execute(ApiValue.API_GET_MY_INFO,PreferenceData.getKeyUserId());

}


    // Spinner Listener

    private AdapterView.OnItemSelectedListener editspinnerSelectListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (adapterView.getId()) {

                case R.id.hobby_first_edit:
                    ArrayAdapter<CharSequence> edit_hobby_first_adapter = null;

                    if (hobby_first_edit.getItem(i).equals("대분류")) {
                        edit_hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_empty, android.R.layout.simple_spinner_dropdown_item);

                    } else if (hobby_first_edit.getItem(i).equals("운동")) {
                        bh_number_1 = "0";
                        edit_hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport, android.R.layout.simple_spinner_dropdown_item);

                    } else if (hobby_first_edit.getItem(i).equals("음악")) {
                        bh_number_1 = "1";
                        edit_hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music, android.R.layout.simple_spinner_dropdown_item);

                    } else if (hobby_first_edit.getItem(i).equals("영화")) {
                        bh_number_1 = "2";
                        edit_hobby_first_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie, android.R.layout.simple_spinner_dropdown_item);

                    }

                    if (edit_hobby_first_adapter != null) {
                        spinner_hobby2_edit.setAdapter(edit_hobby_first_adapter);
                        spinner_hobby2_edit.setOnItemSelectedListener(editspinnerSelectListener);
                    }

                    break;

                case R.id.hobby_second_edit:
                    ArrayAdapter<CharSequence> edit_hobby_second_adapter = null;

                    if (hobby_second_edit.getItem(i).equals("대분류")) {
                        edit_hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_empty, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_second_edit.getItem(i).equals("운동")) {
                        bh_number_2 = "0";
                        edit_hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_second_edit.getItem(i).equals("음악")) {
                        bh_number_2 = "1";
                        edit_hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_second_edit.getItem(i).equals("영화")) {
                        bh_number_2 = "2";
                        edit_hobby_second_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie, R.layout.support_simple_spinner_dropdown_item);
                    }

                    if (edit_hobby_second_adapter != null) {
                        spinner_hobby_second2_edit.setAdapter(edit_hobby_second_adapter);
                        spinner_hobby_second2_edit.setOnItemSelectedListener(editspinnerSelectListener);
                    }

                    break;


                case R.id.hobby_third_edit:

                    ArrayAdapter<CharSequence> edit_hobby_third_adapter = null;

                    if (hobby_third_edit.getItem(i).equals("대분류")) {
                        edit_hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_empty, R.layout.support_simple_spinner_dropdown_item);
                    } else if (hobby_third_edit.getItem(i).equals("운동")) {
                        bh_number_3 = "0";
                        edit_hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_sport, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_third_edit.getItem(i).equals("음악")) {
                        bh_number_3 = "1";
                        edit_hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_music, R.layout.support_simple_spinner_dropdown_item);

                    } else if (hobby_third_edit.getItem(i).equals("영화")) {
                        bh_number_3 = "2";
                        edit_hobby_third_adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinner_hobby_movie, R.layout.support_simple_spinner_dropdown_item);

                    }

                    if (edit_hobby_third_adapter != null) {
                        spinner_hobby_third2_edit.setAdapter(edit_hobby_third_adapter);
                        spinner_hobby_third2_edit.setOnItemSelectedListener(editspinnerSelectListener);
                    }

                    break;


                case R.id.hobby_first_sub_edit:
                    sh_number_1 = String.valueOf(i);
                    break;

                case R.id.hobby_second_sub_edit:
                    sh_number_2 = String.valueOf(i);
                    break;

                case R.id.hobby_third_sub_edit:
                    sh_number_3 = String.valueOf(i);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
}
