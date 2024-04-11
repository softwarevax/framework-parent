package org.softwarevax.framework.test.httptest;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.softwarevax.framework.utils.PropertyUtils;
import org.softwarevax.framework.utils.StringUtils;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHandler extends ChannelInboundHandlerAdapter {

    private HttpInvoke httpInvoke;

    public HttpHandler(HttpInvoke httpInvoke) {
        this.httpInvoke = httpInvoke;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // url解码
        String request = URLDecoder.decode((String) msg,"UTF-8");
        System.out.println(request);
        RequestEntity entity = new RequestEntity();
        try {
            String[] kvs;
            if(StringUtils.contains(request, "\r\n\r\n")) {
                String[] body = StringUtils.split(request, "\r\n\r\n");
                kvs = StringUtils.split(body[0], "\r\n");
                if(body.length > 1) {
                    entity.setBody(body[1]);
                }
            } else {
                kvs = StringUtils.split(request, "\r\n");
            }
            String[] protocols = kvs[0].split(" ");
            entity.setMethodName(protocols[0]);
            String contextPath = protocols[1];
            entity.setUri(StringUtils.split(contextPath, "\\?")[0]);
            if(StringUtils.contains(contextPath, "?")) {
                String queryStr = StringUtils.split(contextPath, "\\?")[1];
                String[] query = StringUtils.split(queryStr, "&");
                Map<String, String> queryString = new HashMap<>();
                for(String str : query) {
                    queryString.put(StringUtils.split(str, "=")[0], StringUtils.split(str, "=")[1]);
                }
                entity.setQueryString(queryString);
            }
            entity.setProtocol(protocols[2].split("/")[0]);
            entity.setVersion(protocols[2].split("/")[1]);
            Map<String, String> headers = new HashMap<>();
            for(int i = 1, size = kvs.length; i < size; i++) {
                headers.put(kvs[i].split(":")[0], kvs[i].split(":")[1]);
            }
            entity.setHeaders(headers);
            String contentType = PropertyUtils.getValIgnoreCase(PropertyUtils.parse(entity.getHeaders()), "content-Type");
            if(StringUtils.isBlank(contentType)) {
                headers.put("content-Type", "application/x-www-form-urlencoded");
            } else {
                entity.setContentType(contentType.trim());
            }
            entity.setBody(parseBody(entity.getBody(), entity.getContentType()));
            Object obj = httpInvoke.invoke(entity);
            if(obj == null) {
                ctx.write(builder(404, entity, "not found"));
                ctx.flush();
                return;
            }
            ctx.write(builder(200, entity, obj));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.write(builder(500, entity, ""));
        }
        ctx.flush();
    }

    private String builder(int code, RequestEntity entity, Object obj) {
        Map<String, String> headers = new HashMap<>();
        ResponseEntity resp = new ResponseEntity();
        resp.setProtocol(entity.getProtocol());
        resp.setVersion(entity.getVersion());
        resp.setStatusCode(code);
        headers.put("Server", "maple");
        headers.put("Content-Type", "text/html");
        headers.put("Request-Url", entity.getUri());
        headers.put("Content-Length", String.valueOf(JSON.toJSONString(obj).length()));
        resp.setHeaders(headers);
        resp.setBody(JSON.toJSONString(obj));
        return resp.toString();
    }

    private Object parseBody(Object obj, String contentType) {
        if(StringUtils.equalsIgnore(contentType, "application/x-www-form-urlencoded")) {
            Map<String, String> formdata = new HashMap<>();
            String formdataStr = (String) obj;
            String[] formdataStrKv = StringUtils.split(formdataStr, "&");
            for (int i = 0; i < formdataStrKv.length; i++) {
                formdata.put(StringUtils.split(formdataStrKv[i], "=")[0], StringUtils.split(formdataStrKv[i], "=")[1]);
            }
            return formdata;
        }
        if(StringUtils.equalsIgnore(contentType, "application/json")) {
            return JSON.parse(obj.toString());
        }
        return obj;
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        System.out.println("http channelRegistered");
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
        System.out.println("http channelUnregistered");
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
        System.out.println("http channelActive");
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
        System.out.println("http channelInactive");
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
        System.out.println("http channelReadComplete");
    }

    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
        System.out.println("http userEventTriggered");
    }

    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelWritabilityChanged();
        System.out.println("http channelWritabilityChanged");
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
        System.out.println("http exceptionCaught");
    }
}
