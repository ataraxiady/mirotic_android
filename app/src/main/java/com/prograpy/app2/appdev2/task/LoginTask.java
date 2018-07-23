package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.MyInfoResult;

import java.util.HashMap;
import java.util.Map;

public class LoginTask extends AsyncTask<String, Integer, MyInfoResult> {

    private LoginTaskResultHandler handler;


    public interface LoginTaskResultHandler{
        public void onSuccessTask(MyInfoResult result);

        public void onFailTask();

        public void onCancelTask();
    }

    public LoginTask(LoginTaskResultHandler handler){
        this.handler=handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected MyInfoResult doInBackground(String... strings) {

        String path = strings[0];
        String myId = strings[1];
        String myPw = strings[2];

        MyInfoResult result =null;

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("myId",myId);
        param.put("myPw",myPw);

        HttpRequest request = new HttpRequest();

        try{
            String str = request.callRequestServer(path, "POST", param);
            Log.d("http","str > " + str);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, MyInfoResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }
    @Override
    protected void onPostExecute(MyInfoResult result) {
        super.onPostExecute(result);

        //doinBackground 에서 넘겨준 result 값이 제대로 넘어왓는지 확인한다
        if(result != null){
            // 값이 제대로 넘어왔으면 핸들러의 성공함수 호출
            handler.onSuccessTask(result);
        }
        else{
            // 값이 제대로 안넘어 왓으면 실패함수 호출
            handler.onCancelTask();
        }
    }


}
