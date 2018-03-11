package org.ljy.jtb.annotations;

import org.ljy.jtb.constants.Charset;

import java.lang.annotation.*;
import java.sql.JDBCType;

/**
 * @author ljy
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String columnName();
    String primaryKey() default "";
    JDBCType jdbcType() default JDBCType.NULL;
    boolean autoIncrement() default false;
    String comment() default "";
    String charSet() default Charset.UTF8;
}
