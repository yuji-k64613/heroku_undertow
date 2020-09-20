package com.sample;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.Headers;

import io.undertow.util.HttpString;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;

public class SimpleHttpHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HeaderMap resHeaders = exchange.getResponseHeaders();
            final String requestPath = exchange.getRequestPath();
            final String queryString = exchange.getQueryString();

            String url = "http://python.org";
            url = url + requestPath;
            if (queryString.length() > 0){
                url = url + "?" + queryString;
            }

            HttpGet httpGet = new HttpGet(url);

            HeaderMap reqHeaders = exchange.getRequestHeaders();
            reqHeaders.forEach(item -> {
                String name = item.getHeaderName().toString();
                if (name.equals("Host")) {
                    return;
                }
                if (name.equals("Referer")) {
                    return;
                }
                for (Iterator<String> it = item.iterator(); it.hasNext();) {
                    httpGet.setHeader(name, it.next());
                }
            });

            try (final CloseableHttpResponse response = httpclient.execute(httpGet)) {
                final HttpEntity entity = response.getEntity();

                Header[] hearders = response.getHeaders();
                Arrays.stream(hearders).forEach(item -> {
                    resHeaders.put(new HttpString(item.getName()), item.getValue());
                });

                byte[] bytes = EntityUtils.toByteArray(entity);
                ByteBuffer buffer = ByteBuffer.wrap(bytes);

                exchange.getResponseSender().send(buffer);
            }
        }
    }
}
