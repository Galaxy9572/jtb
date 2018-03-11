package org.ljy.jtb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ljy
 */
public class LogUtil {
    private static Logger logger = LoggerFactory.getLogger("jtb");

    public static void debug(String msg){
        logger.debug(msg);
    }

    public static void debug(String msg,Object... object){
        logger.debug(msg,object);
    }

    public static void info(String msg){
        logger.info(msg);
    }

    public static void info(String msg,Object... object){
        logger.info(msg,object);
    }

    public static void warn(String msg){
        logger.warn(msg);
    }

    public static void warn(String msg,Object... object){
        logger.warn(msg,object);
    }

    public static void error(String msg){
        logger.error(msg);
    }

    public static void error(String msg,Object... object){
        logger.error(msg,object);
    }
}
