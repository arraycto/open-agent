package com.opencloud.agent.client.handler;

import com.opencloud.agent.codec.RpcResponse;
import com.opencloud.agent.codec.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * RPCFuture for async RPC call
 */
public class RpcFuture implements Future<Object> {
    private static final Logger logger = LoggerFactory.getLogger(RpcFuture.class);

    private Sync sync;
    private RpcRequest request;
    private RpcResponse response;
    private long startTime;

    public RpcFuture(RpcRequest request) {
        this.sync = new Sync();
        this.request = request;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public boolean isDone() {
        return sync.isDone();
    }

    @Override
    public Object get() {
        sync.acquire(1);
        if (this.response != null) {
            return this.response.getResult();
        } else {
            return null;
        }
    }

    /**
     * 另外，当client访问server
     * server 由于阻塞，则RpcFuture将一直阻塞LockSupport.park();
     * server Down机或重启，则RpcFuture将一直阻塞LockSupport.park();
     */
    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException {
        boolean success = sync.tryAcquireNanos(1, unit.toNanos(timeout));
        if (success) {
            if (this.response != null) {
                return this.response.getResult();
            } else {
                return null;
            }
        } else {
            throw new RuntimeException("Timeout exception. Request id: " + this.request.getRequestId()
                    + ". Request class name: " + this.request.getClassName()
                    + ". Request method: " + this.request.getMethodName());
        }
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    public void done(RpcResponse reponse) {
        this.response = reponse;
        sync.release(1);
        // Threshold
        long responseTime = System.currentTimeMillis() - startTime;
        long responseTimeThreshold = 5000;
        if (responseTime > responseTimeThreshold) {
            logger.warn("Service response time is too slow. Request id = " + reponse.getRequestId() + ". Response Time = " + responseTime + "ms");
        }
    }


    /**
     * 目前支持的作用在于countDownLatch
     */
    static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1L;

        //future status
        private final int done = 1;

        @Override
        protected boolean tryAcquire(int arg) {
            return getState() == done;
        }

        @Override
        protected boolean tryRelease(int arg) {
            int pending = 0;
            if (getState() == pending) {
                return compareAndSetState(pending, done);
            } else {
                return true;
            }
        }

        protected boolean isDone() {
            return getState() == done;
        }
    }
}
