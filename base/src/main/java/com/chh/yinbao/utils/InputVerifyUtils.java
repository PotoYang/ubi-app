package com.chh.yinbao.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by potoyang on 2017/8/7.
 */

public class InputVerifyUtils {
    /**
     * 是否是邮箱地址
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 是否是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileNO(String mobile) {

        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobile);

        return m.matches();

    }

    /**
     * 是否是身份证号
     *
     * @param id
     * @return
     */
    public static boolean isId(String id) {
        if (id != null && id.length() >= 15 && id.length() <= 18) {
            if (id.length() == 18) {
                for (int i = 0; i < 17; i++) {
                    int chr = id.charAt(i);
                    if (chr < 48 || chr > 57) {
                        return false;
                    }
                }
                int chr = id.charAt(17);
                if (chr < 48 || chr > 57) {
                    if (!(id.charAt(17) + "").equalsIgnoreCase("X")) {
                        return false;
                    }
                }
            } else if (id.length() == 15) {
                for (int i = 0; i < 15; i++) {
                    int chr = id.charAt(i);
                    if (chr < 48 || chr > 57) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 验证是否是中文
     *
     * @param string
     * @return
     */
    public static boolean isChinese(String string) {
        String regex = "[\\u4E00-\\u9FA5]+";
        if (!TextUtils.isEmpty(string) && string.matches(regex)) {
            return true;
        }
        return false;
    }

    /**
     * 验证是否输入特殊符号
     */
    public static boolean isSymbol(String string) {
        String regex = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]";
        if (!TextUtils.isEmpty(string) && string.matches(regex)) {
            return true;
        }
        return false;
    }

    /**
     * 只能由大小写字母，数字组成。6-20位
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        String regex = "^[a-zA-Z0-9]{6,20}$";
        if (!TextUtils.isEmpty(pwd) && pwd.matches(regex)) {
            return true;
        }
        return false;
    }
}
