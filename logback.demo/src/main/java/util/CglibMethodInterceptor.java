package util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.spi.FilterReply;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by madali on 2019/4/26 11:38
 */
public class CglibMethodInterceptor {

    private Object target;
    private String logFileName;

    private ch.qos.logback.classic.Logger debugLog;
    private ch.qos.logback.classic.Logger infoLog;
    private ch.qos.logback.classic.Logger warnLog;
    private ch.qos.logback.classic.Logger errorLog;

    public CglibMethodInterceptor(Object target) {
        this.target = target;
    }

    public CglibMethodInterceptor(Object target, String logFileName) {
        this.target = target;
        this.logFileName = logFileName;
    }

    public Object createProxy() {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(Logger.class);
        //设置回调函数，对Logger中每个方法进行拦截
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {

//            System.out.println("Before method: " + method.getName());

            //TODO  待优化：每次调用debug，info，warn，error方法前先配置appender，使用完后移除appender，性能低下
            FileAppender appender = getAppender((Logger) target, method);

            //将appender加至Logger上
            doBefore(appender, method);

            //参数1仍为外部声明的源对象，且Method为JDK的Method反射
            Object object = method.invoke(target, args);

//            System.out.println("After method: " + method.getName());

            //将appender从Logger上移除
            doAfter(appender, method);

            return object;
        });

        return enhancer.create();
    }

    private FileAppender getAppender(Logger logger, Method method) {
        FileAppender<ILoggingEvent> appender = new FileAppender<>();

        Level level = null;
        if ("debug".equals(method.getName())) {
            level = Level.DEBUG;
            debugLog = (ch.qos.logback.classic.Logger) logger;
        } else if ("info".equals(method.getName())) {
            level = Level.INFO;
            infoLog = (ch.qos.logback.classic.Logger) logger;
        } else if ("warn".equals(method.getName())) {
            level = Level.WARN;
            warnLog = (ch.qos.logback.classic.Logger) logger;
        } else if ("error".equals(method.getName())) {
            level = Level.ERROR;
            errorLog = (ch.qos.logback.classic.Logger) logger;
        }

        String[] array = logger.getName().split("\\.");
        String catalog = array[array.length - 1];

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%date{dd-MM-yyyy HH:mm:ss.SSS, GMT} GMT [%thread] [%myFileAndLineConverter] - %msg%n");
        encoder.start();

        LevelFilter levelFilter = new LevelFilter();
        levelFilter.setLevel(level);
        levelFilter.setContext(loggerContext);
        levelFilter.setOnMatch(FilterReply.ACCEPT);
        levelFilter.setOnMismatch(FilterReply.DENY);
        levelFilter.start();

        appender.setEncoder(encoder);
        appender.addFilter(levelFilter);
        appender.setContext(loggerContext);

        if (logFileName == null) {
            appender.setFile("log/" + level + "/" + catalog + "/" + catalog + "_" + level + ".log");
        } else {
            appender.setFile("log/" + level + "/" + catalog + "/" + logFileName + "_" + level + ".log");
        }

        appender.start();

        return appender;
    }

    private void doBefore(FileAppender<ILoggingEvent> appender, Method method) {
        if ("debug".equals(method.getName())) {
            if (debugLog != null) debugLog.addAppender(appender);
        } else if ("info".equals(method.getName())) {
            if (infoLog != null) infoLog.addAppender(appender);
        } else if ("warn".equals(method.getName())) {
            if (warnLog != null) warnLog.addAppender(appender);
        } else if ("error".equals(method.getName())) {
            if (errorLog != null) errorLog.addAppender(appender);
        }
    }

    private void doAfter(FileAppender<ILoggingEvent> appender, Method method) {
        if ("debug".equals(method.getName())) {
            if (debugLog != null) debugLog.detachAppender(appender);
        } else if ("info".equals(method.getName())) {
            if (infoLog != null) infoLog.detachAppender(appender);
        } else if ("warn".equals(method.getName())) {
            if (warnLog != null) warnLog.detachAppender(appender);
        } else if ("error".equals(method.getName())) {
            if (errorLog != null) errorLog.detachAppender(appender);
        }
    }

}
