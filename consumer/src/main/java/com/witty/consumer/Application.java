package com.witty.consumer;

import com.witty.error.ServerExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Application - spring boot starter application for consumer server with AnnotationConfiguration
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
@Configuration
@SpringBootApplication
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
