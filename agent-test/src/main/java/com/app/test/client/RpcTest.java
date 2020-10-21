package com.app.test.client;

import com.app.test.service.HelloService;
import com.opencloud.agent.client.RpcClient;
import com.opencloud.agent.client.proxy.ProxyReflectFactory;
import com.opencloud.config.Context;

import static com.opencloud.agent.BasicConfigurationConstants.CONFIG_REGISTER_ADDRESS;

public class RpcTest {

    public static void main(String[] args) throws InterruptedException {

        final RpcClient rpcClient = new RpcClient();
        Context context = new Context();
        context.put(CONFIG_REGISTER_ADDRESS, "10.10.40.40:2181");
        rpcClient.configure(context);
        rpcClient.start();
        int threadNum = 1;
        final int requestNum = 50;
        Thread[] threads = new Thread[threadNum];

        long startTime = System.currentTimeMillis();
        //benchmark for sync call
        for (int i = 0; i < threadNum; ++i) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < requestNum; i1++) {
                    try {
                        final HelloService syncClient = ProxyReflectFactory.createService(HelloService.class, "1.0");
                        String result = syncClient.hello(Integer.toString(i1));
                        if (!result.equals("Hello " + i1)) {
                            System.out.println("error = " + result);
                        } else {
                            System.out.println("result = " + result);
                        }
                        try {
                            Thread.sleep(5 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        long timeCost = (System.currentTimeMillis() - startTime);
        String msg = String.format("Sync call total-time-cost:%sms, req/s=%s", timeCost, ((double) (requestNum * threadNum)) / timeCost * 1000);
        System.out.println(msg);

        rpcClient.stop();
    }

}
