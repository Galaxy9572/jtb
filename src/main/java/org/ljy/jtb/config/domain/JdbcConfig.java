package org.ljy.jtb.config.domain;

import lombok.Data;

/**
 * @author ljy
 */
@Data
public class JdbcConfig {

    private String driverClass;

    private String jdbcUrl;

    private String userName;

    private String password;

}
