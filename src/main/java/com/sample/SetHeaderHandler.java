package com.sample;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

public class SetHeaderHandler implements HttpHandler {
    private final HttpString header;
    private final String value;
    private final HttpHandler next;

    public SetHeaderHandler(final HttpHandler next, final String header, final String value) {
        this.next = next;
        this.value = value;
        this.header = new HttpString(header);
    }

    @Override
    public void handleRequest(final HttpServerExchange exchange) throws Exception {
        //exchange.getResponseHeaders().put(header, value);
        next.handleRequest(exchange);
    }
}
