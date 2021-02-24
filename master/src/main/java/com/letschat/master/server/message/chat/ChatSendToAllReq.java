package com.letschat.master.server.message.chat;

import com.letschat.master.common.dispatcher.Message;
import lombok.Data;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName ChatSendToAllReq.java
 * @Description 群聊请求
 * @createTime 2021年02月24日 10:42:00
 */
@Data
public class ChatSendToAllReq implements Message {
    public static final String TYPE = "CHAT_SEND_TO_ALL_REQUEST";

    private String msgId;

    private String content;

    // TODO 应该还有一个groupId字段 群编号
}
