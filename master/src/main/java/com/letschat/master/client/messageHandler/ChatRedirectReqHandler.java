package com.letschat.master.client.messageHandler;

import com.letschat.master.common.dispatcher.MessageHandler;
import com.letschat.master.server.message.chat.ChatRedirectReq;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName ChatRedirectReqHandler.java
 * @createTime 2021年02月24日 10:36:00
 */
@Slf4j
@Component
public class ChatRedirectReqHandler implements MessageHandler<ChatRedirectReq> {
    @Override
    public void execute(Channel channel, ChatRedirectReq message) {
        log.info("[execute] 收到消息 [{}]",message);
    }

    @Override
    public String getType() {
        return ChatRedirectReq.TYPE;
    }
}
