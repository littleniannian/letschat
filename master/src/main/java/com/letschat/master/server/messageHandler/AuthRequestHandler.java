package com.letschat.master.server.messageHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.letschat.master.common.Invocation;
import com.letschat.master.common.dispatcher.MessageHandler;
import com.letschat.master.server.NettyChannelManager;
import com.letschat.master.server.message.auth.AuthRequest;
import com.letschat.master.server.message.auth.AuthResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName AuthRequestHandler.java
 * @createTime 2021年01月05日 15:41:00
 */
@Slf4j
@Component
public class AuthRequestHandler implements MessageHandler<AuthRequest> {

    private final NettyChannelManager nettyChannelManager;

    public AuthRequestHandler(NettyChannelManager nettyChannelManager) {
        this.nettyChannelManager = nettyChannelManager;
    }

    @Override
    public void execute(Channel channel, AuthRequest message) {
        try {
            if(StringUtils.isEmpty(message.getAccessToken())){
                AuthResponse authResponse = new AuthResponse();
                authResponse.setCode(1);
                authResponse.setMessage("认证 accessToken 未传入");
                channel.writeAndFlush(new Invocation(AuthResponse.TYPE,authResponse));
                return;
            }
            // 将用户和Channel绑定
            // TODO 这里暂时直接将accessToken作为User
            nettyChannelManager.addUser(channel,message.getAccessToken());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setCode(0);
            channel.writeAndFlush(new Invocation(AuthResponse.TYPE,authResponse));
        } catch (JsonProcessingException e) {
            log.error("[AuthRequestHandler] [execute] JsonProcessingException request [{}]",message);
        }
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}
