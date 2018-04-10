package com.prograpy.app2.appdev2.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.main.SubActivity;

public class ProfileActivity extends AppCompatActivity {
    private Button join;
    private Button nickname;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        join = (Button)findViewById(R.id.join);
        nickname = (Button)findViewById(R.id.nickname_btn);
        editText = (EditText)findViewById(R.id.nameText);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, SubActivity.class);
                startActivity(i);
            }
        });

        nickname.setOnClickListener(new View.OnClickListener()
    {

            public void onClick(View view)
            {
                final String nick = editText.getText().toString();
                if(nick.equals(""))
                {
                    Toast.makeText(ProfileActivity.this, "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProfileActivity.this, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
