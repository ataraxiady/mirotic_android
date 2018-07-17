package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.MatchResult;

import java.util.HashMap;
import java.util.Map;

public class MatchListTask extends AsyncTask<String,Integer, MatchResult> {

    private TaskResultHandler handler;

    public interface TaskResultHandler {
        public void onSuccessTask(MatchResult result);

        public void onFailTask();

        public void onCancelTask();
    }

    public MatchListTask(TaskResultHandler handler) {
        this.handler = handler;
    }

    @Override
    protected MatchResult doInBackground(String... strings) {
        String path = strings[0];
        String myId = strings[1];

        MatchResult result = null;

        Map<String, Object> params = new HashMap<>();

        params.put("myId", myId);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path, "POST", params);
            Log.d("http", "str >" + str);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, MatchResult.class);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }


        return result;
    }

    protected void onPostExecute(MatchResult result) {
        super.onPostExecute(result);

        if (result != null) {
            handler.onSuccessTask(result);
        } else {
            handler.onCancelTask();
        }
    }
}
