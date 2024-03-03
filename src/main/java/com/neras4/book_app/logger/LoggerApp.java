package com.neras4.book_app.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerApp {
    private final String className;
    private Logger traceLogger;
    private Logger debugLogger;
    private Logger infoLogger;
    private Logger warnLogger;
    private Logger errorLogger;

    public LoggerApp(String className) {
        this.className = className;
        initializeLoggers();
    }

    public void logs() {
        traceLogger.trace("lala");
        debugLogger.debug("Debug message");
        infoLogger.info("Info message");
        warnLogger.warn("Warn message");
        errorLogger.error("Error message");
    }

    private void initializeLoggers() {
        System.setProperty("customLoggerName", className);

        traceLogger = LoggerFactory.getLogger(System.getProperty("customLoggerName"));
        debugLogger = LoggerFactory.getLogger(System.getProperty("customLoggerName") + ".debug");
        infoLogger = LoggerFactory.getLogger(System.getProperty("customLoggerName") + ".info");
        warnLogger = LoggerFactory.getLogger(System.getProperty("customLoggerName") + ".warn");
        errorLogger = LoggerFactory.getLogger(System.getProperty("customLoggerName") + ".error");
    }

}
