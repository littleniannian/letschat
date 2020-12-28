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
    private int tcpPort;

    @Value("${boss.thread.count}")
    private int bossCount;

    @Value("${worker.thread.count}")
    private int workerCount;

    @Value("${so.keepalive}")
    private boolean keepAlive;

    @Value("${so.backlog}")
    private int backlog;

}
