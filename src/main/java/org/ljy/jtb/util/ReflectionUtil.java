package org.ljy.jtb.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ljy
 */
public class ReflectionUtil {

    public static Map<String,Method> getAllSetters(Class<?> clazz){
        Field[] declaredFields = clazz.getDeclaredFields();
        if (declaredFields.length == 0) {
            return null;
        }
        Map<String,Method> methodMap = new HashMap<>(declaredFields.length);
        StringBuilder sb = new StringBuilder();
        Class[] parameterTypes = new Class[1];
        String fieldName;
        Method method;
        for (Field declaredField : declaredFields) {
            parameterTypes[0] = declaredField.getType();
            fieldName = declaredField.getName();
            sb.append("set");
            sb.append(fieldName.substring(0, 1).toUpperCase());
            sb.append(fieldName.substring(1));
            try {
                method = clazz.getMethod(sb.toString(), parameterTypes[0]);
                methodMap.put(fieldName, method);
                sb.delete(0, sb.length());
            } catch (NoSuchMethodException e) {
                LogUtil.error("No Such Method", e);
            }
        }
        return methodMap;
    }

}
