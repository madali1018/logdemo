package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madali on 2019/4/26 11:37
 */
public class LogbakUtil {

    /**
     * 获取Logger的公共方法
     *
     * @param cls 要记录日志的类
     * @return
     */
    public static Logger getLogger(Class<?> cls) {
        return getLogger(cls, false);
    }

    /**
     * 获取Logger的公共方法
     *
     * @param cls        要记录日志的类
     * @param expandFlag 是否需要单独为该类记录log到对应的目录
     * @return
     */
    public static Logger getLogger(Class<?> cls, boolean expandFlag) {
        Logger target = LoggerFactory.getLogger(cls);

        //生成动态代理类
        if (expandFlag) {
            Object proxy = new CglibMethodInterceptor(target).createProxy();
            return (Logger) proxy;
        }

        return target;
    }

    /**
     * 获取Logger的公共方法
     *
     * @param cls         要记录日志的类
     * @param logFileName 该日志信息要记录到的目的文件的名字
     * @return
     */
    public static Logger getLogger(Class<?> cls, String logFileName) {
        Logger target = getLogger(cls, false);

        //生成动态代理类
        Object proxy = new CglibMethodInterceptor(target, logFileName).createProxy();
        return (Logger) proxy;
    }

}
