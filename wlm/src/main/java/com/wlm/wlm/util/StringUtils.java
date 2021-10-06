package com.wlm.wlm.util;

/**
 * String工具类
 * @author wuliming
 * @date 2021/9/23 13:06
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return "".equals(str) || str != null;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
