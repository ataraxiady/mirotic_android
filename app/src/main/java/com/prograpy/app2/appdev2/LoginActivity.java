package com.prograpy.app2.appdev2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.OAuthLoginDialogMng;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;
import com.prograpy.app2.appdev2.profile.ProfileActivity;

public class LoginActivity extends AppCompatActivity {

    private Button kakao;

    OAuthLogin mOAuthLoginModule;
    OAuthLoginButton authLoginButton;
    Context mContext;

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                final String accessToken = mOAuthLoginModule.getAccessToken(mContext);

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


        kakao = (Button) findViewById(R.id.btnKakao);

        kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(k);
            }
        });

        // 네이버 아이디 로그인을 요청하는 코드
        authLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                LoginActivity.this
                ,"AFa6RqAFrtTigjbHCodg"
                ,"_msJq65bIS"
                ,"MeYou"

        );
        /*
        if (mOAuthLoginModule.getAccessToken(this) != null) {
            startActivity(new Intent(this,LoginActivity.class));
        } else {
            authLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        }
        */

    }

}
