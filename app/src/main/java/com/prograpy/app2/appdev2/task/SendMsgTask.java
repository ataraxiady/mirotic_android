package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.ServerResult;

import java.util.HashMap;
import java.util.Map;
import com.prograpy.app2.appdev2.utils.D;

public class SendMsgTask extends AsyncTask<String, Integer, ServerResult>{

    private TaskResultHandler handler;

    public interface TaskResultHandler {
        public void onSuccessTask(ServerResult result);

        public void onFailTask();

        public void onCancelTask();
    }

    public SendMsgTask(TaskResultHandler handler) {
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ServerResult doInBackground(String... strings) {
        String path = strings[0];
        String myId = strings[1];
        String matchId = strings[2];
        String sendMsg = strings[3];
        String sendTime = strings[4];

        ServerResult result = null;

        Map<String, Object> params = new HashMap<>();

        params.put("myId", myId);
        params.put("matchId", matchId);
        params.put("sendMsg", sendMsg);
        params.put("sendTime", sendTime);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path, "POST", params);
            D.log("HttpRequest", "str >" + str);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, ServerResult.class);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }


        return result;
    }

    @Override
    protected void onPostExecute(ServerResult sendResult) {
        super.onPostExecute(sendResult);

        if (sendResult != null) {
            handler.onSuccessTask(sendResult);
        } else {
            handler.onCancelTask();
        }
    }
}
