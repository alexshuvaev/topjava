package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class JUnitStopWatch extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(JUnitStopWatch.class);

    private static StringBuilder result = new StringBuilder();

    private static void logInfo(Description description, long nanos) {
        String testName = description.getMethodName();
        String duration = testName + " – " + TimeUnit.NANOSECONDS.toMillis(nanos) + "ms.\n";
        result.append(duration);
        log.debug("\n\n" + duration);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, nanos);
    }

    public static String getResult() {
        return result.toString();
    }
}
