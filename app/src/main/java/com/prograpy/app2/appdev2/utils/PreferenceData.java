package com.prograpy.app2.appdev2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * app에 데이터를 저장할때 쓰이는 Perference
 */
public class PreferenceData
{

    private static final String TAG            = "PerferenceData";
    /**
     * preference 키값
     */
    private static final String KEY_USER_ID       = "user_id";
    private static final String KEY_USER_PW       = "user_pw";
    private static final String KEY_USER_GENDER      = "user_gender";
    private static final String KEY_USER_LOGIN_SUCCESS       = "login_success";
    private static final String KEY_USER_IMAGE       = "user_image";
    private static final String KEY_FCM_TOKEN       = "fcm_token";
    private static final String KEY_TODAY_INTRODUCE = "today_introduce";
    private static final String KEY_LOGIN_TIME = "login_time";

    private static SharedPreferences mPreferences; // Preference 객체
    private static Editor            mEditor; // Preference에 값을 수정하는 editor 객체

    /**
     * 공유자원을 초기화 한다
     *
     * @param con
     *         - 사용하는 Context
     * @throws Exception
     */
    public static void init(Context con)
    {
        if (mPreferences == null)
            mPreferences = PreferenceManager.getDefaultSharedPreferences(con);        //preference 객체 생성
        if (mPreferences != null)
            mEditor = mPreferences.edit();                            //editor 객체 생성
    }


    /**
     * 공유자원을 정리한다.
     */
    public static void release()
    {
        if (mEditor != null)
            mEditor.commit();
        mPreferences = null;                //preference 정리
        mEditor = null;                        //preference 에디터 정리
    }


    /**
     * 사용자 아이디 저장
     * @param userId
     */
    public static void setKeyUserId(String userId){

        if(mEditor != null){
            mEditor.putString(KEY_USER_ID, userId);
            mEditor.commit();
        }
    }

    /**
     * 사용자 아이디 반환
     * @return
     */
    public static String getKeyUserId(){

        String userId = "";

        if(mPreferences != null){
            userId = mPreferences.getString(KEY_USER_ID,  userId);
        }

        return userId;
    }

    public static void setKeyFcmToken(String token){

        if(mEditor != null){
            mEditor.putString(KEY_FCM_TOKEN, token);
            mEditor.commit();
        }
    }


    public static String getKeyFcmToken(){
        String token = "";

        if(mPreferences != null){
            token = mPreferences.getString(KEY_FCM_TOKEN, token);
        }

        return token;
    }


    public static void setKeyTodayIntroduce(boolean isOver){

        if(mEditor != null){
            mEditor.putBoolean(KEY_TODAY_INTRODUCE, isOver);
            mEditor.commit();
        }
    }


    public static boolean getKeyTodayIntroduce(){
        boolean isOver = false;

        if(mPreferences != null){
            isOver = mPreferences.getBoolean(KEY_TODAY_INTRODUCE, isOver);
        }

        return isOver;
    }


    public static void setKeyLoginTime(int curTime){

        if(mEditor != null){
            mEditor.putInt(KEY_LOGIN_TIME, curTime);
            mEditor.commit();
        }
    }

    public static int getKeyLoginTime(){
        int curTime = 0;

        if(mPreferences != null){
            curTime = mPreferences.getInt(KEY_LOGIN_TIME, curTime);
        }

        return curTime;
    }

    public static void setKeyUserPw(String pw){

        if(mEditor != null){
            mEditor.putString(KEY_USER_PW, pw);
            mEditor.commit();
        }
    }


    public static String getKeyUserPw(){
        String pw = "";

        if(mPreferences != null){
            pw = mPreferences.getString(KEY_USER_PW, pw);
        }

        return pw;

    }


    public static void setKeyUserLoginSuccess(boolean isSuccess){
        if(mEditor != null){
            mEditor.putBoolean(KEY_USER_LOGIN_SUCCESS, isSuccess);
            mEditor.commit();
        }
    }


    public static boolean getKeyUserLoginSuccess(){

        boolean isSuccess = false;


        if(mPreferences != null){
            isSuccess = mPreferences.getBoolean(KEY_USER_LOGIN_SUCCESS, isSuccess);
        }

        return isSuccess;
    }

    public static void setKeyUserImage(String userImage){
        if(mEditor != null){
            mEditor.putString(KEY_USER_IMAGE, userImage);
            mEditor.commit();
        }
    }

    public static String getKeyUserImage(){
        String userImage = "";


        if(mPreferences != null){
            userImage = mPreferences.getString(KEY_USER_IMAGE, userImage);

        }

        return userImage;
    }

    public static void setKeyUserGender(String gender){

        if(mEditor != null){
            mEditor.putString(KEY_USER_GENDER, gender);
            mEditor.commit();
        }

    }

    public static String getKeyUserGender(){
        String gender = "";

        if(mPreferences != null){

            gender = mPreferences.getString(KEY_USER_GENDER, gender);

        }

        return gender;
    }

}