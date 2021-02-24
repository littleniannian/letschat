package com.letschat.master.server.messageHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.letschat.master.common.Invocation;
import com.letschat.master.common.dispatcher.MessageHandler;
import com.letschat.master.server.NettyChannelManager;
import com.letschat.master.server.message.chat.ChatRedirectReq;
import com.letschat.master.server.message.chat.ChatSendResponse;
import com.letschat.master.server.message.chat.ChatSendToAllReq;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName ChatSendToAllHandler.java
 * @createTime 2021年02月24日 10:44:00
 */
@Slf4j
@Component
public class ChatSendToAllHandler implements MessageHandler<ChatSendToAllReq> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, ChatSendToAllReq message) {
        try {
            ChatSendResponse sendResponse = new ChatSendResponse();
            sendResponse.setMsgId(message.getMsgId());
            sendResponse.setCode(0);
            channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE, sendResponse));
            ChatRedirectReq sendToUserRequest = new ChatRedirectReq();
            sendToUserRequest.setMsgId(message.getMsgId());
            sendToUserRequest.setContent(message.getContent());
            nettyChannelManager.sendAll(new Invocation(ChatRedirectReq.TYPE, sendToUserRequest));
        } catch (JsonProcessingException e) {
            log.error("[execute] JsonProcessingException message [{}]",message);
        }
    }

    @Override
    public String getType() {
        return ChatSendToAllReq.TYPE;
    }
}
