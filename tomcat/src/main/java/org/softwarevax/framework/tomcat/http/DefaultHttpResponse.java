package org.softwarevax.framework.tomcat.http;

import lombok.Data;
import lombok.experimental.Accessors;
import org.softwarevax.framework.tomcat.http.enums.StatusCode;

@Data
@Accessors(chain = true)
public class DefaultHttpResponse implements HttpResponse {

    private String protocol;

    private String protocolVersion;

    private StatusCode statusCode;

    private String url;

    private String contentType;

    private String body;
}
