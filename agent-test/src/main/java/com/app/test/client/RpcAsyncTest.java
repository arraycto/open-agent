package com.app.test.client;

import com.app.test.service.HelloService;
import com.opencloud.agent.client.RpcClient;
import com.opencloud.agent.client.handler.RpcFuture;
import com.opencloud.agent.client.proxy.ProxyReflectFactory;
import com.opencloud.agent.client.proxy.RpcService;
import com.opencloud.config.Context;

import java.util.concurrent.TimeUnit;

import static com.opencloud.agent.BasicConfigurationConstants.CONFIG_REGISTER_ADDRESS;

/**
 * Created by luxiaoxun on 2016/3/16.
 */
public class RpcAsyncTest {
    public static void main(String[] args) throws InterruptedException {
        final RpcClient rpcClient = new RpcClient();
        Context context = new Context();
        context.put(CONFIG_REGISTER_ADDRESS, "10.10.40.40:2181");
        rpcClient.configure(context);
        int threadNum = 1;
        final int requestNum = 100;
        Thread[] threads = new Thread[threadNum];

        long startTime = System.currentTimeMillis();
        //benchmark for async call
        for (int i = 0; i < threadNum; ++i) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < requestNum; i++) {
                        try {
                            RpcService client = ProxyReflectFactory.createAsyncService(HelloService.class, "2.0");
                            RpcFuture helloFuture = client.call("hello", Integer.toString(i));
                            String result = (String) helloFuture.get(3000, TimeUnit.MILLISECONDS);
                            if (!result.equals("Hi " + i)) {
                                System.out.println("error = " + result);
                            } else {
                                System.out.println("result = " + result);
                            }
                            try {
                                Thread.sleep(5 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        long timeCost = (System.currentTimeMillis() - startTime);
        String msg = String.format("Async call total-time-cost:%sms, req/s=%s", timeCost, ((double) (requestNum * threadNum)) / timeCost * 1000);
        System.out.println(msg);

        rpcClient.stop();

    }
}
