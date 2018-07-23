package com.prograpy.app2.appdev2.utils;

import java.text.DecimalFormat;

public class Utils {

    /**
     * 비밀번호 유효성 체크
     *
     * @param pw
     *         체크할 비밀번호
     * @return 유효성 여부
     */
    public static boolean isValidPw(String pw) {

        // 대문자가 안걸러져서 대문자는 별도로
        if(java.util.regex.Pattern.compile("^(.*[A-Z].*).$").matcher(pw).matches()){
            return false;
        }

        String stricterFilterString = "^((?=.*[0-9])(?=.*[a-z])).{8,16}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(stricterFilterString);
        java.util.regex.Matcher m = p.matcher(pw);
        return m.matches();
    }

    /**
     * 아이디 유효성 체크
     *
     * @param id
     *         체크할 아이디
     * @return 유효성 여부
     */
    public static boolean isValidId(String id) {

        // 대문자가 안걸러져서 대문자는 별도로
        if(java.util.regex.Pattern.compile("^(.*[A-Z].*).$").matcher(id).matches()){
            return false;
        }

        String stricterFilterString = "^((?=.*[0-9])(?=.*[a-z])|(?=.*[a-z])).{4,16}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(stricterFilterString);
        java.util.regex.Matcher m = p.matcher(id);
        return m.matches();
    }



    /**
     * 이름 유효성 체크
     * @param nickname 체크할 이름
     * @return 유효성 여부
     */
    public static boolean isValidName(String nickname) {
        String stricterFilterString = "^[ㄱ-힣\\s]*$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(stricterFilterString);
        java.util.regex.Matcher m = p.matcher(nickname);
        return m.matches();
    }

    /**
     * 이메일 유효성 체크
     *
     * @param email
     *         체크할 이메일
     * @return 유효성 여부
     */
    public static boolean isValidEmailAddress(String email) {
        boolean stricterFilter = true;
        String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
        String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
        String emailRegex = stricterFilter ? stricterFilterString : laxString;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(emailRegex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String moneyFormatToWon(int inputMoney) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");

        return decimalFormat.format(inputMoney);
    }

}
