package com.letschat.master.server.message.heartbeat;

import com.letschat.master.common.dispatcher.Message;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName HeartbeatResponse.java
 * @createTime 2021年01月05日 15:32:00
 */
public class HeartbeatResponse implements Message {
    public static final String TYPE ="HEARTBEAT_RESPONSE";

    @Override
    public String toString() {
        return "HeartbeatResponse{}";
    }
}
