package com.ly.mock.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 利用正则表达式判断字符串是否是数字
 * @param str
 * @return
 */
public class IsNumeric{

    public static boolean is(String str) {
        Pattern pattern=Pattern.compile("[0-9]*");
        Matcher isNum=pattern.matcher(str);
        if(!isNum.matches()){
            return false;
        }
        return true;
    }
}
