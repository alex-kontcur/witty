/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.consumer;

import com.witty.error.ServerExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

/**
 * Application - spring boot starter application for consumer server with AnnotationConfiguration
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@SpringBootApplication
@Configuration
@PropertySource("classpath*:/consumer.properties")
//Here we using shared xml configurations from infrastructure module
@ImportResource("classpath*:META-INF/spring/server-context.xml")
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServerExceptionHandler getServerExceptionHandler() {
        return new ServerExceptionHandler();
    }
}
