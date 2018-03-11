package org.ljy.jtb.annotations;

import org.ljy.jtb.constants.IndexMethod;
import org.ljy.jtb.constants.IndexType;

import java.lang.annotation.*;

/**
 * @author ljy
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Index {
    String indexName() default "";
    String indexType() default IndexType.NORMAL;
    String method() default IndexMethod.BTREE;
}
