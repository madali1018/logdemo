package demo;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by madali on 2019/6/25 17:00
 */
public class SpecialLogPathDemo {

    // SpecialLogPathDemo-log 将日志输出到指定路径
    private static final Logger logger = LoggerFactory.getLogger(SpecialLogPathDemo.class);

    public static void main(String[] args) {
        // log4j.xml路径
        String path1 = System.getProperty("user.dir") + "\\log4j.demo\\src\\main\\resources\\log4j.xml";
        String path2 = SpecialLogPathDemo.class.getClassLoader().getResource("log4j.xml").getPath();
        String path3 = "D:\\IdeaProjects\\madali1018\\logdemo\\log4j.demo\\src\\main\\resources\\log4j.xml";

        System.out.println(path1);
        System.out.println(path2);
        System.out.println(path3);

        // 指定程序加载的时候使用log4j.xml
        PropertyConfigurator.configure(path1);

        logger.info("111");
        logger.warn("222");
    }

}
