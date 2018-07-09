package com.prograpy.app2.appdev2.task;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.JoinResult;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
public class MyPageTask extends AsyncTask<String, Integer, String> {
    // 각 인터페이스가 구현된 객체를 셋팅받을 핸들러 객체
    private JoinTask.JoinTaskResultHandler handler;

    public interface JoinTaskResultHandler{
        *//**
         * 성공에 대한 함수
         * @param result 서버 결과값
         *//*
        public void onSuccessTask(JoinResult result);

        *//**
         * 실패에 대한 함수
         *//*
        public void onFailTask();

        *//**
         * 취소에 대한 함수
         *//*
        public void onCancelTask();
    }

    public MyPageTask(JoinTask.JoinTaskResultHandler handler){

        // 구현된 인터페이스 내용을 JoinTask에 선언된 핸들러 객체에서 셋팅해준다.
        this.handler = handler;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {


        // 현재 task의 return 값이 JoinResult 클래스이므로 결과를 반환해줄 객체를 만든다
        JoinResult result = null;
        // http로 서버에 요청을 위한 클래스 객체 생성
        HttpRequest request = new HttpRequest();


        try {

            String str = request.callRequestServer(path,  "POST", params);

            // 서버 결과 값 확인
            Log.d("http", "str > " + str);


        } catch (Exception e) {
            // 에러가 나면 이부분으로 들어오게 된다.

            //에러 내용 출력 함수
            e.printStackTrace();

            // 에러가 나면 null을 리턴한다
            return null;
        }
        return result;
    }
    @Override
    protected void onPostExecute(JoinResult result) {
        super.onPostExecute(result);


        //doinBackground 에서 넘겨준 result 값이 제데로 넘어왓는지 확인한다
        if(result != null){
            // 값이 제데로 넘어왔으면 핸들러의 성공함수 호출
            handler.onSuccessTask(result);
        }
        else{
            // 값이 제데로 안넘어 왓으면 실패함수 호출
            handler.onCancelTask();
        }
    }

}*/
