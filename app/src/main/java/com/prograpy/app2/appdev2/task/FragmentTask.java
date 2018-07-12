package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.FragmentResult;

import java.util.HashMap;
import java.util.Map;

public class FragmentTask extends AsyncTask<String,Integer,FragmentResult> {

    private FragmentResultHandler handler;

    public interface FragmentResultHandler {
        public void onSuccesTask(FragmentResult result);

        public void onFailTask();

        public void onCancelTask();
    }

    public FragmentTask(FragmentResultHandler handler) {
        this.handler = handler;
    }

    @Override
    protected FragmentResult doInBackground(String... strings) {
        String path = strings[0];
        String myId = strings[1];

        FragmentResult result = null;

        Map<String, Object> params = new HashMap<>();

        params.put("myId", myId);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path, "POST", params);
            Log.d("http", "str >" + str);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, FragmentResult.class);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }


        return result;
    }

    protected void onPostExecute(FragmentResult result) {
        super.onPostExecute(result);

        if (result != null) {
            handler.onSuccesTask(result);
        } else {
            handler.onCancelTask();
        }
    }
}
