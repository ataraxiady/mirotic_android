package com.prograpy.app2.appdev2.network.response;

/**
 * 서버에 요청할 API path 정보를 나열해놓은 클래스
 * 필요한 path에 따라서 값을 입력하면 됨
 */
public class ApiValue {

    // 회원가입 요청
    public static final String API_JOIN = "/addUserInfo";

    public static final String APT_LIKEDISLIKE = "/sendLikeInfo";

    public static final String APT_GET_MATCHING_INFO = "/getTestMatchingInfo";
    public static final String APT_GET_MY_INFO = "/getMyInfo";

}
