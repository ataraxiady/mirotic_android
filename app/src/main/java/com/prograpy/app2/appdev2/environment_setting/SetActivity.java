package com.prograpy.app2.appdev2.environment_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.prograpy.app2.appdev2.R;

public class SetActivity extends AppCompatActivity {
    private TextView vs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        vs = findViewById(R.id.version);

        vs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetActivity.this, SettingAppVersion.class);
                startActivity(intent);
            }
        });
  }
}
