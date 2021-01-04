package com.letschat.master.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName NettyServerHandler.java
 * @Description 实现客户端 Channel 建立连接、断开连接、异常时的处理。
 * @createTime 2021年01月04日 15:24:00
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final NettyChannelManager channelManager;

    public NettyServerHandler(NettyChannelManager channelManager) {
        this.channelManager = channelManager;
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        channelManager.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelManager.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("[exceptionCaught][连接[{}] 发生异常]", ctx.channel().id(), cause);
        ctx.channel().close();
    }
}
