package com.letschat.master.common;

import com.letschat.master.util.SU;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName InvocationDecoder.java
 * @createTime 2021年01月04日 16:27:00
 */
@Slf4j
public class InvocationDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 标记当前读取位置
        in.markReaderIndex();
        if (in.readableBytes() <= 4)
            return;
        int length = in.readInt();
        if(length<0)
            throw new CorruptedFrameException("negative length :"+length);
        // 如果 message 不够可读，则退回到原读取位置
        if(in.readableBytes()<length){
            in.resetReaderIndex();
            return;
        }
        // 读取内容
        byte[] content = new byte[length];
        in.readBytes(content);
        // 解析成invocation对象
        Invocation invocation = SU.getJsonMapper().readValue(content,Invocation.class);
        out.add(invocation);
        log.info("[decode][连接[{}] 解析到一条消息[{}]", ctx.channel().id(), invocation);
    }
}
