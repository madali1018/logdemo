package util;

import org.slf4j.Logger;

/**
 * Created by madali on 2019/4/26 11:36
 */
public class Demo1 {

    private static final Logger LOGGER1 = LogbakUtil.getLogger(Demo1.class);
    private static final Logger LOGGER2 = LogbakUtil.getLogger(Demo1.class, "logtest");
    private static final Logger LOGGER3 = LogbakUtil.getLogger(Demo1.class, true);

    public static void main(String[] args) {
        System.out.println("------");
        LOGGER1.debug("logback测试1-debug");
        LOGGER1.info("logback测试1-info");
        LOGGER1.warn("logback测试1-warn");
        LOGGER1.error("logback测试1-error");

        System.out.println("------");
        LOGGER2.debug("logback测试2-debug");
        LOGGER2.info("logback测试2-info");
        LOGGER2.warn("logback测试2-warn");
        LOGGER2.error("logback测试2-error");

        System.out.println("------");
        LOGGER3.debug("logback测试3-debug");
        LOGGER3.info("logback测试3-info");
        LOGGER3.warn("logback测试3-warn");
        LOGGER3.error("logback测试3-error");
    }

}
