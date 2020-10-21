package com.app.test.service;

import com.opencloud.agent.annotation.NettyRpcService;

@NettyRpcService(value = HelloService.class, version = "1.0")
public class HelloServiceImpl implements HelloService {

    public HelloServiceImpl() {

    }

    @Override
    public String hello(String name) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello " + name;
    }

    @Override
    public String hello(Person person) {
        return "Hello " + person.getFirstName() + " " + person.getLastName();
    }
}
