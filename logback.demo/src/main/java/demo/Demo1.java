package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madali on 2019/4/26 11:23
 */
public class Demo1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo1.class);

    public static void main(String[] args) {
        LOGGER.debug("logback测试-debug");
        LOGGER.info("logback测试-info");
        LOGGER.warn("logback测试-warn");
        LOGGER.error("logback测试-error");
    }

}
