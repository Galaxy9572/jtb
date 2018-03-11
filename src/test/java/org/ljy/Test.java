package org.ljy;

import org.ljy.jtb.builder.ITableBuilder;
import org.ljy.jtb.builder.MySqlTableBuilderImpl;
import org.ljy.jtb.config.IPropertyConfigReader;
import org.ljy.jtb.config.PropertyConfigReaderImpl;
import org.ljy.jtb.config.domain.BaseConfig;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author ljy
 */
public class Test {
    public static void main(String[] args) throws IllegalAccessException, IOException, InvocationTargetException {

        IPropertyConfigReader propertyConfigReader = new PropertyConfigReaderImpl("jtb.properties");
        BaseConfig baseConfig = propertyConfigReader.readBaseConfig();
        ITableBuilder iTableBuilder = new MySqlTableBuilderImpl();
        iTableBuilder.build(baseConfig);

    }
}
