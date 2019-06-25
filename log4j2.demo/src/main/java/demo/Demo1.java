package demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by madali on 2019/4/26 11:04
 */
public class Demo1 {

    private static final Logger LOGGER = LogManager.getLogger(Demo1.class);

    public static void main(String[] args) {
        LOGGER.debug("log4j2测试-debug");
        LOGGER.info("log4j2测试-info");
        LOGGER.warn("log4j2测试-warn");
        LOGGER.error("log4j2测试-error");

        LOGGER.info("id,name:\t{}\t{}", 1, "房");
    }

}
