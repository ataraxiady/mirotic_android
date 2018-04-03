package com.prograpy.app2.appdev2.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;

public class Sub1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);
    }
    public void onClick(View v)
    {
        Toast.makeText(this, "버튼이 눌림", Toast.LENGTH_SHORT).show();
        finish();
    }
}
