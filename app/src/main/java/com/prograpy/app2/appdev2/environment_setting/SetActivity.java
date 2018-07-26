package com.prograpy.app2.appdev2.environment_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.prograpy.app2.appdev2.R;

public class SetActivity extends AppCompatActivity {
    private TextView vs, sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);
        vs = findViewById(R.id.version);
        sendMail = findViewById(R.id.send);


        vs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent version = new Intent(SetActivity.this, SettingAppVersion.class);
                startActivity(version);
            }
        });

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(SetActivity.this, SendMail.class);
                startActivity(send);
            }
        });



  }
}
