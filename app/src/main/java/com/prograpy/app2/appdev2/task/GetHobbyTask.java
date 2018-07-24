package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.HobbyResult;

import java.util.HashMap;
import java.util.Map;

public class GetHobbyTask extends AsyncTask<String, Integer, HobbyResult> {

    private TaskResultHandler handler;


    public interface TaskResultHandler{
        public void onSuccessTask(HobbyResult result);
        public void onFailTask();
        public void onCancelTask();
    }


    public GetHobbyTask(TaskResultHandler handler){
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HobbyResult doInBackground(String... strings) {

        String path = strings[0];

        HobbyResult result = null;

        // http로 서버에 요청을 위한 클래스 객체 생성
        HttpRequest request = new HttpRequest();


        try {
            String str = request.callRequestServer(path,  "GET", null);


            Log.d("http", "str > " + str);


            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, HobbyResult.class);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        // 결과 리턴
        return result;
    }


    @Override
    protected void onPostExecute(HobbyResult result) {
        super.onPostExecute(result);

        if(result != null){
            handler.onSuccessTask(result);
        }
        else{
            handler.onCancelTask();
        }
    }
}

