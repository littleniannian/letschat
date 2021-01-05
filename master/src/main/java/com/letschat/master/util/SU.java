package com.letschat.master.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName SU.java
 * @createTime 2021年01月04日 15:17:00
 */
public class SU {
    private static final ObjectMapper jsonMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);

    public static ObjectMapper getJsonMapper(){
        return jsonMapper;
    }
}
