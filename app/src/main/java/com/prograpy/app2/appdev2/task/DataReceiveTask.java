package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.DataReceiveResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 2018-07-11.
 */

public class DataReceiveTask extends AsyncTask<String, Integer, DataReceiveResult>
{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected DataReceiveResult doInBackground(String... strings) {
        String path = strings[0];
        String myId = strings[1];

        DataReceiveResult result = null;

        Map<String, Object> params = new HashMap<>();

        params.put("myId", myId);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path, "POST",params);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, DataReceiveResult.class);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(DataReceiveResult result) {
        super.onPostExecute(result);
    }
}
