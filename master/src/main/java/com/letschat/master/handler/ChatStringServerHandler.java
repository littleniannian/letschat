/*
package com.letschat.master.handler;

import com.letschat.master.util.Constants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Date;

*/
/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName ChatStringServerHandler.java
 * @createTime 2020年12月31日 13:52:00
 *//*

@Slf4j
public class ChatStringServerHandler extends SimpleChannelInboundHandler<String> {
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel incoming = ctx.channel();
        log.info("频道 id {}",incoming.id());
        for (Channel channel: channels) {
            log.info("聊天信息 {}",msg);
            if(channel!=incoming){
                channel.writeAndFlush("["+ InetAddress.getLocalHost() +"]:"+msg+ Constants.MSG_SUFFIX);
            }else {
                channel.writeAndFlush("[you]"+msg+Constants.MSG_SUFFIX);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    */
/**
     * 连接频道后
     * @param ctx
     * @throws Exception
     *//*

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String remoteAddress = String.valueOf(ctx.channel().remoteAddress().toString());
        log.info("远程连接的用户地址{}",remoteAddress);
        ctx.write("欢迎，进入 " + InetAddress.getLocalHost().getHostName() + "!"+Constants.MSG_SUFFIX);
        ctx.write("It is " + new Date() + " now."+Constants.MSG_SUFFIX);
        ctx.flush();
        channels.writeAndFlush("[新用户] -" +remoteAddress+"加入");
        channels.add(ctx.channel());
    }

    */
/**
     * 断开连接后
     * @param ctx
     * @throws Exception
     *//*

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String remoteAddress = String.valueOf(ctx.channel().remoteAddress().toString());
        log.info("channel inactive remoteAddress {}",remoteAddress);
        channels.writeAndFlush("[用户] "+remoteAddress+"- 离开");
        channels.remove(ctx.channel());
    }

*/
/*    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("远程连接的用户地址{}",ctx.channel().remoteAddress());
        String uName = String.valueOf(RandomUtil.getName());
        channels.writeAndFlush("[新用户] -" +uName+"加入");
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.writeAndFlush("[有个用户离开] - 离开");
        channels.remove(incoming);
    }*//*

}
*/
