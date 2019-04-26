package demo;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * Created by madali on 2019/4/26 11:29
 */
public class Demo2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo2.class);

    static {
        // 指定logback.xml的路径
        URL url = Demo2.class.getResource("/logback2.xml");

        File logbackFile;
        try {
            logbackFile = new File(url.toURI());
            System.out.println("当前使用的logback.xml是:" + logbackFile.getPath());
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(loggerContext);
            loggerContext.reset();
            try {
                configurator.doConfigure(logbackFile);
            } catch (JoranException e) {
                System.exit(-1);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        t1();
    }

    private static void t1() {
        LOGGER.debug("logback测试-debug");
        LOGGER.info("logback测试-info");
        LOGGER.warn("logback测试-warn");
        LOGGER.error("logback测试-error");
    }

    /**
     * logback支持异步记录日志，这样可加快程序的主流程处理速度，提高接口的qps。
     * logback异步记录日志的原理，也是使用一个缓冲队列，当缓冲数量到一定阀值时，才把日志写到文件里。
     * 需要在logback.xml中配置异步输出appender
     */
    private static void t2() {
        int messageSize = 500000;
        int threadSize = 50;
        final int everySize = messageSize / threadSize;

        final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        long startTime = System.currentTimeMillis();
        for (int ts = 0; ts < threadSize; ts++) {
            new Thread(() -> {
                for (int es = 0; es < everySize; es++) {
                    LOGGER.info(Thread.currentThread().getName() + "...es:{}.", es);
                }
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("logback:messageSize = " + messageSize + ",threadSize = " + threadSize + ",costTime = " + (endTime - startTime) + "ms");
    }

}
