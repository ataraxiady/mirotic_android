package com.prograpy.app2.appdev2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.prograpy.app2.appdev2.profile.ProfileActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button kakao;
    private Button naver;
    private Button facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        kakao = (Button) findViewById(R.id.btnKakao);
        naver = (Button) findViewById(R.id.btnNaver);
        facebook = (Button) findViewById(R.id.btnFacebook);


        kakao.setOnClickListener(this);
        naver.setOnClickListener(this);
        facebook.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnKakao:
                Intent k = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(k);
                break;
            case  R.id.btnNaver:
                Intent n = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(n);
                break;
            case  R.id.btnFacebook:
                Intent f = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(f);
                break;
        }

    }
}
