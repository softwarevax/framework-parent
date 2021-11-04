package org.softwarevax.framework.tomcat;

import org.softwarevax.framework.tomcat.http.bootstrap.BootstrapServer;

public class TomcatApplication {

    public static void main(String[] args) {
        // http启动端口为8080
        new BootstrapServer(8080);
    }
}
