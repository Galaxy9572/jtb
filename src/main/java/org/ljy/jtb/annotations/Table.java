package org.ljy.jtb.annotations;

import org.ljy.jtb.constants.Charset;
import org.ljy.jtb.constants.EngineType;

import java.lang.annotation.*;

/**
 * @author ljy
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String dbName();
    String tableName();
    String engineType() default EngineType.INNO_DB;
    String charSet() default Charset.UTF8;
}
