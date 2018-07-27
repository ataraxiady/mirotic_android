package com.prograpy.app2.appdev2.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app2.appdev2.R;

public class ProvisionActivity extends AppCompatActivity {

    private WebView webView;

    private TextView mAgree;

    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_provision);


        webView = (WebView) findViewById(R.id.provision_view);
        mAgree = (TextView) findViewById(R.id.agree);

        mBack= (ImageView) findViewById(R.id.img_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProvisionActivity.this, JoinActivity.class);
                intent.putParcelableArrayListExtra("bigHobby", getIntent().getParcelableArrayListExtra("bigHobby"));
                intent.putParcelableArrayListExtra("smallHobby", getIntent().getParcelableArrayListExtra("smallHobby"));
                startActivity(intent);
            }
        });

        webView.loadUrl("http://13.209.78.126:8080/prography_terms.html");
    }
}
