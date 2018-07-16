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
    private static final String KEY_AUTO_LOGIN       = "auto_login";
    private static final String KEY_LOGIN_SUCCESS       = "login_success";

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


    /**
     * 사용자 비번 저장
     * @param userPw
     */
    public static void setKeyUserPw(String userPw){

        if(mEditor != null){
            mEditor.putString(KEY_USER_PW, userPw);
            mEditor.commit();
        }
    }

    /**
     * 사용자 비번 반환
     * @return
     */
    public static String getKeyUserPw(){

        String userPw = "";

        if(mPreferences != null){
            userPw = mPreferences.getString(KEY_USER_PW,  userPw);
        }

        return userPw;
    }


    /**
     * 자동로그인 여부 저장
     * @param isAutoLogin
     */
    public static void setKeyAutoLogin(boolean isAutoLogin){

        if(mEditor != null){
            mEditor.putBoolean(KEY_AUTO_LOGIN, isAutoLogin);
            mEditor.commit();
        }

    }


    /**
     * 자동 로그인 여부 반환
     * @return
     */
    public static boolean getKeyAutoLogin(){

        boolean isAutoLogin = false;

        if(mPreferences != null){
            isAutoLogin = mPreferences.getBoolean(KEY_AUTO_LOGIN, isAutoLogin);
        }

        return isAutoLogin;
    }



    /**
     * 로그인 여부 저장
     * @param isLogin
     */
    public static void setKeyLoginSuccess(boolean isLogin){

        if(mEditor != null){
            mEditor.putBoolean(KEY_LOGIN_SUCCESS, isLogin);
            mEditor.commit();
        }

    }


    /**
     * 로그인 여부 반환
     * @return
     */
    public static boolean getKeyLoginSuccess(){

        boolean isLogin = false;

        if(mPreferences != null){
            isLogin = mPreferences.getBoolean(KEY_LOGIN_SUCCESS, isLogin);
        }

        return isLogin;
    }
}