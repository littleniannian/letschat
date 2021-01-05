package com.letschat.master.client.controller;

import com.letschat.master.client.NettyClient;
import com.letschat.master.common.Invocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName HomeController.java
 * @createTime 2021年01月05日 16:05:00
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class HomeController {

    private final NettyClient nettyClient;

    public HomeController(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @PostMapping("mock")
    public String mock(String type,String message){
        Invocation invocation = new Invocation(type,message);
        log.info("invocation [{}]",invocation);
        nettyClient.send(invocation);
        return "success";
    }
}
