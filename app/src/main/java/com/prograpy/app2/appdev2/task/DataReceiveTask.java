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

    private  DataReceiveTaskHandler handler;

    public interface DataReceiveTaskHandler{
        public void onSuccesTask(DataReceiveResult result);

        public void onFailTask();

        public void onCancelTask();
    }

    public DataReceiveTask(DataReceiveTaskHandler handler)
    {
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected DataReceiveResult doInBackground(String... strings) {
        String url = "";
        String path = strings[0];
        String name = strings[1];
        String gender = strings[2];
        int age = Integer.parseInt(strings[3]);
        String area = strings[4];
        String profileimage = strings[5];

        DataReceiveResult result = null;

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,"POST",null);

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

        if(result != null){
            handler.onSuccesTask(result);
        }
        else{
            handler.onCancelTask();
        }
    }
}
