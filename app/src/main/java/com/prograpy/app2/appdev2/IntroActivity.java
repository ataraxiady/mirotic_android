package com.prograpy.app2.appdev2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.prograpy.app2.appdev2.main.MainActivity;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.data.HobbyData;
import com.prograpy.app2.appdev2.network.response.result.HobbyResult;
import com.prograpy.app2.appdev2.network.response.result.MyInfoResult;
import com.prograpy.app2.appdev2.task.GetHobbyTask;
import com.prograpy.app2.appdev2.task.LoginTask;
import com.prograpy.app2.appdev2.task.UpdateFcmKeyTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {

    private ArrayList<HobbyData> bigHobbyList = new ArrayList<HobbyData>();
    private ArrayList<HobbyData> smallHobbyList = new ArrayList<HobbyData>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);


        if (CheckPermission(this)) {

            callHobbyListData();

        }
    }


    private void updateFcmKey() {

        UpdateFcmKeyTask updateFcmKeyTask = new UpdateFcmKeyTask(null);
        updateFcmKeyTask.execute(ApiValue.API_UPDATE_FCM_KEY, PreferenceData.getKeyUserId(), PreferenceData.getKeyFcmToken());
    }


    private void callHobbyListData(){

        GetHobbyTask hobbyTask = new GetHobbyTask(new GetHobbyTask.TaskResultHandler() {
            @Override
            public void onSuccessTask(HobbyResult result) {


                if(result.isSuccess()){


                    if(result.getBigHobbyList() != null && result.getBigHobbyList().size() > 0 &&
                            result.getSmallHobbyList() != null && result.getSmallHobbyList().size() > 0){

                        bigHobbyList  = result.getBigHobbyList();
                        smallHobbyList = result.getSmallHobbyList();

                        if (PreferenceData.getKeyUserLoginSuccess()) {

                            callLoginTask();


                        } else {

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Intent k = new Intent(IntroActivity.this, LoginActivity.class);
                                    k.putParcelableArrayListExtra("bigHobby", bigHobbyList);
                                    k.putParcelableArrayListExtra("smallHobby", smallHobbyList);
                                    startActivity(k);
                                    finish();
                                }
                            }, 2000);
                        }

                    }else{

                        Toast.makeText(IntroActivity.this, "서버 통신 실패", Toast.LENGTH_SHORT).show();
                        finish();

                    }


                }else{

                    Toast.makeText(IntroActivity.this, "서버 통신 실패", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }

            @Override
            public void onFailTask() {

                Toast.makeText(IntroActivity.this, "서버 통신 실패", Toast.LENGTH_SHORT).show();
                finish();

            }

            @Override
            public void onCancelTask() {

                Toast.makeText(IntroActivity.this, "서버 통신 실패", Toast.LENGTH_SHORT).show();
                finish();

            }
        });


        hobbyTask.execute(ApiValue.API_GET_HOBBY_LIST);
    }


    
    private void callLoginTask(){
        LoginTask loginTask = new LoginTask(new LoginTask.LoginTaskResultHandler() {
            @Override
            public void onSuccessTask(MyInfoResult result) {
                if (result.isSuccess()) {
                    
                    if(result.getUserInfos().size() > 0){

                        PreferenceData.setKeyUserId(result.getUserInfos().get(0).getID());
                        PreferenceData.setKeyUserPw(result.getUserInfos().get(0).getPassword());
                        PreferenceData.setKeyUserLoginSuccess(true);

                        updateFcmKey();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent k = new Intent(IntroActivity.this, MainActivity.class);
                                k.putParcelableArrayListExtra("bigHobby", bigHobbyList);
                                k.putParcelableArrayListExtra("smallHobby", smallHobbyList);
                                startActivity(k);
                                finish();
                            }
                        }, 2000);

                    } else {
                        Toast.makeText(IntroActivity.this, result.getError(), Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent k = new Intent(IntroActivity.this, LoginActivity.class);
                                k.putParcelableArrayListExtra("bigHobby", bigHobbyList);
                                k.putParcelableArrayListExtra("smallHobby", smallHobbyList);
                                startActivity(k);
                                finish();
                            }
                        }, 2000);
                    }
                    
                } else {
                    Toast.makeText(IntroActivity.this, result.getError(), Toast.LENGTH_SHORT).show();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent k = new Intent(IntroActivity.this, LoginActivity.class);
                            k.putParcelableArrayListExtra("bigHobby", bigHobbyList);
                            k.putParcelableArrayListExtra("smallHobby", smallHobbyList);
                            startActivity(k);
                            finish();
                        }
                    }, 2000);

                }
            }

            @Override
            public void onFailTask() {

                PreferenceData.setKeyUserId("");
                PreferenceData.setKeyUserPw("");
                PreferenceData.setKeyUserLoginSuccess(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent k = new Intent(IntroActivity.this, LoginActivity.class);
                        k.putParcelableArrayListExtra("bigHobby", bigHobbyList);
                        k.putParcelableArrayListExtra("smallHobby", smallHobbyList);
                        startActivity(k);
                        finish();
                    }
                }, 2000);

                Toast.makeText(IntroActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelTask() {
                PreferenceData.setKeyUserId("");
                PreferenceData.setKeyUserPw("");
                PreferenceData.setKeyUserLoginSuccess(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent k = new Intent(IntroActivity.this, LoginActivity.class);
                        k.putParcelableArrayListExtra("bigHobby", bigHobbyList);
                        k.putParcelableArrayListExtra("smallHobby", smallHobbyList);
                        startActivity(k);
                        finish();
                    }
                }, 2000);

                Toast.makeText(IntroActivity.this, "서버 통신을 취소하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        loginTask.execute(ApiValue.API_LOGIN, PreferenceData.getKeyUserId(), PreferenceData.getKeyUserPw());
    }
    
    
    
    /**
     * 권한 있는지 없는지 체크하는거
     *
     * @param act
     * @return
     */
    public static Boolean CheckPermission(Activity act) {

        Boolean Permission = false;

        String[] PermissionCheckType = new String[3];
        PermissionCheckType[0] = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        PermissionCheckType[1] = android.Manifest.permission.READ_EXTERNAL_STORAGE;
        PermissionCheckType[2] = android.Manifest.permission.CAMERA;


        // 권한이 없는 경우
        if (ActivityCompat.checkSelfPermission(act, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(act, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(act, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {


            // 최초 요청 및 다시보지않기는 fasle
            if (ActivityCompat.shouldShowRequestPermissionRationale(act, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(act, android.Manifest.permission.READ_EXTERNAL_STORAGE) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(act, android.Manifest.permission.CAMERA)) {

                ActivityCompat.requestPermissions(act, PermissionCheckType, 0);
            } else { // 사용자가 거절한 경우

                ActivityCompat.requestPermissions(act, PermissionCheckType, 0);
            }


        } else {
            // 사용 권한이 있음을 확인한 경우
            Permission = true;
        }
        return Permission;
    }


    @SuppressLint("Override")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 0:
                // 권한이 없는 경우
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    //여기서 다시 보지 않기를 체크한다
                    //이쪽으로 들어오는 false는 다시보지 않기 체크로 인한 false

                    ActivityCompat.requestPermissions(this, permissions, 0);
                }else{

                    callHobbyListData();
                }
        }
    }
}

