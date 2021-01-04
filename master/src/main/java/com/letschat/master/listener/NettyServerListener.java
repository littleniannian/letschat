/*
package com.letschat.master.listener;

import com.letschat.master.server.ChatServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

*/
/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName NettyServerListener.java
 * @createTime 2020年12月31日 13:41:00
 *//*

@Component
@WebListener
@Slf4j
public class NettyServerListener implements ServletContextListener {

    private final ChatServer chatServer;

    public NettyServerListener(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("servletContext初始化...");
        chatServer.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
*/
