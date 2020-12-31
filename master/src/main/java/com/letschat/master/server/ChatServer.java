package com.letschat.master.server;

import com.letschat.master.config.NettyServerConfig;
import com.letschat.master.handler.ChatServerHandler;
import com.letschat.master.handler.ChatStringServerHandler;
import com.letschat.master.util.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName ChatServer.java
 * @Description Netty服务器
 * @createTime 2020年12月31日 10:42:00
 */
@Slf4j
@Component
public class ChatServer {

    private final NettyServerConfig nettyServerConfig;

    public ChatServer(NettyServerConfig nettyServerConfig) {
        this.nettyServerConfig = nettyServerConfig;
    }

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.option(ChannelOption.SO_BACKLOG,nettyServerConfig.backlog);
            serverBootstrap.group(group,bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(nettyServerConfig.tcpPort)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            log.info("接收到新连接");
                            /*socketChannel.pipeline().addLast(new HttpServerCodec());
                            socketChannel.pipeline().addLast(new ChunkedWriteHandler());
                            socketChannel.pipeline().addLast(new HttpObjectAggregator(8192));
                            socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/ws",null,true,65526*10));*/
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            socketChannel.pipeline().addLast(new StringDecoder(Charset.forName(Constants.MSG_DECODING)));
                            socketChannel.pipeline().addLast(new StringEncoder(Charset.forName(Constants.MSG_DECODING)));
                            socketChannel.pipeline().addLast(new ChatStringServerHandler());
                        }
                    });
            ChannelFuture cf = serverBootstrap.bind().sync();
            log.info("ChatServer 启动正在监听 {}",cf.channel().localAddress());
            // 等待服务器socket关闭
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty 服务器启动异常");
        }finally {
            bossGroup.shutdownGracefully();
            group.shutdownGracefully();
        }
    }
}
