package com.letschat.master.client;

import com.letschat.master.common.Invocation;
import com.letschat.master.server.message.heartbeat.HeartbeatRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName NettyClientHandler.java
 * @createTime 2021年01月05日 14:24:00
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private NettyClient nettyClient;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 发起重连
        nettyClient.reconnect();
        // 继续触发事件
        super.channelInactive(ctx);
    }

    /**
     * 有用户事件触发时，运行心跳检测
     * @param ctx
     * @param event
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if(event instanceof IdleStateEvent){
            log.info("[userEventTriggered][发起一次心跳]");
        }
        HeartbeatRequest request = new HeartbeatRequest();
        nettyClient.send(new Invocation(HeartbeatRequest.TYPE,request));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("[exceptionCaught][连接[{}] 发生[{}]异常]", ctx.channel().id(), cause);
        ctx.channel().close();
    }
}
