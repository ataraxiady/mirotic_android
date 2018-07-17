package com.prograpy.app2.appdev2.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prograpy.app2.appdev2.network.HttpRequest;
import com.prograpy.app2.appdev2.network.response.result.JoinResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 회원 가입을 요청하는 task
 * 형식은 POST이며 Path 정보는 addUserInfo
 * 회원에 대한 데이터를 전송해줘야 한다.
 *
 *
 * 서버에서 내려주는 결과값 json 형식은
 * { "success" : (true/false), "error" : (각 상황에 따른 메세지) }
 *
 */
public class JoinTask extends AsyncTask<String, Integer, JoinResult> {

    // 각 인터페이스가 구현된 객체를 셋팅받을 핸들러 객체
    private JoinTaskResultHandler handler;


    /**
     * task를 호출한 쪽에 결과를 전달해주기 위한 핸들러 인터페이스
     *
     * 인터페이스 란?
     * 인터페이스는 함수의 내용은 선언해두지 않고 함수의 반환형, 이름, 파라미터들만
     * 선언한다. 그리고 해당 인터페이스를 구현할 쪽에서 인터페이스 함수들의 내용을
     * 각자가 필요한 부분에 맞게 구현하기 위한 기술
     */
    public interface JoinTaskResultHandler{
        /**
         * 성공에 대한 함수
         * @param result 서버 결과값
         */
        public void onSuccessTask(JoinResult result);

        /**
         * 실패에 대한 함수
         */
        public void onFailTask();

        /**
         * 취소에 대한 함수
         */
        public void onCancelTask();
    }


    /**
     * 생성자 함수로써 Task를 생성할때 구현해놓은 인터페이스에 대한 내용도 셋팅해준다
     * @param handler
     */
    public JoinTask(JoinTaskResultHandler handler){

        // 구현된 인터페이스 내용을 JoinTask에 선언된 핸들러 객체에서 셋팅해준다.
        this.handler = handler;
    }


    /**
     * 백그라운드 작업하기 전에 호출되는 함수
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    /**
     * 백그라운드 작업 시작 할때 호출되는 함수
     * @param strings 넘겨준 파라미터들
     * @return task 요청 결과
     */
    @Override
    protected JoinResult doInBackground(String... strings) {

        // 넘겨받은 파라미터를 하나하나 가져와서 세팅한다
        String path = strings[0];
        String name = strings[1];
        String gender = strings[2];
        int age = Integer.parseInt(strings[3]);
        String area = strings[4];
        String profileimage = strings[5];
        int bh_number_1 = Integer.parseInt(strings[6]);
        int bh_number_2 = Integer.parseInt(strings[7]);
        int bh_number_3 = Integer.parseInt(strings[8]);
        int sh_number_1 = Integer.parseInt(strings[9]);
        int sh_number_2 = Integer.parseInt(strings[10]);
        int sh_number_3 = Integer.parseInt(strings[11]);
        String kakaoKey = strings[12];
        String id = strings[13];
        String password = strings[14];
        String fcmKey = strings[14];


        // 현재 task의 return 값이 JoinResult 클래스이므로 결과를 반환해줄 객체를 만든다
        JoinResult result = null;


        // 서버에 넘겨줄 데이터를 셋팅해주는 Map 객체로
        // String => key 값 형태 / Object => 해당 키값에 맞는 데이터
        // 형식으로 셋팅해준다
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("gender", gender);
        params.put("age", age);
        params.put("area", area);
        params.put("profileimage", profileimage);
        params.put("bh_number_1", bh_number_1);
        params.put("bh_number_2", bh_number_2);
        params.put("bh_number_3", bh_number_3);
        params.put("sh_number_1", sh_number_1);
        params.put("sh_number_2", sh_number_2);
        params.put("sh_number_3", sh_number_3);
        params.put("kakaoKey", kakaoKey);
        params.put("ID", id);
        params.put("password", password);
        params.put("fcmKey", fcmKey);


        // http로 서버에 요청을 위한 클래스 객체 생성
        HttpRequest request = new HttpRequest();


        try {
            // 해당 부분에서 수정되어야 할 것
            // 서버에 요청해야하는 부분이 POST인 경우 2번째 "" 파라미터에 POST로 변경
            // 서버에 요청해야하는 부분이 GET인 경우 2번째 "" 파라미터에 GET으로 변경
            // 3번째 파라미터는 서버로 넘겨줘야하는 데이터를 셋팅한 Map 객체를 넘긴다. 없으면 null
            String str = request.callRequestServer(path,  "POST", params);


            // 서버 결과 값 확인
            Log.d("http", "str > " + str);


            // String으로 넘어온 서버의 json 데이터를 Gson 라이브러리를 활용하여 파싱한다.
            Gson gson = new GsonBuilder().create();
            result = gson.fromJson(str, JoinResult.class);

        } catch (Exception e) {
            // 에러가 나면 이부분으로 들어오게 된다.

            //에러 내용 출력 함수
            e.printStackTrace();

            // 에러가 나면 null을 리턴한다
            return null;
        }

        // 결과 리턴
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
}

