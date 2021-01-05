package com.letschat.master.server.messageHandler;

import com.letschat.master.common.dispatcher.MessageHandler;
import com.letschat.master.server.message.heartbeat.HeartbeatRequest;
import com.letschat.master.server.message.heartbeat.HeartbeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName HeartbeatRequestHandler.java
 * @Description 心跳请求
 * @createTime 2021年01月05日 15:28:00
 */
@Slf4j
@Component
public class HeartbeatRequestHandler implements MessageHandler<HeartbeatRequest> {
    @Override
    public void execute(Channel channel, HeartbeatRequest message) {
        log.info("[execute][收到连接[{}] 的心跳请求]", channel.id());
        // 响应心跳
        HeartbeatResponse response = new HeartbeatResponse();
        channel.writeAndFlush(response);
    }

    @Override
    public String getType() {
        return HeartbeatRequest.TYPE;
    }
}
