package org.softwarevax.framework.tomcat.utils;

import org.apache.commons.lang3.StringUtils;
import org.softwarevax.framework.tomcat.http.*;
import org.softwarevax.framework.tomcat.http.enums.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpParser {

    public static HttpRequest parser(String receive) {
        Assert.notBlank(receive, "协议非法");
        DefaultHttpRequest request = new DefaultHttpRequest();
        String[] split = receive.split(Constants.LINE_SPLIT);
        Assert.isTrue(split != null && split.length > 1, "协议非法");
        // http请求第一行：方法 uri 协议/版本
        String firstLine = split[0];
        Assert.notBlank(firstLine, "协议非法");
        String[] firstLineSplit = StringUtils.split(firstLine, Constants.PROP_SPLIT);
        request.setUrl(firstLineSplit[1]);
        Assert.isTrue(firstLineSplit != null && firstLineSplit.length == 3, "协议非法");
        Method method = getMethod(firstLineSplit[0]);
        Assert.notNull(method, "请求方法不支持");
        request.setMethod(method);
        Assert.isTrue(StringUtils.contains(firstLineSplit[1], "?"), "协议非法");
        String[] uriQueryString = StringUtils.split(firstLineSplit[1], "?");
        Assert.notNull(uriQueryString, "协议非法");
        request.setUri(uriQueryString[0]);
        request.setQueryString(extractParameter(uriQueryString.length == 1 ? "" : uriQueryString[1], "&", "="));
        String[] protocol =  StringUtils.split(firstLineSplit[2], "/");
        Assert.isTrue(protocol != null && protocol.length == 2, "协议非法");
        request.setProtocol(protocol[0]);
        request.setProtocolVersion(protocol[1]);
        String body = split[split.length - 1];
        // 去除第一行的协议，最后一行的body，和倒数第二行的空白行
        String headerString = StringUtils.join(Arrays.copyOfRange(split, 1, split.length - 2), Constants.LINE_SPLIT);
        Map<String, String> headers = extractParameter(headerString, Constants.LINE_SPLIT, ":");
        request.setHeaders(headers);
        String contentType = getValue(headers, Constants.CONTENT_TYPE_KEY);
        request.setContentType(StringUtils.isBlank(contentType) ? Constants.DEFAULT_CONTENT_TYPE : contentType);
        request.setBody(body);
        return request;
    }

    public static String response(HttpResponse resp) {
        StringBuffer sb = new StringBuffer();
        // HTTP/1.1 200 OK
        sb.append(Constants.HTTP_VERSION);
        sb.append(Constants.PROP_SPLIT);
        sb.append(resp.getStatusCode().getCode());
        sb.append(Constants.PROP_SPLIT);
        sb.append(resp.getStatusCode().getMsg());
        sb.append(Constants.LINE_SPLIT);
        // header
        sb.append("Server: ").append(Constants.HTTP_SERVER_NAME).append(Constants.LINE_SPLIT);
        sb.append("Content-Type: ").append(StringUtils.isBlank(resp.getContentType()) ? Constants.DEFAULT_CONTENT_TYPE : resp.getContentType());
        sb.append(Constants.LINE_SPLIT);
        sb.append("Connection: keep-alive").append(Constants.LINE_SPLIT);
        sb.append("Cache-Control: max-age=0").append(Constants.LINE_SPLIT);
        sb.append("Content-Length: ").append(resp.getBody() == null ? 0 : resp.getBody().toString().length()).append(Constants.LINE_SPLIT);
        sb.append(Constants.LINE_SPLIT);
        // body
        if(resp.getBody() != null) {
            sb.append(resp.getBody());
        }
        return sb.toString();
    }

    private static Method getMethod(String methodName) {
        Assert.notBlank(methodName, "方法名不能为空");
        String method = StringUtils.lowerCase(methodName);
        switch (method) {
            case "post": {
                return Method.POST;
            }
            case "get": {
                return Method.GET;
            }
            case "delete": {
                return Method.DELETE;
            }
            case "put": {
                return Method.PUT;
            }
        }
        return null;
    }

    /**
     * 提取参数
     * @param str
     * @param split
     * @param assign
     * @return
     */
    private static Map<String, String> extractParameter(String str, String split, String assign) {
        HashMap<String, String> parameters = new HashMap<>();
        if(StringUtils.isBlank(str) || !StringUtils.contains(str, assign)) {
            return parameters;
        }
        String[] keyVals = StringUtils.split(str, split);
        for (int i = 0, size = keyVals.length; i < size; i++) {
            if(!StringUtils.contains(keyVals[i], assign)) {
                continue;
            }
            String[] keyVal = StringUtils.split(keyVals[i], assign);
            // 避免出现"a="或"=b"的情况，处理一下特殊情况
            parameters.put(StringUtils.isBlank(keyVal[0]) ? "" : keyVal[0].trim(), StringUtils.isBlank(keyVal[1]) ? "" : keyVal[1].trim());
        }
        return parameters;
    }

    /**
     * 忽略大小写获取键值对的值
     * @param props
     * @param key
     * @return
     */
    public static String getValue(Map<String, String> props, String key) {
        if(props == null || props.isEmpty() || StringUtils.isBlank(key)) {
            return null;
        }
        Iterator<Map.Entry<String, String>> iterator = props.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            if(StringUtils.equalsIgnoreCase(next.getKey(), key)) {
                return next.getValue();
            }
        }
        return null;
    }
}
