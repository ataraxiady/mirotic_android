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

import com.prograpy.app2.appdev2.main.MainActivity;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.task.UpdateFcmKeyTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);


        if (CheckPermission(this)) {

            if (PreferenceData.getKeyUserLoginSuccess()) {

                updateFcmKey();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent k = new Intent(IntroActivity.this, MainActivity.class);
                        startActivity(k);
                        finish();
                    }
                }, 2000);

            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent k = new Intent(IntroActivity.this, LoginActivity.class);
                        startActivity(k);
                        finish();
                    }
                }, 2000);
            }

        }
    }


    private void updateFcmKey() {

        UpdateFcmKeyTask updateFcmKeyTask = new UpdateFcmKeyTask(null);
        updateFcmKeyTask.execute(ApiValue.API_UPDATE_FCM_KEY, PreferenceData.getKeyUserId(), PreferenceData.getKeyFcmToken());
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
                }
        }
    }
}
