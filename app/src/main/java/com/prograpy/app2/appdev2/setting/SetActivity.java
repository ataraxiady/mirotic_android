package com.prograpy.app2.appdev2.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;

public class SetActivity extends AppCompatActivity {
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
    }

    public void btn1(View v)
    {
        Toast.makeText(this,"마이페이지", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this,Sub1Activity.class);
        startActivity(intent);
    }
    public void btn2(View v)
    {
        Toast.makeText(this,"환경설정", Toast.LENGTH_LONG).show();
    }
    public void btn3(View v)
    {
        Toast.makeText(this,"Q / A", Toast.LENGTH_LONG).show();
    }
    public void btn4(View v)
    {
        Toast.makeText(this,"이용약관", Toast.LENGTH_LONG).show();
    }


}
