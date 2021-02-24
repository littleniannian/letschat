package com.letschat.master.server.message.chat;

import com.letschat.master.common.dispatcher.Message;
import lombok.Data;

/**
 * @author jarvis
 * @version 1.0.0
 * @Description
 * @createTime 2021年01月12日 00:21:00
 */
@Data
public class ChatSendResponse implements Message {

    public static final String TYPE = "CHAT_SEND_RESPONSE";

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;

}
