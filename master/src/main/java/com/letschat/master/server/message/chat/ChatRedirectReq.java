package com.letschat.master.server.message.chat;

import com.letschat.master.common.dispatcher.Message;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName ChatRediectReq.java
 * @createTime 2021年02月24日 10:09:00
 */
@Data
public class ChatRedirectReq implements Message {
    public static final String TYPE = "CHAT_REDIRECT_TO_USER_REQUEST";

    /**
     * 消息发送者
     */
    private String fromUser;

    /**
     * 消息编号
     */
    private String msgId;

    /**
     * 消息内容
     */
    private String content;
}
