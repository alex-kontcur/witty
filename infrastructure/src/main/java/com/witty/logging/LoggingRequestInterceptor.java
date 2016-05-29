/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.logging;

/**
 * LoggingRequestInterceptor - interceptor for logging of REST requests
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        String responseInfo = response.getStatusCode() + " : " +  response.getStatusText();
        logger.info("[RestTemplate] Request -> {}, Response -> {}", request.getURI(), responseInfo);
        return response;
    }
}
