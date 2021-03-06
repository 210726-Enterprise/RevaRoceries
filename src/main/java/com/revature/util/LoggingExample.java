package com.revature.util;

import org.apache.log4j.*;

public class LoggingExample {

    final static Logger logger = Logger.getLogger(LoggingExample.class);

    public static void main(String[] args){
        /* Log4J Logging Levels - Levels add on to each other
            TRACE - General use information about the application
            DEBUG - More specific than trace, used for debugging
            INFO - Informational messages about the application.
            WARN - A warning for potential issues in the application
            ERROR - A more severe set of messages related to errors in the application
            FATAL - The most severe issues in the application should be logged at this level
         */

        PatternLayout layout = new PatternLayout("%n%p - %d - %C{1} - %m");

        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setThreshold(Level.DEBUG);
        consoleAppender.setLayout(layout);
        consoleAppender.activateOptions();
        Logger.getRootLogger().addAppender(consoleAppender);


        DailyRollingFileAppender rollingFileAppender = new DailyRollingFileAppender();
        rollingFileAppender.setFile("src/main/logs/daily/app.log");
        rollingFileAppender.setThreshold(Level.ALL);
        rollingFileAppender.setDatePattern("'.'yyyy-MM-dd-HH-mm");
        rollingFileAppender.setLayout(layout);
        rollingFileAppender.activateOptions();
        Logger.getRootLogger().addAppender(rollingFileAppender);


        FileAppender fileAppender = new FileAppender();
        fileAppender.setThreshold(Level.WARN);
        fileAppender.setLayout(layout);
        fileAppender.setFile("src/main/logs/issues.txt");
        fileAppender.setAppend(false);
        fileAppender.activateOptions();
        Logger.getRootLogger().addAppender(fileAppender);

        /* Layout patterns
            %m - print the user supplied message during the logging event
            %C - print the class that created the logging event HINT: use C{1} -> get the simple name
            %d - output the date of the logging event
            %p - output the priority (level) of the logging event
            %n - new line
         */

        logger.debug("This is a debug");
        logger.info("This is some info");
        logger.error("This is an error");

        try{
            // some code

            throw new Exception("This should log as an error");
        } catch (Exception e){
            logger.error("catch block",e);
        }

    }
}
