package com.prograpy.app2.appdev2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.prograpy.app2.appdev2.main.MainActivity;
import com.prograpy.app2.appdev2.network.NetworkProgressDialog;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.data.UserData;
import com.prograpy.app2.appdev2.network.response.result.MyInfoResult;
import com.prograpy.app2.appdev2.profile.JoinActivity;
import com.prograpy.app2.appdev2.task.LoginTask;
import com.prograpy.app2.appdev2.task.UpdateFcmKeyTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<UserData> loginDataList = new ArrayList<UserData>();

    private NetworkProgressDialog networkProgressDialog;

    private Button join;
    private Button login;
    private Button pass;

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

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.d("newToken", newToken);

                PreferenceData.setKeyFcmToken(newToken);
            }
        });

        login = (Button) findViewById(R.id.login);
        join = (Button) findViewById(R.id.go_join);
        pass = (Button) findViewById(R.id.go_pass);

        myIdEdit = findViewById(R.id.id);

        myPwEdit = findViewById(R.id.password);

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateFcmKey();

                Intent k = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(k);
                finish();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(k);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myId = myIdEdit.getText().toString().trim();
                myPw = myPwEdit.getText().toString().trim();

//                networkProgressDialog.show();

                LoginTask loginTask = new LoginTask(new LoginTask.LoginTaskResultHandler() {
                    @Override
                    public void onSuccessTask(MyInfoResult result) {
//                        networkProgressDialog.dismiss();

                        if (result != null) {
                            if (result.isSuccess()) {

                                Log.d("확인","확인1");

                                loginDataList = result.getUserInfos();
                                if (loginDataList != null && loginDataList.size() > 0) {
                                    id = loginDataList.get(0).getID();
                                    pw = loginDataList.get(0).getPassword();
                                    Log.d("확인","확인2");
                                }

                                    Intent k = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(k);
                                    finish();
                                    Log.d("확인","확인3");

                            } else {
                                Toast.makeText(LoginActivity.this, result.getError(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailTask() {
                        networkProgressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.failed_server_connect), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelTask() {
                        networkProgressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "서버 통신을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                loginTask.execute(ApiValue.API_LOGIN, myId, myPw);
                Log.d("확인","확인4");

            }
        });



        if(PreferenceData.getKeyUserId().isEmpty()){
            pass.setVisibility(View.GONE);
            join.setVisibility(View.VISIBLE);
        }else{
            pass.setVisibility(View.VISIBLE);
//            join.setVisibility(View.GONE);
        }


    }


    private void updateFcmKey(){

        UpdateFcmKeyTask updateFcmKeyTask = new UpdateFcmKeyTask(null);
        updateFcmKeyTask.execute(ApiValue.API_UPDATE_FCM_KEY, PreferenceData.getKeyUserId(), PreferenceData.getKeyFcmToken());
    }

}
