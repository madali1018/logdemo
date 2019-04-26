package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madali on 2019/4/26 11:00
 */
public class Demo1 {

    // myLog 将日志输出到指定路径
    private static final Logger myLog = LoggerFactory.getLogger("myLog");

    // logger 将日志输出到 项目主配置(控制台，文件等) 指定的路径
    private static final Logger logger = LoggerFactory.getLogger(Demo1.class);

    public static void main(String[] args) {
        myLog.info("info");
        myLog.warn("warn");

        logger.info("111");
        logger.warn("222");
    }

}
