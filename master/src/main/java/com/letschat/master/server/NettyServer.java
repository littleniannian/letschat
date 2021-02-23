package com.letschat.master.server;

import com.letschat.master.config.NettyServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName NettyServer.java
 * @createTime 2021年01月04日 14:24:00
 */
@Slf4j
@Component
public class NettyServer {
    /**
     * boss 线程组，用于服务端接受客户端的连接
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();

    /**
     * worker 线程组，用于服务端接受客户端的数据读写
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Channel channel;

    private final NettyServerConfig nettyServerConfig;
    private final NettyServerHandlerInitializer nettyServerHandlerInitializer;

    public NettyServer(NettyServerConfig nettyServerConfig, NettyServerHandlerInitializer nettyServerHandlerInitializer) {
        this.nettyServerConfig = nettyServerConfig;
        this.nettyServerHandlerInitializer = nettyServerHandlerInitializer;
    }

    @PostConstruct
    public void start() throws InterruptedException{
        // 创建 ServerBootstrap 对象，用于 Netty Server 启动
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 设置 ServerBootstrap 的各种属性
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class) // 指定 Channel 为服务端 NioServerSocketChannel
                .localAddress(new InetSocketAddress(nettyServerConfig.tcpPort)) // 设置 Netty Server 的端口
                .option(ChannelOption.SO_BACKLOG, 1024) // 服务端 accept 队列的大小
                .childOption(ChannelOption.SO_KEEPALIVE, true) // TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
                .childOption(ChannelOption.TCP_NODELAY, true) // 允许较小的数据包的发送，降低延迟
                .childHandler(nettyServerHandlerInitializer);
        // 绑定端口，并同步等待成功，即启动服务端
        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            channel = future.channel();
            log.info("[start][Netty Server 启动在 {} 端口]", nettyServerConfig.tcpPort);
        }
    }

    @PreDestroy
    public void shutDown(){
        // 关闭NettyServer
        if(channel != null){
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
