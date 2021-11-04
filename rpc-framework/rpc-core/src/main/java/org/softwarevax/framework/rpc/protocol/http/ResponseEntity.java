package org.softwarevax.framework.rpc.protocol.http;

import java.util.Iterator;
import java.util.Map;

public class ResponseEntity implements Cloneable {

    private String protocol;

    private String version;

    private int statusCode;

    private Map<String, String> headers;

    private String body;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getProtocol() + "/" + this.getVersion() + " " + this.statusCode + " OK").append("\r\n");
        Iterator<Map.Entry<String, String>> iterator = this.headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            sb.append(next.getKey()).append(": ").append(next.getValue()).append("\r\n");
        }
        sb.append("\r\n");
        sb.append(body);
        return sb.toString();
    }
}
