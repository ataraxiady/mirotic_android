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
        String profileImage = strings[1];
        String name = strings[2];
        String gender = strings[3];
        int age = Integer.parseInt(strings[4]);
        String area = strings[5];

        FragmentResult result = null;

        Map<String, Object> params = new HashMap<>();

        params.put("profileImage", profileImage);
        params.put("name", name);
        params.put("gender", gender);
        params.put("age", age);
        params.put("area", area);

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
