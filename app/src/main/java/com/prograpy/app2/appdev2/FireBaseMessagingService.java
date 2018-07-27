package com.prograpy.app2.appdev2;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.prograpy.app2.appdev2.chat.ChatMainActivity;
import com.prograpy.app2.appdev2.utils.FileUtils;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.prograpy.app2.appdev2.utils.D;

public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "Firebase";


    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);

        String matchId = "";
        String matchImage ="";
        String matchName ="";

        if (intent.getExtras() != null && intent.getExtras().size() > 0) {
            D.log(TAG, "Message data payload: " + intent.getExtras());
            matchId = intent.getStringExtra("matchId");
            matchImage = intent.getStringExtra("matchImage");
            matchName = intent.getStringExtra("matchName");
        }


        if(matchId == null || matchImage == null || matchName == null)
            return;

        if(matchId.isEmpty() || matchImage.isEmpty() || matchName.isEmpty())
            return;

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List< ActivityManager.RunningTaskInfo > taskInfo = activityManager.getRunningTasks(1);

        D.log( "CURRENT Activity ",  taskInfo.get(0).topActivity.getShortClassName());


        if(taskInfo.get(0).topActivity.getShortClassName().contains("ChatMainActivity")){

            Intent intent2 = new Intent(this, ChatMainActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.putExtra("msg", (String)intent.getExtras().get("gcm.notification.body"));
            intent2.putExtra("matchId", matchId);
            intent2.putExtra("matchImage", matchImage);
            intent2.putExtra("matchName", matchName);
            startActivity(intent2);

        }else{

            if (intent.getExtras() != null) {
                D.log(TAG, "Message Notification Body: " + (String)intent.getExtras().get("gcm.notification.body"));
                sendNotification((String)intent.getExtras().get("gcm.notification.body"), matchName, matchId, matchImage);
            }

        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

//        String matchId = "";
//        String matchImage ="";
//
//        D.log(TAG, "From: " + remoteMessage.getFrom());
//
//        if (remoteMessage.getData().size() > 0) {
//            D.log(TAG, "Message data payload: " + remoteMessage.getData());
//            matchId = remoteMessage.getData().get("matchId");
//            matchImage = remoteMessage.getData().get("matchImage");
//        }
//
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        List< ActivityManager.RunningTaskInfo > taskInfo = activityManager.getRunningTasks(1);
//
//        D.log( "CURRENT Activity ",  taskInfo.get(0).topActivity.getShortClassName());
//
//
//        if(taskInfo.get(0).topActivity.getShortClassName().contains("ChatMainActivity")){
//
//            Intent intent = new Intent(this, ChatMainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("msg", remoteMessage.getNotification().getBody());
//            intent.putExtra("matchId", matchId);
//            intent.putExtra("matchImage", matchImage);
//            startActivity(intent);
//
//        }else{
//
//            if (remoteMessage.getNotification() != null) {
//                D.log(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//                sendNotification(remoteMessage.getNotification().getBody(), matchId, matchImage);
//            }
//
//        }

    }




    private void sendNotification(String messageBody, String matchName, String matchId, String matchImage) {
        Intent intent = new Intent(this, IntroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "com.prograpy.app2.appdev2";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.icon)
                        .setContentTitle(matchName)
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
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss").format(new Date()));

        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());


        FileUtils fileUtils = new FileUtils(this);

        String fileName = PreferenceData.getKeyUserId() + "_" + matchId + "_chat";

        if(!fileUtils.isFileExists(fileName)){
            fileUtils.createFile(fileName);
        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String fileText = messageBody + "," + format.format(date) + "," + 1 + "," + matchImage;
        fileUtils.writeFileText(fileName, fileText);

    }
}