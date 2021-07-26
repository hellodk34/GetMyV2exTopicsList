package com.hellodk.getv2exmytopics.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: hellodk
 * @description user cookies verify
 * @date: 7/26/2021 11:21 AM
 */

public class UserCookiesVerify {

    private final static UserCookiesVerify USER_COOKIES_VERIFY = new UserCookiesVerify();

    /**
     * @param * @param :
     * @return com.hellodk.getv2exmytopics.config.UserCookiesVerify
     * @author hellodk
     * @description 获取唯一实例
     * @date 11:31 AM
     */
    public static UserCookiesVerify getInstance() {
        return USER_COOKIES_VERIFY;
    }

    /**
     * @author hellodk
     * @description 用户 cookies V2 V2EX_TAB PB3_SESSION V2EX_LANG 这四个应该就够了，目前发现有些场景需要填写 V2EX_REFERRER 这个 cookie
     * 另外需要注意的是 第一个填写的变量是 TOTAL_PAGE_NUMBER 总的页码数，可以登录之后查看 https://www.v2ex.com/my/topics 获取到。需要第一个填写，默认值是 1
     * @date 7/26/2021 11:26 AM
     * @param * @param null:
     * @return
     */
    public static int TOTAL_PAGE_NUMBER = 1;

    public static String A2 = "";

    public static String V2EX_TAB = "";

    public static String PB3_SESSION = "";

    public static String V2EX_LANG = "";

    public static String V2EX_REFERRER = "";

    @Override
    public String toString() {
        return super.toString();
    }

    public UserCookiesVerify() {
    }

    public static void verify(int totalPageNumber, String a2, String v2exTab, String pb3Session, String v2exLang) {
        UserCookiesVerify.TOTAL_PAGE_NUMBER = totalPageNumber;
        UserCookiesVerify.A2 = a2;
        UserCookiesVerify.V2EX_TAB = v2exTab;
        UserCookiesVerify.PB3_SESSION = pb3Session;
        UserCookiesVerify.V2EX_LANG = v2exLang;
    }


    public static void verify(int totalPageNumber, String a2, String v2exTab, String pb3Session, String v2exLang, String v2exReferrer) {
        UserCookiesVerify.TOTAL_PAGE_NUMBER = totalPageNumber;
        UserCookiesVerify.A2 = a2;
        UserCookiesVerify.V2EX_TAB = v2exTab;
        UserCookiesVerify.PB3_SESSION = pb3Session;
        UserCookiesVerify.V2EX_LANG = v2exLang;
        UserCookiesVerify.V2EX_REFERRER = v2exReferrer;
    }


}
