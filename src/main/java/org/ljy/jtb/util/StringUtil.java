package org.ljy.jtb.util;

/**
 * @author ljy
 */
public class StringUtil {
    public static boolean isBlank(String string) {
        return string == null || string.length() == 0 || "".equals(string.trim());
    }
}
