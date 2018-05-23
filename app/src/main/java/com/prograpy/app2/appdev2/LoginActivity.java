package com.prograpy.app2.appdev2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.prograpy.app2.appdev2.profile.ProfileActivity;

public class LoginActivity extends AppCompatActivity  {

    private Button kakao;


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

    }

}
