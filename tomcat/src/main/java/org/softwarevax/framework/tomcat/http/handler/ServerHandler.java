package org.softwarevax.framework.tomcat.http.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.softwarevax.framework.tomcat.http.DefaultHttpResponse;
import org.softwarevax.framework.tomcat.http.HttpRequest;
import org.softwarevax.framework.tomcat.http.HttpResponse;
import org.softwarevax.framework.tomcat.http.enums.StatusCode;
import org.softwarevax.framework.tomcat.utils.HttpParser;

import java.net.URLDecoder;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private StringBuffer msgBuff = new StringBuffer();

    private HttpRequest request;

    public ServerHandler() {
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
        msgBuff.append(msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
        try {
            String msg = URLDecoder.decode(msgBuff.toString(), "utf-8");
            System.out.println(msg);
            this.request = HttpParser.parser(msg);
            // 服务端做出业务处理后返回结果: eg:返回内容hello vax
            HttpResponse resp = new DefaultHttpResponse().setStatusCode(StatusCode._200).setBody("hello vax");
            ctx.writeAndFlush(HttpParser.response(resp));
            this.msgBuff.setLength(0);
        } catch (IllegalArgumentException e) {
            // 异常捕获
            HttpResponse resp = new DefaultHttpResponse().setStatusCode(StatusCode._450);
            ctx.writeAndFlush(HttpParser.response(resp));
        }
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelWritabilityChanged();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }

}
