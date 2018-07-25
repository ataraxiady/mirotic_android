package com.prograpy.app2.appdev2.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.prograpy.app2.appdev2.network.response.data.HobbyData;
import com.prograpy.app2.appdev2.network.response.data.UserData;
import com.prograpy.app2.appdev2.network.response.result.ModifyResult;
import com.prograpy.app2.appdev2.network.response.result.MyInfoResult;
import com.prograpy.app2.appdev2.task.GetMyInfoTask;
import com.prograpy.app2.appdev2.task.ModifyinformationTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.util.ArrayList;

public class MyPageFragment extends Fragment {

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
    private Button edit_btn, save_btn;
    private EditText area_EditText = null;
    private RadioButton man_btn, woman_btn;
    private Spinner spinner_hobby1_edit, spinner_hobby2_edit, spinner_hobby_second1_edit,
            spinner_hobby_second2_edit, spinner_hobby_third1_edit, spinner_hobby_third2_edit;

    private ArrayAdapter hobby_first_edit, hobby_second_edit, hobby_third_edit;

    //  서버로 보내 줄 키 값 변수
    private String area = "";
    private String bh_number_1 = "";
    private String bh_number_2 = "";
    private String bh_number_3 = "";
    private String sh_number_1 = "";
    private String sh_number_2 = "";
    private String sh_number_3 = "";

    GetMyInfoTask datarecivetask;

    private ArrayList<HobbyData> bigHobbyList = new ArrayList<HobbyData>();
    private ArrayList<String> bigHobbyNameList = new ArrayList<String>();

    private ArrayList<HobbyData> smallHobbyList = new ArrayList<HobbyData>();
    private ArrayList<String> smallHobbyNameList = new ArrayList<String>();


    public static MyPageFragment createFragment(ArrayList<HobbyData> bigHobbyList, ArrayList<HobbyData> smallHobbyList) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("bigHobbyList", bigHobbyList);
        bundle.putParcelableArrayList("smallHobbyList", smallHobbyList);

        MyPageFragment fragment = new MyPageFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);


        bigHobbyList = getArguments().getParcelableArrayList("bigHobbyList");
        smallHobbyList = getArguments().getParcelableArrayList("smallHobbyList");


        if(bigHobbyList.size() > 0){

            for (HobbyData data : bigHobbyList){
                bigHobbyNameList.add(data.getHobby_name());
            }
        }


        // 프로필 정보
        profileImage = (ImageView) view.findViewById(R.id.imageView);
        name_TextView = view.findViewById(R.id.profile_name);
        area_TextView = view.findViewById(R.id.profile_area);
        age_TextView = view.findViewById(R.id.profile_age);
        first_main = view.findViewById(R.id.mypage_firsthobby1);
        first_sub = view.findViewById(R.id.mypage_firsthobby2);
        second_main = view.findViewById(R.id.mypage_second_hobby1);
        second_sub = view.findViewById(R.id.mypage_second_hobby2);
        third_main = view.findViewById(R.id.mypage_thirdhobby1);
        third_sub = view.findViewById(R.id.mypage_thirdhobby2);
        // 수정 정보
        edit_btn = view.findViewById(R.id.edit);
        save_btn = view.findViewById(R.id.savebtn);
        area_EditText = view.findViewById(R.id.profile_area_edit);
        man_btn = (RadioButton) view.findViewById(R.id.man);
        woman_btn = (RadioButton) view.findViewById(R.id.woman);

        spinner_hobby1_edit = (Spinner) view.findViewById(R.id.hobby_first_edit);
        spinner_hobby2_edit = (Spinner) view.findViewById(R.id.hobby_first_sub_edit);
        hobby_first_edit = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, bigHobbyNameList);

        spinner_hobby1_edit.setAdapter(hobby_first_edit);
        spinner_hobby1_edit.setOnItemSelectedListener(editspinnerSelectListener);

        spinner_hobby_second1_edit = (Spinner) view.findViewById(R.id.hobby_second_edit);
        spinner_hobby_second2_edit = (Spinner) view.findViewById(R.id.hobby_second_sub_edit);
        hobby_second_edit = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, bigHobbyNameList);

        spinner_hobby_second1_edit.setAdapter(hobby_second_edit);
        spinner_hobby_second1_edit.setOnItemSelectedListener(editspinnerSelectListener);

        spinner_hobby_third1_edit = (Spinner) view.findViewById(R.id.hobby_third_edit);
        spinner_hobby_third2_edit = (Spinner) view.findViewById(R.id.hobby_third_sub_edit);
        hobby_third_edit = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, bigHobbyNameList);

        spinner_hobby_third1_edit.setAdapter(hobby_third_edit);
        spinner_hobby_third1_edit.setOnItemSelectedListener(editspinnerSelectListener);


        man_btn.setClickable(false);
        woman_btn.setClickable(false);

        /*  수정 버튼을 누르면 FrameLayout의 GONE으로
            해놓은 위젯들이 VISIBLE로 변환하여 화면에 출력  */

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setVisibleView(true);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });


        /**
         *   서버로부터 데이터를 입력받는 Task
         */
        datarecivetask = new GetMyInfoTask(new GetMyInfoTask.TaskResultHandler() {
            @Override
            public void onSuccesTask(MyInfoResult result) {

                if (result.isSuccess()) {

                    if (result.getUserInfos() != null && result.getUserInfos().size() > 0) {

                        UserData myInfo = result.getUserInfos().get(0);

                        Glide.with(getContext()).load(myInfo.getProfileimage()).into(profileImage);

                        name_TextView.setText(myInfo.getName());
                        area_TextView.setText(myInfo.getArea());
                        age_TextView.setText("(" + myInfo.getAge() + ")");

                        boolean first = false, sec = false, thr = false;

                        for(HobbyData data : bigHobbyList){
                            if(data.getHobby_num() == myInfo.getBh_number_1()){
                                first_main.setText(data.getHobby_name());
                                first = true;
                            }
                            if(data.getHobby_num() == myInfo.getBh_number_2()){
                                second_main.setText(data.getHobby_name());
                                sec = true;
                            }
                            if(data.getHobby_num() == myInfo.getBh_number_3()){
                                third_main.setText(data.getHobby_name());
                                thr = true;
                            }

                            if(first && sec && thr)
                                break;
                        }

                        first = false;
                        sec = false;
                        thr = false;

                        for(HobbyData data : smallHobbyList){
                            if(data.getHobby_num() == myInfo.getSh_number_1()){
                                first_sub.setText(data.getHobby_name());
                                first = true;
                            }
                            if(data.getHobby_num() == myInfo.getSh_number_2()){
                                second_sub.setText(data.getHobby_name());
                                sec = true;
                            }
                            if(data.getHobby_num() == myInfo.getSh_number_3()){
                                third_sub.setText(data.getHobby_name());
                                thr = true;
                            }

                            if(first && sec && thr)
                                break;
                        }


                        if (myInfo.getGender().equals("남자")) {
                            man_btn.setSelected(true);
                            man_btn.setChecked(true);
                        } else {
                            woman_btn.setChecked(true);
                            woman_btn.setSelected(true);
                        }
                    }

                }
            }


            @Override
            public void onFailTask() {
                Toast.makeText(getContext(), "서버통신 실패", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelTask() {
                Toast.makeText(getContext(), "서버통신 취소", Toast.LENGTH_SHORT).show();
            }
        });

        datarecivetask.execute(ApiValue.API_GET_MY_INFO, PreferenceData.getKeyUserId());

        return view;
    }


    // Spinner Listener

    private AdapterView.OnItemSelectedListener editspinnerSelectListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            switch (adapterView.getId()) {

                case R.id.hobby_first_edit:
                    ArrayAdapter edit_hobby_first_adapter = null;
                    ArrayList<String> smallHobbyNameList = new ArrayList<String>();

                    smallHobbyNameList.clear();

                    for(HobbyData data : smallHobbyList){
                        if(data.getHobby_big_num() == bigHobbyList.get(i).getHobby_num()){
                            smallHobbyNameList.add(data.getHobby_name());
                        }
                    }

                    edit_hobby_first_adapter = new ArrayAdapter(view.getContext(), R.layout.support_simple_spinner_dropdown_item, smallHobbyNameList);

                    if (edit_hobby_first_adapter != null) {
                        spinner_hobby2_edit.setAdapter(edit_hobby_first_adapter);
                        edit_hobby_first_adapter.notifyDataSetChanged();

                        spinner_hobby2_edit.setOnItemSelectedListener(editspinnerSelectListener);
                    }

                    bh_number_1 = String.valueOf(bigHobbyList.get(i).getHobby_num());

                    break;

                case R.id.hobby_second_edit:
                    ArrayAdapter edit_hobby_second_adapter = null;
                    ArrayList<String> smallHobbyNameList2 = new ArrayList<String>();

                    smallHobbyNameList2.clear();

                    for(HobbyData data : smallHobbyList){
                        if(data.getHobby_big_num() == bigHobbyList.get(i).getHobby_num()){
                            smallHobbyNameList2.add(data.getHobby_name());
                        }
                    }

                    edit_hobby_second_adapter = new ArrayAdapter(view.getContext(), R.layout.support_simple_spinner_dropdown_item, smallHobbyNameList2);

                    if (edit_hobby_second_adapter != null) {
                        spinner_hobby_second2_edit.setAdapter(edit_hobby_second_adapter);
                        edit_hobby_second_adapter.notifyDataSetChanged();

                        spinner_hobby_second2_edit.setOnItemSelectedListener(editspinnerSelectListener);
                    }

                    bh_number_2 = String.valueOf(bigHobbyList.get(i).getHobby_num());
                    break;


                case R.id.hobby_third_edit:

                    ArrayAdapter edit_hobby_third_adapter = null;
                    ArrayList<String> smallHobbyNameList3 = new ArrayList<String>();

                    smallHobbyNameList3.clear();

                    for(HobbyData data : smallHobbyList){
                        if(data.getHobby_big_num() == bigHobbyList.get(i).getHobby_num()){
                            smallHobbyNameList3.add(data.getHobby_name());
                        }
                    }

                    edit_hobby_third_adapter = new ArrayAdapter(view.getContext(), R.layout.support_simple_spinner_dropdown_item, smallHobbyNameList3);

                    if (edit_hobby_third_adapter != null) {
                        spinner_hobby_third2_edit.setAdapter(edit_hobby_third_adapter);
                        edit_hobby_third_adapter.notifyDataSetChanged();

                        spinner_hobby_third2_edit.setOnItemSelectedListener(editspinnerSelectListener);
                    }

                    bh_number_3 = String.valueOf(bigHobbyList.get(i).getHobby_num());
                    break;


                case R.id.hobby_first_sub_edit:

                    for (HobbyData data : smallHobbyList){
                        if(data.getHobby_name().equals((String)adapterView.getSelectedItem())){
                            sh_number_1 = String.valueOf(data.getHobby_num());
                            break;
                        }
                    }

                    break;

                case R.id.hobby_second_sub_edit:

                    for (HobbyData data : smallHobbyList){
                        if(data.getHobby_name().equals((String)adapterView.getSelectedItem())){
                            sh_number_2 = String.valueOf(data.getHobby_num());
                            break;
                        }
                    }

                    break;

                case R.id.hobby_third_sub_edit:
                    for (HobbyData data : smallHobbyList){
                        if(data.getHobby_name().equals((String)adapterView.getSelectedItem())){
                            sh_number_3 = String.valueOf(data.getHobby_num());
                            break;
                        }
                    }

                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void setVisibleView(boolean isEdit){

        if(isEdit){
            edit_btn.setVisibility(View.GONE);
            save_btn.setVisibility(View.VISIBLE);

            area_TextView.setVisibility(View.GONE);
            area_EditText.setVisibility(View.VISIBLE);

            first_main.setVisibility(View.GONE);
            spinner_hobby1_edit.setVisibility(View.VISIBLE);
            spinner_hobby1_edit.setAdapter(hobby_first_edit);

            first_sub.setVisibility(View.GONE);
            spinner_hobby2_edit.setVisibility(View.VISIBLE);

            second_main.setVisibility(View.GONE);
            spinner_hobby_second1_edit.setVisibility(View.VISIBLE);
            spinner_hobby_second1_edit.setAdapter(hobby_second_edit);

            second_sub.setVisibility(View.GONE);
            spinner_hobby_second2_edit.setVisibility(View.VISIBLE);

            third_main.setVisibility(View.GONE);
            spinner_hobby_third1_edit.setVisibility(View.VISIBLE);
            spinner_hobby_third1_edit.setAdapter(hobby_third_edit);

            third_sub.setVisibility(View.GONE);
            spinner_hobby_third2_edit.setVisibility(View.VISIBLE);

        }else{
            edit_btn.setVisibility(View.VISIBLE);
            save_btn.setVisibility(View.GONE);

            area_EditText.setText("");
            area_EditText.setVisibility(View.GONE);
            area_TextView.setVisibility(View.VISIBLE);

            first_main.setVisibility(View.VISIBLE);
            spinner_hobby1_edit.setVisibility(View.GONE);
            spinner_hobby1_edit.setAdapter(null);

            first_sub.setVisibility(View.VISIBLE);
            spinner_hobby2_edit.setVisibility(View.GONE);

            second_main.setVisibility(View.VISIBLE);
            spinner_hobby_second1_edit.setVisibility(View.GONE);
            spinner_hobby_second1_edit.setAdapter(null);

            second_sub.setVisibility(View.VISIBLE);
            spinner_hobby_second2_edit.setVisibility(View.GONE);

            third_main.setVisibility(View.VISIBLE);
            spinner_hobby_third1_edit.setVisibility(View.GONE);
            spinner_hobby_third1_edit.setAdapter(null);

            third_sub.setVisibility(View.VISIBLE);
            spinner_hobby_third2_edit.setVisibility(View.GONE);

            man_btn.setClickable(false);
            woman_btn.setClickable(false);


        }
    }


    private void updateData() {
        ModifyinformationTask modifyinformationTask = new ModifyinformationTask(new ModifyinformationTask.MyInfoResulthandler() {
            @Override
            public void onSuccessTask(ModifyResult result) {

                if(result.isSuccess()){
                    // 수정 데이터 셋팅
                    area_TextView.setText(area_EditText.getText().toString());
                    first_main.setText(spinner_hobby1_edit.getSelectedItem().toString());
                    first_sub.setText(spinner_hobby2_edit.getSelectedItem().toString());
                    second_main.setText(spinner_hobby_second1_edit.getSelectedItem().toString());
                    second_sub.setText(spinner_hobby_second2_edit.getSelectedItem().toString());
                    third_main.setText(spinner_hobby_third1_edit.getSelectedItem().toString());
                    third_sub.setText(spinner_hobby_third2_edit.getSelectedItem().toString());
                }
                else{
                    Toast.makeText(getContext(), result.getError(), Toast.LENGTH_SHORT).show();
                }

                setVisibleView(false);
            }

            @Override
            public void onFailTask() {
                setVisibleView(false);
                Toast.makeText(getContext(), "서버 통신 실패", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelTask() {
                setVisibleView(false);
                Toast.makeText(getContext(), "서버 통신 실패", Toast.LENGTH_SHORT).show();
            }
        });
        modifyinformationTask.execute(ApiValue.API_ModifyInfo, PreferenceData.getKeyUserId(), area_EditText.getText().toString(),
                bh_number_1, bh_number_2, bh_number_3, sh_number_1, sh_number_2,
                sh_number_3);

    }
}
