package com.prograpy.app2.appdev2.chat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app2.appdev2.LoginActivity;
import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.data.ChatData;
import com.prograpy.app2.appdev2.network.response.result.ServerResult;
import com.prograpy.app2.appdev2.task.SendMsgTask;
import com.prograpy.app2.appdev2.utils.FileUtils;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by samsung on 2018-05-15.
 */

public class ChatMainActivity extends AppCompatActivity {

    private ArrayList<ChatData> chatList = new ArrayList<ChatData>();

    private RecyclerView chatListView;
    private ChatMainAdapter chatMainAdapter;

    private FileUtils fileUtils ;

    private Button btnSend;
    private EditText edInputMsg;
    private String msg = "";
    private String fileName = "";

    private String matchId ="";

    private int msgType = 0;


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

        matchId = getIntent().getStringExtra("matchId");

        fileName = PreferenceData.getKeyUserId() + "_" + matchId + "_chat";

        fileUtils = new FileUtils(this);

        if(fileUtils.isFileExists(fileName)){
            ArrayList<String> fileStrList = fileUtils.loadItemsFromFile(fileName);

            if(fileStrList != null && fileStrList.size() > 0){
                for (String str : fileStrList){
                    String[] splitStr = str.split(",");
                    ChatData chatData = new ChatData();
                    chatData.setChatMsg(splitStr[0]);
                    chatData.setChatTime(splitStr[1]);
                    chatData.setChatType(Integer.parseInt(splitStr[2]));

                    chatList.add(chatData);
                }
            }
        }else{
            fileUtils.createFile(fileName);
        }

        chatMainAdapter = new ChatMainAdapter();

        chatListView = (RecyclerView) findViewById(R.id.chatListView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        chatListView.setLayoutManager(manager);
        chatListView.setAdapter(chatMainAdapter);

        chatMainAdapter.setChatLists(chatList);
        chatMainAdapter.notifyDataSetChanged();
        chatListView.scrollToPosition(chatList.size() - 1);

        edInputMsg = (EditText) findViewById(R.id.editText);
        edInputMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND ||
                        actionId == EditorInfo.IME_ACTION_DONE){

                    msg = edInputMsg.getText().toString();

                    if(!msg.isEmpty())
                        sendTask();

                    edInputMsg.setText("");
                }
                return false;
            }
        });
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
        Log.d("onNewIntent", intent.getStringExtra("matchId"));

        // 메세지를 받긴 받았는데 다른 상대가 보낸 메세지일 경우는
        // 노티를 보여주고 그 상대방 파일에 저장
        // 아니면 현재 채팅방에서 노출
        if(matchId.equals(intent.getStringExtra("matchId"))){

            if(intent.getStringExtra("msg") != null &&
                    !intent.getStringExtra("msg").isEmpty()){

                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                msg = intent.getStringExtra("msg");
                msgType = 1;

                ChatData chatData = new ChatData();
                chatData.setChatMsg(msg);
                chatData.setChatTime(format.format(date));
                chatData.setChatType(msgType);
                chatList.add(chatData);

                String fileText = msg + "," + format.format(date) + "," + msgType;
                fileUtils.writeFileText(fileName, fileText);

            }

            chatMainAdapter.notifyDataSetChanged();
            chatListView.scrollToPosition(chatList.size() - 1);
        }else{
            sendNotification(intent.getStringExtra("msg"), intent.getStringExtra("matchId"));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        chatMainAdapter.notifyDataSetChanged();
        chatListView.scrollToPosition(chatList.size() - 1);
    }

    private void sendTask(){

        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        SendMsgTask sendMsgTask = new SendMsgTask(new SendMsgTask.TaskResultHandler() {
            @Override
            public void onSuccessTask(ServerResult result) {

                if(result.isSuccess()){
                    msgType = 0;

                    ChatData chatData = new ChatData();
                    chatData.setChatMsg(msg);
                    chatData.setChatTime(format.format(date));
                    chatData.setChatType(msgType);
                    chatList.add(chatData);

                    String fileText = msg + "," + format.format(date) + "," + msgType;
                    fileUtils.writeFileText(fileName, fileText);


                    chatMainAdapter.notifyDataSetChanged();
                    chatListView.scrollToPosition(chatList.size() - 1);

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


        sendMsgTask.execute(ApiValue.API_SEND_MSG, PreferenceData.getKeyUserId(), matchId, msg, format.format(date));
    }


    private void sendNotification(String messageBody, String matchId) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "com.prograpy.app2.appdev2";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.logo2)
                        .setContentTitle(matchId)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelName = "meyou";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


        FileUtils fileUtils = new FileUtils(this);

        String fileName = PreferenceData.getKeyUserId() + "_" + matchId + "_chat";

        if(!fileUtils.isFileExists(fileName)){
            fileUtils.createFile(fileName);
        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String fileText = messageBody + "," + format.format(date) + "," + 1;
        fileUtils.writeFileText(fileName, fileText);

    }

}
