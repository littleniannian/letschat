package com.letschat.master.client.messageHandler;

import com.letschat.master.common.dispatcher.MessageHandler;
import com.letschat.master.server.message.chat.ChatSendResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName ChatSendResHandler.java
 * @createTime 2021年02月24日 10:33:00
 */
@Component
@Slf4j
public class ChatSendResHandler implements MessageHandler<ChatSendResponse> {
    @Override
    public void execute(Channel channel, ChatSendResponse message) {
        log.info("[execute] 发送结果 [{}]",message);
    }

    @Override
    public String getType() {
        return ChatSendResponse.TYPE;
    }
}
