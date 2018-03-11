package org.ljy.jtb.config;

import org.ljy.jtb.config.domain.BaseConfig;
import org.ljy.jtb.util.LogUtil;
import org.ljy.jtb.util.ReflectionUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

/**
 * @author ljy
 */
public class PropertyConfigReaderImpl implements IPropertyConfigReader {

    private static Properties properties;
    private static Map<String,Method> baseConfigMethodsMap;
    private static Class<BaseConfig> baseConfigClass = BaseConfig.class;
    private static Field[] baseConfigFields;

    static {
        baseConfigFields = baseConfigClass.getDeclaredFields();
        baseConfigMethodsMap = ReflectionUtil.getAllSetters(baseConfigClass);
    }

    public PropertyConfigReaderImpl(String path) {
        properties = new Properties();
        try {
            InputStream in = PropertyConfigReaderImpl.class.getClassLoader().getResourceAsStream(path);
            properties.load(in);
        } catch (Exception e) {
            LogUtil.error("Load Properties failed",e);
        }
    }

    @Override
    public BaseConfig readBaseConfig() throws IOException, InvocationTargetException, IllegalAccessException {
        BaseConfig baseConfig = new BaseConfig();
        Method method;
        for (Field field : baseConfigFields) {
            String fieldName = field.getName();
            String property = properties.getProperty(fieldName);
            method = baseConfigMethodsMap.get(fieldName);
            method.invoke(baseConfig,property);
        }
        return baseConfig;
    }
}
