package com.letschat.master.server.message.heartbeat;

import com.letschat.master.common.dispatcher.Message;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName HeartbeatRequest.java
 * @createTime 2021年01月05日 15:30:00
 */
public class HeartbeatRequest implements Message {
    public static final String TYPE ="HEARTBEAT_REQUEST";

    @Override
    public String toString() {
        return "HeartbeatRequest{}";
    }
}
