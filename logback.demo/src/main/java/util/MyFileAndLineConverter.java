package util;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Created by madali on 2019/4/26 11:38
 */
public class MyFileAndLineConverter extends ClassicConverter {

    private String getFullyQualifiedName(ILoggingEvent le) {
        StackTraceElement[] cda = le.getCallerData();

        String[] array = le.getLoggerName().split("\\.");
        String fileName = array[array.length - 1] + ".java";

        if (cda != null && cda.length > 0) {
            return fileName;
        } else {
            return CallerData.NA;
        }
    }

    private String getLineNumber(ILoggingEvent le) {
        StackTraceElement[] cda = le.getCallerData();

        String[] array = le.getLoggerName().split("\\.");
        String fileName = array[array.length - 1] + ".java";

        if (cda != null && cda.length > 0) {
            for (StackTraceElement e : cda) {
                if (fileName.equals(e.getFileName())) {
                    return Integer.toString(e.getLineNumber());
                }
            }
            return Integer.toString(cda[0].getLineNumber());
        } else {
            return CallerData.NA;
        }
    }

    @Override
    public String convert(ILoggingEvent le) {
        //log所在的类的类名
        String fileName = getFullyQualifiedName(le);
        //log所在的类的行数
        String line = getLineNumber(le);
        return fileName + ": " + line;
    }
}
