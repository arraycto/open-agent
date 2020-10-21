package com.opencloud.agent.sink;

import com.opencloud.agent.CounterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class PollableParserRunner extends ParserRunner {
    private static final Logger logger = LoggerFactory.getLogger(PollableParserRunner.class);
    private static final long BACKOFF_SLEEP_INCREMENT = 1000;
    private static final long MAX_BACKOFF_SLEEP = 5000;

    private AtomicBoolean shouldStop;

    private CounterGroup counterGroup;
    private PollingRunner runner;
    private Thread runnerThread;

    public PollableParserRunner() {
        super();
        super.setPolicy(new EventParserProcessor());
        shouldStop = new AtomicBoolean();
        counterGroup = new CounterGroup();
    }

    @Override
    public void start() {
        super.start();
        ParserProcessor parser = getPolicy();
        parser.start();
        runner = new PollingRunner();
        runner.shouldStop = shouldStop;
        runner.counterGroup = counterGroup;
        runner.policy = parser;

        runnerThread = new Thread(runner);
        runnerThread.setName(getClass().getSimpleName() + "-" + parser.getClass().getSimpleName());
        runnerThread.start();
    }

    @Override
    public void stop() {
        super.stop();
        if (runner != null) {
            runner.shouldStop.set(true);
        }
        try {
            if (runnerThread != null) {
                runnerThread.interrupt();
                runnerThread.join(5000);
            }
        } catch (InterruptedException e) {
            logger.warn("Interrupted while waiting for polling runner to stop. Please report this.", e);
            Thread.currentThread().interrupt();
        }
        AbstractParserProcessor policy = getPolicy();
        if (policy != null) {
            policy.stop();
        }
    }

    @Override
    public void put(SourceMessage sourceMessage) {
        AbstractParserProcessor parser = getPolicy();
        parser.put(sourceMessage);
    }

    public static class PollingRunner implements Runnable {
        private ParserProcessor policy;
        private AtomicBoolean shouldStop;
        private CounterGroup counterGroup;

        @Override
        public void run() {
            while (!shouldStop.get()) {
                try {
                    if (policy.process().equals(ParserProcessor.Status.BACKOFF)) {
                        counterGroup.incrementAndGet("runner.backoffs");

                        Thread.sleep(Math.min(counterGroup.incrementAndGet("runner.backoffs.consecutive") * BACKOFF_SLEEP_INCREMENT, MAX_BACKOFF_SLEEP));
                    } else {
                        counterGroup.set("runner.backoffs.consecutive", 0L);
                    }
                } catch (InterruptedException e) {
                    logger.debug("Interrupted while processing an event. Exiting.");
                    counterGroup.incrementAndGet("runner.interruptions");
                } catch (Exception e) {
                    logger.error("Unable to deliver event. Exception follows.", e);
                    try {
                        Thread.sleep(MAX_BACKOFF_SLEEP);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            logger.debug("Polling runner exiting. Metrics:{}", counterGroup);
        }
    }
}
