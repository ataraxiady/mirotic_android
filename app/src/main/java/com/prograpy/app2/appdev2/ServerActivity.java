package com.prograpy.app2.appdev2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app2.appdev2.network.response.ExampleResult;
import com.prograpy.app2.appdev2.task.ExampleTask;

/**
 * Created by samsung on 2018-06-01.
 */

public class ServerActivity extends AppCompatActivity{
    private String url = "http://13.209.78.126:8080";
    private String path = "/appDev2/addUserInfo";

    private ImageView infoPic;
    private TextView infoName;
    private TextView infoText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_info);

        infoPic = (ImageView) findViewById(R.id.infoPic);
        infoName = (TextView) findViewById(R.id.infoName);
        infoText = (TextView) findViewById(R.id.infoText);

        ExampleTask exampleTask = new ExampleTask(new ExampleTask.ExampleTaskResultHandler() {
            @Override
            public void onSuccessExampleTask(ExampleResult result) {
                if(result.isSuccess()){
                    ExampleDbList info = result.getDbList().get(0);
                    infoPic.setImageDrawable(result.getDbList().get(0).profileimage);
                    infoName.setText(info.name);
                    infoText.setText(info.bh_number_1 +" - " +info.sh_number_1);
                }
            }

            @Override
            public void onFailExampleTask() {

            }

            @Override
            public void onCancelExampleTask() {

            }
        });




    }


}
