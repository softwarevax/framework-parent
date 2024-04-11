package org.softwarevax.framework.test.httptest;

public class Test {

    public static void main(String[] args) {
        HttpInvoke httpInvoke = new HttpInvoke() {
            @Override
            public Object invoke(RequestEntity entity) {
                return "hello";
            }
        };

        BootstrapServer bootstrapServer = new BootstrapServer(8081, httpInvoke);
    }
}
