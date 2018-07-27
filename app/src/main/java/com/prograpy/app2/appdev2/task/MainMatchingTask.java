package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.MainMatchingResult;

import java.util.HashMap;
import java.util.Map;
import com.prograpy.app2.appdev2.utils.D;

public class MainMatchingTask extends AsyncTask<String,Integer, MainMatchingResult> {

    private TaskResultHandler handler;

    public interface TaskResultHandler {
        public void onSuccesTask(MainMatchingResult result);

        public void onFailTask();

        public void onCancelTask();
    }

    public MainMatchingTask(TaskResultHandler handler) {
        this.handler = handler;
    }

    @Override
    protected MainMatchingResult doInBackground(String... strings) {
        String path = strings[0];
        String myId = strings[1];
        String myGender = strings[2];

        MainMatchingResult result = null;

        Map<String, Object> params = new HashMap<>();

        params.put("myId", myId);
        params.put("myGender", myGender);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path, "POST", params);
            D.log("HttpRequest", "str >" + str);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, MainMatchingResult.class);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }


        return result;
    }

    protected void onPostExecute(MainMatchingResult result) {
        super.onPostExecute(result);

        if (result != null) {
            handler.onSuccesTask(result);
        } else {
            handler.onCancelTask();
        }
    }
}
