package org.ljy.jtb.builder;

import org.ljy.jtb.config.domain.BaseConfig;

import java.util.List;

/**
 * @author ljy
 */
public interface ITableBuilder {

    List<String> build(BaseConfig baseConfig);

}
