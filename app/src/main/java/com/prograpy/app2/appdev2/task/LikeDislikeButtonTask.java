package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.LikeDislikeResult;
import java.util.HashMap;
import java.util.Map;

public class LikeDislikeButtonTask extends AsyncTask<String, Integer, LikeDislikeResult> {

    private LikeDislikeResultHandler handler;

    public interface LikeDislikeResultHandler{
        public void onSuccesTask(LikeDislikeResult result);

        public void onFailTask();

        public void onCancelTask();
    }

    public LikeDislikeButtonTask(LikeDislikeResultHandler handler){
        this.handler = handler;
    }

    @Override
    protected LikeDislikeResult doInBackground(String... strings) {
        String path = strings[0];
        String myId = strings[1];
        String otherId = strings[2];
        String isLike = strings[3];

        LikeDislikeResult result = null;

        Map<String,Object> params = new HashMap<>();

        params.put("myId",myId);
        params.put("otherId",otherId);
        params.put("isLike", isLike);

        HttpRequest request = new HttpRequest();

        try {
            String str = request.callRequestServer(path,"POST",params);

                Log.d("http","str >" + str);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, LikeDislikeResult.class);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }


        return result;
    }

    protected void onPreExecute(){super.onPreExecute();}

    protected void onPostExecute(LikeDislikeResult result){
        super.onPostExecute(result);

        if(result != null){
            handler.onSuccesTask(result);
        }
        else{
            handler.onCancelTask();
        }
    }

}
