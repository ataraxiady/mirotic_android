package com.prograpy.app2.appdev2.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.result.SendResult;
import com.prograpy.app2.appdev2.task.SendMsgTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by samsung on 2018-05-15.
 */

public class ChatMainActivity extends AppCompatActivity {

    private ArrayList<String> subjectList;
    private ArrayAdapter<String> adapter;
    private ChatDataManager chatDataManager;
    private Button btnSend;
    private EditText edInputMsg;
    private String msg = "";
    private String sendTime = "";
    private int i;


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            msg = edInputMsg.getText().toString();

            if(!msg.isEmpty())
                sendTask();

            edInputMsg.setText("");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        chatDataManager = new ChatDataManager();

        subjectList = chatDataManager.getSubjectList();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, subjectList);

        ListView listView = (ListView) findViewById(R.id.chatListView);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(false);

        edInputMsg = (EditText) findViewById(R.id.editText);

        edInputMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty())
                    btnSend.setEnabled(false);
                else
                    btnSend.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnSend = (Button) findViewById(R.id.sendBtn);
        btnSend.setOnClickListener(listener);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d("onNewIntent", intent.getStringExtra("msg"));

        if(intent.getStringExtra("msg") != null &&
                !intent.getStringExtra("msg").isEmpty()){

            msg = intent.getStringExtra("msg");

            Log.d("onResume", msg);

            chatDataManager.addData(msg);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }

    private void sendTask(){

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        SendMsgTask sendMsgTask = new SendMsgTask(new SendMsgTask.TaskResultHandler() {
            @Override
            public void onSuccessTask(SendResult result) {

                if(result.isSuccess()){
                    chatDataManager.addData(msg);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(ChatMainActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailTask() {
                Toast.makeText(ChatMainActivity.this, "메세지 전송 실패. 잠시 후 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelTask() {

            }
        });


        sendMsgTask.execute(ApiValue.API_SEND_MSG, "ㅎㅎㅎ_naver", "공유_naver", msg, format.format(date));
    }

}
