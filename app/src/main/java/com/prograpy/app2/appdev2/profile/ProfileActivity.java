package com.prograpy.app2.appdev2.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.main.SubActivity;

public class ProfileActivity extends AppCompatActivity {
    private Button join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        join = (Button)findViewById(R.id.join);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, SubActivity.class);
                startActivity(i);
            }
        });
    }
}
