package com.lcz.lcz_blog.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘传政
 * @date 2019-04-28 13:58
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class StringUtils {

    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    //座机
    public static boolean isFixedPhone(String string) {
        if (string == null)
            return false;
        //将点号全部替换为横线
        String targetStr = string.replaceAll("\\-", "");
        String regEx1 = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(targetStr);
        if (m.matches())
            return true;
        else
            return false;
    }

    public static boolean isMobilePhone(String string) {
        if (string == null)
            return false;
        String regEx1 = "^1[34578][0-9]{9}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    /**
     * 格式化为两位小数字符串
     *
     * @param d
     * @return
     */
    public static String format(Double d) {
        String s = String.format("%.2f", d);
        return s;
    }

    public static String formatBankAccount(String account) {
        String replaceAll = account.replaceAll("\\d{4}(?!$)", "$0  ");
        return replaceAll;
    }

    /**
     * 将手机号码18033339999 转换为 180****9999
     *
     * @param phoneNumber
     * @return
     */
    public static String changPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        if (phoneNumber.length() > 10) {
            String frontThreeString = phoneNumber.substring(0, 3);
            sb.append(frontThreeString);
            String substring = phoneNumber.substring(3, 7);
            String replace = substring.replace(substring, "****");
            sb.append(replace);
            String lastFourString = phoneNumber.substring(7, 11);
            sb.append(lastFourString);
            return sb.toString();
        } else {
            return "";
        }

    }

    /**
     * 分隔11位的手机号 185 0123 1486
     *
     * @param phoneNumber
     * @return
     */
    public static String splitPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return "";
        }
        if (phoneNumber.length() != 11) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        if (phoneNumber.length() > 10) {
            String frontThreeString = phoneNumber.substring(0, 3);
            sb.append(frontThreeString);
            sb.append(" ");
            String substring = phoneNumber.substring(3, 7);
            sb.append(substring);
            sb.append(" ");
            String lastFourString = phoneNumber.substring(7, 11);
            sb.append(lastFourString);
            return sb.toString();
        } else {
            return "";
        }

    }

    /**
     * 将身份证号130922199104206011 转换为 1***************1
     * 微信就是这样显示的
     *
     * @param idCardNumber
     * @return
     */
    public static String changIdCardNumber(String idCardNumber) {
        if (TextUtils.isEmpty(idCardNumber)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        if (idCardNumber.length() > 2) {
            String firstStr = idCardNumber.substring(0, 1);
            sb.append(firstStr);
            String substring = idCardNumber.substring(1, idCardNumber.length() - 1);
            StringBuffer sbXing = new StringBuffer();
            for (char c : substring.toCharArray()) {
                sbXing.append("*");
            }
            String replace = substring.replace(substring, sbXing);
            sb.append(replace);
            String lastStr = idCardNumber.substring(idCardNumber.length() - 1);
            sb.append(lastStr);
            return sb.toString();
        } else {
            return idCardNumber;
        }

    }

    public static int containCount(String all, String s) {
        int count = 0;
        while (all.contains(s)) {
            all = all.substring(all.indexOf(s) + 1);
            ++count;

        }
        return count;
    }
}
