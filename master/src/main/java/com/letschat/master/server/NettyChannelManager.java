package com.letschat.master.server;

import com.letschat.master.common.Invocation;
import com.letschat.master.util.Constants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName NettyChannelManager.java
 * @Description 客户端Channel的管理，向客户端Channel发送消息
 * @createTime 2021年01月04日 15:27:00
 */
@Slf4j
@Component
public class NettyChannelManager {

    private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance(Constants.CHANNEL_ATTR_KEY);

    /**
     * Channel 映射
     */
    private ConcurrentMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();

    /**
     * 用户与 Channel 的映射。
     *
     * 通过它，可以获取用户对应的 Channel。这样，我们可以向指定用户发送消息。
     */
    private ConcurrentMap<String,Channel> userChannels = new ConcurrentHashMap<>();


    /**
     * 添加channel到channels中
     * @param channel
     */
    public void add(Channel channel){
        channels.put(channel.id(),channel);
        log.info("一个连接加入[{}]",channel.id());
    }

    /**
     * 添加指定用户到userChannels中
     * @param channel
     * @param user
     */
    public void addUser(Channel channel,String user){
        Channel existChannel = channels.get(channel.id());
        if(existChannel == null){
            log.info("addUser 连接[{}] 不存在",channel.id());
            return;
        }
        // 设置属性
        channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
        // 添加到 userChannels
        userChannels.put(user,channel);
    }

    public void remove(Channel channel){
        // 移除 channels
        channels.remove(channel.id());
        // 移除 userChannels
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)) {
            userChannels.remove(channel.attr(CHANNEL_ATTR_KEY_USER).get());
        }
        log.info("[remove][一个连接[{}]离开]", channel.id());
    }

    public void send(String user, Invocation invocation){
        // 获得用户对应的 Channel
        Channel channel = userChannels.get(user);
        if (channel == null) {
            log.error("[send][连接不存在]");
            return;
        }
        if (!channel.isActive()) {
            log.error("[send][连接({})未激活]", channel.id());
            return;
        }
        // 发送消息
        channel.writeAndFlush(invocation);
    }

    public void sendAll(Invocation invocation){
        for (Channel channel : channels.values()) {
            if (!channel.isActive()) {
                log.error("[send][连接[{}]未激活]", channel.id());
                return;
            }
            // 发送消息
            channel.writeAndFlush(invocation);
        }
    }



}
