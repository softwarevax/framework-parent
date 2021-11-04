package org.softwarevax.framework.rpc.client;

import org.softwarevax.framework.utils.StringUtils;

import java.util.Iterator;
import java.util.Map;

public class HttpClient {

    /**
     * json
     * @param url
     * @param body
     * @return
     */
    public static String postBuilder(String host, String url, String body) {
        StringBuffer sb = new StringBuffer();
        sb.append("POST").append(" ");
        sb.append(url).append(" ");
        sb.append("HTTP/1.1").append("\r\n");
        sb.append("Host: ").append(host).append("\r\n");
        sb.append("Content-Type: application/json").append("\r\n");
        sb.append("Connection: keep-alive").append("\r\n");
        sb.append("Cache-Control: max-age=0").append("\r\n");
        sb.append("Accept: text/html,application/xhtml xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9").append("\r\n");
        sb.append("\r\n\r\n");
        sb.append(body);
        return sb.toString();
    }

    /**
     * formdata
     * @param url
     * @param body
     * @return
     */
    public static String postFormBuilder(String host, String url, Map<String, String> body) {
        StringBuffer sb = new StringBuffer();
        sb.append("POST").append(" ");
        sb.append(url).append(" ");
        sb.append("HTTP/1.1").append("\r\n");
        sb.append("Host: ").append(host).append("\r\n");
        sb.append("Content-Type: application/x-www-form-urlencoded").append("\r\n");
        sb.append("Connection: keep-alive").append("\r\n");
        sb.append("Cache-Control: max-age=0").append("\r\n");
        sb.append("Accept: text/html,application/xhtml xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9").append("\r\n");
        sb.append("\r\n");
        if(body != null && !body.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = body.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                sb.append(next.getKey()).append("=").append(next.getValue()).append("&");
            }
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String getFormBody(String resp) {
        String[] split = StringUtils.split(resp, "\r\n\r\n");
        return split[1];
    }
}
