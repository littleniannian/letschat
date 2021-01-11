package com.letschat.master.client;

import com.letschat.master.common.InvocationDecoder;
import com.letschat.master.common.InvocationEncoder;
import com.letschat.master.common.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName NettyClientHandlerInitializer.java
 * @createTime 2021年01月05日 14:19:00
 */
@Slf4j
@Component
public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {
    private static final Integer READ_TIMEOUT_SECONDS = 60;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private NettyClientHandler nettyClientHandler;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                // 空闲检测
                // 在 Channel 的读或者写空闲时间太长时，将会触发一个 IdleStateEvent 事件。
                .addLast(new IdleStateHandler(READ_TIMEOUT_SECONDS,0,0))
                .addLast(new ReadTimeoutHandler(3*READ_TIMEOUT_SECONDS))
                .addLast(new InvocationEncoder())
                .addLast(new InvocationDecoder())
                .addLast(messageDispatcher)
                .addLast(nettyClientHandler);
    }
}
