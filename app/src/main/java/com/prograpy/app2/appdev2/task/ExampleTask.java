package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.ExampleResult;

/**
 * Created by samsung on 2018-06-01.
 */

public class ExampleTask extends AsyncTask<String, Integer, ExampleResult> {

    private ExampleTaskResultHandler handler;


    public interface ExampleTaskResultHandler{
        public void onSuccessExampleTask(ExampleResult result);
        public void onFailExampleTask();
        public void onCancelExampleTask();
    }



    public ExampleTask(ExampleTaskResultHandler handler){
        this.handler = handler;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ExampleResult doInBackground(String... strings) {

        String url = strings[0];
        String path = strings[1];

        ExampleResult result  = null;


        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(url, path,  "GET",null);

            Log.d("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, ExampleResult.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(ExampleResult exampleTaskResult) {
        super.onPostExecute(exampleTaskResult);

        if(exampleTaskResult != null){
            handler.onSuccessExampleTask(exampleTaskResult);
        }else{
            handler.onFailExampleTask();
        }

    }
}
