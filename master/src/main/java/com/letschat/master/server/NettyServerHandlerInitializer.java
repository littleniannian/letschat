package com.letschat.master.server;

import com.letschat.master.common.InvocationDecoder;
import com.letschat.master.common.InvocationEncoder;
import com.letschat.master.common.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName NettyServerHandlerInitializer.java
 * @createTime 2021年01月04日 14:33:00
 */
@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    private static final Integer READ_TIMEOUT_SECONDS = 3 * 60;

    private final MessageDispatcher messageDispatcher;
    private final NettyServerHandler nettyServerHandler;

    public NettyServerHandlerInitializer(MessageDispatcher messageDispatcher, NettyServerHandler nettyServerHandler) {
        this.messageDispatcher = messageDispatcher;
        this.nettyServerHandler = nettyServerHandler;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // <1> 获得 Channel 对应的 ChannelPipeline
        ChannelPipeline channelPipeline = ch.pipeline();
        // <2> 添加一堆 NettyServerHandler 到 ChannelPipeline 中
        channelPipeline
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))// 空闲检测
                .addLast(new InvocationEncoder())// 编码器
                .addLast(new InvocationDecoder())// 解码器
                .addLast(messageDispatcher)// 消息分发器
                .addLast(nettyServerHandler);// 服务端处理器
    }
}
