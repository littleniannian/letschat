package com.letschat.master.handler;

import com.letschat.master.util.RandomUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author jarvis.yuan
 * @version 1.0.0
 * @ClassName ChatServerHandler.java
 * @createTime 2020年12月28日 15:06:00
 */
@ChannelHandler.Sharable
@Slf4j
public class ChatServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        log.info("频道 id {}",incoming.id());
        for (Channel channel: channels) {
            log.info("聊天信息 {}",msg.text());
            if(channel!=incoming){
                // 发送给除发送人以外的人
                channel.writeAndFlush(new TextWebSocketFrame("["+ InetAddress.getLocalHost() +"]")+msg.text());
            }else {
                // 显示给发送人自己
                channel.writeAndFlush(new TextWebSocketFrame("[you]"+msg.text()));
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("远程连接的用户地址{}",ctx.channel().remoteAddress());
        String uName = String.valueOf(RandomUtil.getName());
        channels.writeAndFlush(new TextWebSocketFrame("[新用户] -" +uName+"加入"));
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.writeAndFlush(new TextWebSocketFrame("[用户] - 离开"));
        channels.remove(incoming);
    }
}
