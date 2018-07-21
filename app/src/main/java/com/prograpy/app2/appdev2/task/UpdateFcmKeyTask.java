package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.ServerResult;

import java.util.HashMap;
import java.util.Map;

public class UpdateFcmKeyTask extends AsyncTask<String, Integer, ServerResult> {


    private TaskResultHandler handler;

    public interface TaskResultHandler {
        public void onSuccessTask(ServerResult result);

        public void onFailTask();

        public void onCancelTask();
    }

    public UpdateFcmKeyTask(TaskResultHandler handler) {
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
        String fcmKey = strings[2];

        ServerResult result = null;

        Map<String, Object> params = new HashMap<>();
        params.put("myId", myId);
        params.put("fcmKey", fcmKey);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path, "POST", params);
            Log.d("http", "str >" + str);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, ServerResult.class);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }


        return result;
    }


    @Override
    protected void onPostExecute(ServerResult serverResult) {
        super.onPostExecute(serverResult);

    }
}
