package com.letschat.master.common.dispatcher;

import com.letschat.master.common.Invocation;
import com.letschat.master.util.SU;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName MessageDispatcher.java
 * @Description 创建 MessageDispatcher 类，将 Invocation 分发到其对应的 MessageHandler 中，
 * 进行业务逻辑的执行。
 * @createTime 2021年01月04日 15:08:00
 */
@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    private final ExecutorService executor = Executors.newFixedThreadPool(200);

    /**
     * 这里将消息匹配到对应的消息handler进行处理
     * @param ctx
     * @param invocation
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation invocation) throws Exception {
        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(invocation.getType());
        Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
        Message message = SU.getJsonMapper().readValue(invocation.getMessage(),messageClass);
        // 交给线程池执行
        executor.submit(()-> messageHandler.execute(ctx.channel(),message));
    }
}
