package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.DataReceiveResult;

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
        String url = strings[0];
        String path = strings[1];

        DataReceiveResult result = null;

        HttpRequest request = new HttpRequest();
        try {
            String str = request.callRequestServer(url,path,null);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, DataReceiveResult.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(DataReceiveResult result) {
        super.onPostExecute(result);
    }
}
