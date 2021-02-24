package com.letschat.master.server.messageHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.letschat.master.common.Invocation;
import com.letschat.master.common.dispatcher.MessageHandler;
import com.letschat.master.server.NettyChannelManager;
import com.letschat.master.server.message.chat.ChatRedirectReq;
import com.letschat.master.server.message.chat.ChatSendResponse;
import com.letschat.master.server.message.chat.ChatSendToOneRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName ChatSendToOneHandler.java
 * @createTime 2021年02月24日 10:13:00
 */
@Component
@Slf4j
public class ChatSendToOneHandler implements MessageHandler<ChatSendToOneRequest> {

    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, ChatSendToOneRequest message) {
        try {
            // 直接返回发送成功的消息
            ChatSendResponse sendResponse = new ChatSendResponse();
            sendResponse.setMsgId(message.getMsgId());
            sendResponse.setCode(0);
            channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE,sendResponse));

            // 创建转发的消息，发送给指定用户
            ChatRedirectReq redirectReq = new ChatRedirectReq();
            redirectReq.setMsgId(message.getMsgId());
            redirectReq.setContent(message.getContent());
            redirectReq.setFromUser(channel.attr(NettyChannelManager.CHANNEL_ATTR_KEY_USER).get());
            nettyChannelManager.send(message.getToUser(),new Invocation(ChatRedirectReq.TYPE,redirectReq));
        } catch (JsonProcessingException e) {
            log.error("[ChatSendToOneHandler] execute [JsonProcessingException] request [{}]",message);
        }
    }

    @Override
    public String getType() {
        return ChatSendToOneRequest.TYPE;
    }
}
