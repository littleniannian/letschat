package com.letschat.master.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.letschat.master.common.dispatcher.Message;
import com.letschat.master.util.SU;
import lombok.Data;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName Invocation.java
 * @createTime 2021年01月04日 15:11:00
 */
@Data
public class Invocation {

    private String type;
    private String message;

    public Invocation() {
    }

    public Invocation(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public Invocation(String type, Message message) throws JsonProcessingException {
        this.type = type;
        this.message = SU.getJsonMapper().writeValueAsString(message);
    }
}
