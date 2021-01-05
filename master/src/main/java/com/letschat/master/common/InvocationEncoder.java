package com.letschat.master.common;

import com.letschat.master.util.SU;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName InvocationEncoder.java
 * @Description 和InvocationDecoder一起解决粘包和拆包问题
 * @createTime 2021年01月04日 16:23:00
 */
@Slf4j
public class InvocationEncoder extends MessageToByteEncoder<Invocation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Invocation invocation, ByteBuf out) throws Exception {
        byte[] content = SU.getJsonMapper().writeValueAsBytes(invocation);
        out.writeInt(content.length);
        out.writeBytes(content);
        log.info("[encode][连接[{}] 编码了一条消息[{}]]", ctx.channel().id(), invocation);
    }
}
