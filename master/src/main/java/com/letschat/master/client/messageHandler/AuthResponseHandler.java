package com.letschat.master.client.messageHandler;

import com.letschat.master.common.dispatcher.MessageHandler;
import com.letschat.master.server.message.auth.AuthResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName AuthResponseHandler.java
 * @createTime 2021年01月05日 16:01:00
 */
@Slf4j
@Component
public class AuthResponseHandler implements MessageHandler<AuthResponse> {
    @Override
    public void execute(Channel channel, AuthResponse message) {
        log.info("[execute] 认证结果 [{}]",message);
    }

    @Override
    public String getType() {
        return AuthResponse.TYPE;
    }
}
