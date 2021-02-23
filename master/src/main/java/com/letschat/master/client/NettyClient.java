package com.letschat.master.client;

import com.letschat.master.common.Invocation;
import com.letschat.master.config.NettyServerConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private NettyClientHandlerInitializer nettyClientHandlerInitializer;
    @Autowired
    private NettyServerConfig config;

    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    private volatile Channel channel;

    @PostConstruct
    public void start(){
        // 创建 Bootstrap 对象，用于 Netty Client 启动
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(config.serverHost,config.tcpPort)
                .option(ChannelOption.SO_KEEPALIVE,true) // 实现TCP层级的心跳保活机制
                .option(ChannelOption.TCP_NODELAY,true) // 允许较小的数据包发送，降低延迟
                .handler(nettyClientHandlerInitializer);
        bootstrap.connect().addListener((ChannelFutureListener) future -> {
            // 连接失败
            if (!future.isSuccess()) {
                log.error("[start][Netty Client 连接服务器[{}:{}] 失败]", config.serverHost, config.tcpPort);
                reconnect();
                return;
            }
            // 连接成功
            channel = future.channel();
            log.info("[start][Netty Client 连接服务器[{}:{}] 成功]", config.serverHost, config.tcpPort);
        });
    }

    @PreDestroy
    public void shutDown(){
        // 关闭NettyClient
        if(channel!=null){
            channel.close();
        }
        eventLoopGroup.shutdownGracefully();
    }

    public void send(Invocation invocation){
        if(channel == null){
            log.error("[send][连接不存在]");
            return;
        }
        if(!channel.isActive()){
            log.error("[send][连接[{}]未激活]",channel.id());
        }
        channel.writeAndFlush(invocation);
    }

    /**
     * 通过调用 EventLoop 提供的 #schedule(Runnable command, long delay, TimeUnit unit) 方法，实现定时逻辑。
     * 而在内部的具体逻辑，调用 NettyClient 的 #start() 方法，发起连接 Netty 服务端。
     */
    public void reconnect(){
        // 实现定时调用
        eventLoopGroup.schedule(()->{
            log.info("[reconnect][开始重连]");
            start();
        },RECONNECT_SECONDS, TimeUnit.SECONDS);
        log.info("[reconnect][{} 秒后将发起重连]", RECONNECT_SECONDS);
    }
}
