package com.letschat.master.server.message.auth;

import com.letschat.master.common.dispatcher.Message;
import lombok.Data;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName AuthRequest.java
 * @createTime 2021年01月05日 15:39:00
 */
@Data
public class AuthRequest implements Message {
    public static final String TYPE = "AUTH_REQUEST";

    private String accessToken;
}
