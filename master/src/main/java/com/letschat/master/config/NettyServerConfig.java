package com.letschat.master.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author jarvis.yuan
 * @version 1.0.0
 * @ClassName NettyServerConfig.java
 * @createTime 2020年12月28日 14:26:00
 */
@Configuration
public class NettyServerConfig {
    @Value("${tcp.port}")
    public int tcpPort;

    @Value("${so.keepalive}")
    public boolean keepAlive;

    @Value("${so.backlog}")
    public int backlog;

}
