package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.MyInfoResult;

import java.util.HashMap;
import java.util.Map;
import com.prograpy.app2.appdev2.utils.D;

/**
 * Created by User on 2018-07-11.
 */

public class GetMyInfoTask extends AsyncTask<String, Integer, MyInfoResult>
{

    private  TaskResultHandler handler;

    public interface TaskResultHandler{
        public void onSuccesTask(MyInfoResult result);

        public void onFailTask();

        public void onCancelTask();
    }

    public GetMyInfoTask(TaskResultHandler handler)
    {
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected MyInfoResult doInBackground(String... strings) {

        String path = strings[0];
        String myId = strings[1];

        MyInfoResult result = null;

        Map<String, Object> params = new HashMap<>();

        params.put("myId", myId);

        HttpRequest request = new HttpRequest();

        try {

            String str = request.callRequestServer(path, "POST",params);

            D.log("HttpRequest", "str > " + str);

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

        if(result != null){
            handler.onSuccesTask(result);
        }
        else{
            handler.onCancelTask();
        }
    }
}
