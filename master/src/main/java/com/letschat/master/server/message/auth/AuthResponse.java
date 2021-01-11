package com.letschat.master.server.message.auth;

import com.letschat.master.common.dispatcher.Message;
import lombok.Data;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName AuthResponse.java
 * @createTime 2021年01月05日 15:40:00
 */
@Data
public class AuthResponse implements Message {
    public static final String TYPE = "AUTH_RESPONSE";

    private Integer code;

    private String message;
}
