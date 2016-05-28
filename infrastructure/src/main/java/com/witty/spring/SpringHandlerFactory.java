/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.witty.spring;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * SpringHandlerFactory
 *
 * @author Alexander Kontsur (bona)
 * @since 28.05.2016
 */
public class SpringHandlerFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Handler createSpringMVCHandler(String servletConfigLocation, String applicationPath) {
        ServletContextHandler servletContext = new ServletContextHandler(null, "/", true, true);
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setContextConfigLocation(servletConfigLocation);
        dispatcherServlet.setContextInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
            @Override
            public void initialize(ConfigurableApplicationContext applicationContext) {
                applicationContext.setParent(SpringHandlerFactory.this.applicationContext);
            }
        });
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        servletContext.addServlet(servletHolder, applicationPath);
        return servletContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}