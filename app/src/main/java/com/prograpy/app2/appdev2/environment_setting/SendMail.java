package com.prograpy.app2.appdev2.environment_setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prograpy.app2.appdev2.R;

public class SendMail extends AppCompatActivity {
    private EditText subject;
    private EditText content;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmail_setting);

        subject = (EditText) findViewById(R.id.subject2);
        content = (EditText) findViewById(R.id.content);
        btn = (Button)findViewById(R.id.sender);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] address = {"pctmdehd19@naver.com"};
                String subjectsend = subject.getText().toString();
                String contentsend = content.getText().toString();


                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, subjectsend);
                email.setType("plain/text");
                email.putExtra(Intent.EXTRA_TEXT, contentsend);


                startActivity(email);

            }
        });


    }
}
