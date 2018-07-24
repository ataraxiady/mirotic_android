package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.ModifyResult;
import com.prograpy.app2.appdev2.network.response.result.MyInfoResult;

import java.util.HashMap;
import java.util.Map;

public class ModifyinformationTask extends AsyncTask<String, String, ModifyResult> {


    private MyInfoResulthandler handler;

    public interface MyInfoResulthandler{
        public void onSuccessTask(ModifyResult result);
        public void onFailTask();
        public void onCancelTask();
    }

    public ModifyinformationTask(MyInfoResulthandler handler){
        this.handler = handler;
    };

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ModifyResult doInBackground(String... strings) {
        String path = strings[0];
        String myId = strings[1];
        String area = strings[2];
        int bh_number_1 = Integer.parseInt(strings[3]);
        int bh_number_2 = Integer.parseInt(strings[4]);
        int bh_number_3 = Integer.parseInt(strings[5]);
        int sh_number_1 = Integer.parseInt(strings[6]);
        int sh_number_2 = Integer.parseInt(strings[7]);
        int sh_number_3 = Integer.parseInt(strings[8]);

        ModifyResult result = null;

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("myId", myId);
        params.put("area", area);
        params.put("bh_number_1", bh_number_1);
        params.put("bh_number_2", bh_number_2);
        params.put("bh_number_3", bh_number_3);
        params.put("sh_number_1", sh_number_1);
        params.put("sh_number_2", sh_number_2);
        params.put("sh_number_3", sh_number_3);

        HttpRequest request = new HttpRequest();


        try {
            String str = request.callRequestServer(path,  "POST", params);


            // 서버 결과 값 확인
            Log.d("http", "str > " + str);

            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, ModifyResult.class);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return result;
    }

    @Override
    protected void onPostExecute(ModifyResult result) {
        super.onPostExecute(result);

        if (result != null) {
            handler.onSuccessTask(result);
        } else {
            handler.onCancelTask();
        }
    }
}
