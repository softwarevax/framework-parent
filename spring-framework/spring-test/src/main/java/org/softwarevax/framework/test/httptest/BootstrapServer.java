package org.softwarevax.framework.test.httptest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class BootstrapServer {

    private Integer port;

    private Channel channel;

    private EventLoopGroup boss;

    private EventLoopGroup worker;

    private ServerBootstrap bootstrap;

    public BootstrapServer(int port, HttpInvoke invoke) {
        this.port = port;
        this.boss = new NioEventLoopGroup();
        this.worker = new NioEventLoopGroup();
        this.bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(this.boss, this.worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new StringDecoder());
                            ch.pipeline().addLast("encoder", new StringEncoder());
                            ch.pipeline().addLast(new HttpHandler(invoke));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = bootstrap.bind(this.port).sync();
            System.out.println("http server started, port = " + port);
            this.channel = f.channel();
            this.channel.closeFuture().sync();
            this.boss.shutdownGracefully().sync();
            this.worker.shutdownGracefully().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
