package com.opencloud.agent.concurrent;

import com.opencloud.agent.util.JvmTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.opencloud.agent.util.StackTraceUtil.stackTrace;

public abstract class AbstractRejectedExecutionHandler implements RejectedExecutionHandler {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractRejectedExecutionHandler.class);

    protected final String threadPoolName;
    private final AtomicBoolean dumpNeeded;
    private final String dumpPrefixName;

    public AbstractRejectedExecutionHandler(String threadPoolName, boolean dumpNeeded, String dumpPrefixName) {
        this.threadPoolName = threadPoolName;
        this.dumpNeeded = new AtomicBoolean(dumpNeeded);
        this.dumpPrefixName = dumpPrefixName;
    }

    public void dumpJvmInfoIfNeeded() {
        if (dumpNeeded.getAndSet(false)) {
            String now = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String name = threadPoolName + "_" + now;
            try (FileOutputStream fileOutput = new FileOutputStream(new File(dumpPrefixName + "_dump_" + name + ".log"))) {

                List<String> stacks = JvmTools.jStack();
                for (String s : stacks) {
                    fileOutput.write(s.getBytes(StandardCharsets.UTF_8));
                }

                List<String> memoryUsages = JvmTools.memoryUsage();
                for (String m : memoryUsages) {
                    fileOutput.write(m.getBytes(StandardCharsets.UTF_8));
                }

                if (JvmTools.memoryUsed() > 0.9) {
                    JvmTools.jMap(dumpPrefixName + "_dump_" + name + ".hprof", false);
                }
            } catch (Throwable t) {
                logger.error("Dump jvm info error: {}.", stackTrace(t));
            }
        }
    }
}
