package com.prograpy.app2.appdev2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.prograpy.app2.appdev2.main.MainActivity;
import com.prograpy.app2.appdev2.network.NetworkProgressDialog;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.data.UserData;
import com.prograpy.app2.appdev2.network.response.result.MyInfoResult;
import com.prograpy.app2.appdev2.network.response.result.ServerResult;
import com.prograpy.app2.appdev2.profile.JoinActivity;
import com.prograpy.app2.appdev2.task.LoginTask;
import com.prograpy.app2.appdev2.task.UpdateFcmKeyTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;
import com.prograpy.app2.appdev2.utils.Utils;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<UserData> loginDataList = new ArrayList<UserData>();

    private NetworkProgressDialog networkProgressDialog;

    private ImageView join;
    private ImageView login;

    private EditText myIdEdit;
    private EditText myPwEdit;

    String myId;
    String myPw;
    String id;
    String pw;


    OAuthLogin mOAuthLoginModule;
    OAuthLoginButton authLoginButton;
    Context mContext;

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(mContext);

                String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                String tokenType = mOAuthLoginModule.getTokenType(mContext);
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,  new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                String newToken = instanceIdResult.getToken();
//                Log.d("newToken", newToken);
//
//                PreferenceData.setKeyFcmToken(newToken);
//            }
//        });

        PreferenceData.setKeyFcmToken(FirebaseInstanceId.getInstance().getToken());

        networkProgressDialog = new NetworkProgressDialog(this);

        login = (ImageView) findViewById(R.id.login);
        join = (ImageView) findViewById(R.id.go_join);

        myIdEdit = findViewById(R.id.id);

        myPwEdit = findViewById(R.id.password);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(LoginActivity.this, JoinActivity.class);
                k.putParcelableArrayListExtra("bigHobby", getIntent().getParcelableArrayListExtra("bigHobby"));
                k.putParcelableArrayListExtra("smallHobby", getIntent().getParcelableArrayListExtra("smallHobby"));
                startActivity(k);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myId = myIdEdit.getText().toString().trim();
                myPw = myPwEdit.getText().toString().trim();

                if(!Utils.isValidId(myId)){
                    Toast.makeText(LoginActivity.this, "아이디는 영소문자+숫자 조합으로 4~16자 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!Utils.isValidPw(myPw)){
                    Toast.makeText(LoginActivity.this, "비밀번호는 영소문자+숫자 조합으로 8~16자 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                networkProgressDialog.show();

                LoginTask loginTask = new LoginTask(new LoginTask.LoginTaskResultHandler() {
                    @Override
                    public void onSuccessTask(MyInfoResult result) {
                        networkProgressDialog.dismiss();

                        if (result.isSuccess()) {

                            loginDataList = result.getUserInfos();

                            PreferenceData.setKeyUserId(loginDataList.get(0).getID());
                            PreferenceData.setKeyUserPw(loginDataList.get(0).getPassword());
                            PreferenceData.setKeyUserImage(loginDataList.get(0).getProfileimage());
                            PreferenceData.setKeyUserGender(loginDataList.get(0).getGender());
                            PreferenceData.setKeyUserLoginSuccess(true);

                            updateFcmKey();
                        } else {
                            Toast.makeText(LoginActivity.this, result.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailTask() {
                        networkProgressDialog.dismiss();

                        PreferenceData.setKeyUserId("");
                        PreferenceData.setKeyUserPw("");
                        PreferenceData.setKeyUserImage("");
                        PreferenceData.setKeyUserGender("");
                        PreferenceData.setKeyUserLoginSuccess(false);

                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelTask() {
                        networkProgressDialog.dismiss();

                        PreferenceData.setKeyUserId("");
                        PreferenceData.setKeyUserPw("");
                        PreferenceData.setKeyUserImage("");
                        PreferenceData.setKeyUserGender("");
                        PreferenceData.setKeyUserLoginSuccess(false);

                        Toast.makeText(LoginActivity.this, "서버 통신을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                loginTask.execute(ApiValue.API_LOGIN, myId, myPw);

            }
        });

    }



    private void updateFcmKey() {

        UpdateFcmKeyTask updateFcmKeyTask = new UpdateFcmKeyTask(new UpdateFcmKeyTask.TaskResultHandler() {
            @Override
            public void onSuccessTask(ServerResult result) {

                Intent k = new Intent(LoginActivity.this, MainActivity.class);
                k.putParcelableArrayListExtra("bigHobby", getIntent().getParcelableArrayListExtra("bigHobby"));
                k.putParcelableArrayListExtra("smallHobby", getIntent().getParcelableArrayListExtra("smallHobby"));
                startActivity(k);
                finish();
            }

            @Override
            public void onFailTask() {

            }

            @Override
            public void onCancelTask() {

            }
        });
        updateFcmKeyTask.execute(ApiValue.API_UPDATE_FCM_KEY, PreferenceData.getKeyUserId(), PreferenceData.getKeyFcmToken());
    }
}
