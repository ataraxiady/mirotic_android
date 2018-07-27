package com.prograpy.app2.appdev2.network;

import android.util.Log;

import com.google.gson.Gson;
import com.prograpy.app2.appdev2.utils.D;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by samsung on 2018-06-01.
 */

public class HttpRequest {
    private static final String TAG = "HttpRequest";
    private static final int TIMEOUT_CONNECTION = 30000;
    private static final int TIMEOUT_READ = 30000;

    private static final String SERVER_URL = "http://13.209.78.126:8080/appDev2";

    /**
     * 서버 실행 요청을 전달하는 함수
     * @param api_path 요청할 api의 경로
     * @param method post / get
     * @param params 각 방식에 맞는 파라미터
     * @return 서버에서 받은 json 결과를 string으로 리턴
     * @throws Exception 주로 connection이 원활히 이루어지지 않았을 때 발생한다.
     */
    public static String callRequestServer (String api_path, String method, Map<String, Object> params) throws Exception{
        return startRequest(api_path, method, params);
    }

    /**
     * 실질적으로 서버쪽에 요청하는 함수
     * @param api_path 요청할 api의 경로
     * @param method post / get
     * @param params 각 방식에 맞는 파라미터
     * @return 서버에서 받은 json 결과를 string으로 리턴
     * @throws Exception 주로 connection이 원활히 이루어지지 않았을 때 발생한다.
     */
    public static String startRequest(String api_path, String method, Map<String, Object> params) throws Exception{
        URL url = null;

        if(method.equalsIgnoreCase("post")){
            url  = createUrl(api_path, null);
        }else{

            url  = createUrl(api_path, params);
        }

        D.log(TAG, "request url > " + url.toString());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setConnectTimeout(TIMEOUT_CONNECTION); //연결 타임아웃
            connection.setReadTimeout(TIMEOUT_READ); // 데이터 읽어들이는 타임아웃
            connection.setRequestMethod(method); // post or get


            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
            connection.setRequestProperty("Connection", "Keep-Alive");


            if (method.equals("GET") || method.equals("DELETE")) {

                connection.setDoOutput(false);
            } else {

                Gson gson = new Gson();
                String postJson = gson.toJson(params);

                D.log(TAG, "request send post json > " + postJson);

                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Length", Integer.toString(postJson.getBytes("utf-8").length));
                OutputStream os = connection.getOutputStream();
                os.write(postJson.getBytes("utf-8"));
                os.flush();
                os.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("API response error : " + 0);
        }

        int responseCode = 0;

        try {
            responseCode = connection.getResponseCode();

        }catch (InterruptedIOException e){
            e.printStackTrace();

        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        if (responseCode != HttpURLConnection.HTTP_OK) { // 정상적인 상황이 아닐때에는 에러를 던진다.
            throw new Exception("API response error : " + responseCode);
        }


        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer result = new StringBuffer();
        String buf = "";
        buf = reader.readLine();
        while(buf != null) {
            result.append(buf).append("\r\n");
            buf = reader.readLine();
        }

        String resultStr = result.toString();

        return resultStr;
    }



    public static URL createUrl (String api_path, Map<String, Object> params) throws MalformedURLException, UnsupportedEncodingException {

        boolean isFirst = true;

        StringBuffer urlStr = new StringBuffer();
        urlStr.append(SERVER_URL);
        urlStr.append(api_path);

        if(params != null && params.size() > 0){
            Iterator<String> paramsKey = params.keySet().iterator();

            while (paramsKey.hasNext()){

                String key = paramsKey.next();
                Object data = params.get(key);

                if(data != null && data.toString().length() > 0){

                    if(isFirst){
                        urlStr.append(key).append("=").append(URLEncoder.encode(data.toString(), "utf-8"));
                        isFirst = false;
                    }
                    else{
                        urlStr.append("&").append(key).append("=").append(URLEncoder.encode(data.toString(), "utf-8"));
                    }

                }
            }

        }

        return new URL(urlStr.toString());
    }
}
