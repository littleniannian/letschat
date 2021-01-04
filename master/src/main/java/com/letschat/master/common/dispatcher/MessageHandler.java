package com.letschat.master.common.dispatcher;

import com.letschat.master.common.dispatcher.Message;
import io.netty.channel.Channel;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName MessageHandler.java
 * @createTime 2021年01月04日 14:38:00
 */
public interface MessageHandler<T extends Message> {
    /**
     * 执行处理消息
     * @param channel
     * @param message
     */
    void execute(Channel channel,T message);

    /**
     * 获得消息类型
     * @return
     */
    String getType();

}
