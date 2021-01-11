package com.letschat.master.server.message.chat;

import com.letschat.master.common.dispatcher.Message;
import lombok.Data;

/**
 * @author jarvis
 * @version 1.0.0
 * @Description
 * @createTime 2021年01月12日 00:19:00
 */
@Data
public class ChatSendToOneRequest implements Message {
    public static final String TYPE = "CHAT_SEND_TO_ONE_REQUEST";

    /**
     * 发送给的用户
     */
    private String toUser;

    /**
     * 信息编号
     */
    private String msgId;

    /**
     * 内容
     */
    private String content;

}
