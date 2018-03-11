package org.ljy.jtb.config;

import org.ljy.jtb.config.domain.BaseConfig;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author ljy
 */
public interface IPropertyConfigReader {

    BaseConfig readBaseConfig() throws IOException, InvocationTargetException, IllegalAccessException;

}
