package com.sample;

import javax.servlet.ServletException;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.RequestDumpingHandler;
import io.undertow.server.handlers.accesslog.AccessLogHandler;
import io.undertow.util.Headers;
import io.undertow.servlet.Servlets;
import io.undertow.Handlers;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.examples.servlet.ServletServer;
import io.undertow.examples.servlet.MessageServlet;
import org.apache.commons.lang3.StringUtils;

public class HelloWorldServer {
    public static void main(final String[] args) {
        HttpHandler handler0 = new SimpleHttpHandler();
        HttpHandler handler1 = new SetHeaderHandler(handler0, "", "");
        HttpHandler handler2 = new RequestDumpingHandler(handler1);

        int port = 8080;
        String portStr = System.getenv("PORT");
        if (!StringUtils.isEmpty(portStr)){
            port = Integer.parseInt(portStr);
        }

        Undertow server = Undertow.builder()
                .addHttpListener(port,"0.0.0.0")
                .setHandler(handler2).build();
        server.start();
    }
}
