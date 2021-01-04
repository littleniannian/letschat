package com.letschat.master.client;

import com.letschat.master.config.NettyServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName NettyClient.java
 * @createTime 2021年01月04日 17:40:00
 */
@Slf4j
@Component
public class NettyClient {
    private static final Integer RECONNECT_SECONDS = 20;

    private final NettyServerConfig config;

    public NettyClient(NettyServerConfig config) {
        this.config = config;
    }
}
