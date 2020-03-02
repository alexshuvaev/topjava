package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class JUnitStopWatch extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(JUnitStopWatch.class);

    private static void logInfo(Description description, long nanos) {
        String testName = description.getMethodName();
        String air = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        log.debug("\n\n" + air + air +
                testName + " " + "\n" +
                "Duration: " + TimeUnit.NANOSECONDS.toMillis(nanos) + "ms.\n" +
                air + air + "\n\n");
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, nanos);
    }
}
