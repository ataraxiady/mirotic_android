package com.prograpy.app2.appdev2.network.response;

/**
 * 서버에 요청할 API path 정보를 나열해놓은 클래스
 * 필요한 path에 따라서 값을 입력하면 됨
 */
public class ApiValue {

    // 회원가입 요청
    public static final String API_JOIN = "/addUserInfo";

    public static final String API_LIKEDISLIKE = "/sendLikeInfo";

    public static final String API_GET_MATCHING_INFO = "/getTestMatchingInfo";
    public static final String API_GET_MY_INFO = "/getMyInfo";

    public static final String API_SEND_MSG = "/sendMessage";

    public static final String API_GET_MATCH_LIST = "/getMatchList";

    public static final String API_UPDATE_FCM_KEY = "/updateFcmKey";

    public static final String API_ModifyInfo = "/SendModifydata";

}
