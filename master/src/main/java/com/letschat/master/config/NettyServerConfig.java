package com.letschat.master.config;

import com.letschat.master.common.dispatcher.MessageDispatcher;
import com.letschat.master.common.dispatcher.MessageHandlerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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

    @Value("${server.host}")
    private String serverHost;

    @Bean
    public MessageDispatcher messageDispatcher(){
        return new MessageDispatcher();
    }

    @Bean
    public MessageHandlerContainer messageHandlerContainer(){
        return new MessageHandlerContainer();
    }

}
