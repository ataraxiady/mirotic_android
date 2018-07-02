package com.prograpy.app2.appdev2.network;

import com.google.gson.Gson;

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

    public static String callRequestServer (String serverUrl, String api_path, String method, Map<String, Object> params) throws Exception{
        return startRequest(serverUrl, api_path, method, params);
    }

    public static String startRequest(String serverUrl, String api_path, String method, Map<String, Object> params) throws Exception{

        URL url = createUrl(serverUrl, api_path, params);

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

                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Length", Integer.toString(postJson.getBytes("utf-8").length));
                OutputStream os = connection.getOutputStream();
                os.write(postJson.getBytes("utf-8"));
                os.flush();
                os.close();
            }

        }catch (Exception e) {
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



    public static URL createUrl (String serverUrl, String api_path, Map<String, Object> params) throws MalformedURLException, UnsupportedEncodingException {

        boolean isFirst = true;

        StringBuffer urlStr = new StringBuffer();
        urlStr.append(serverUrl);
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
