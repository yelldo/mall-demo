package com.mmall.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 随着web容器的初始化而初始化的各种资源
 * Created by luzy on 2017/12/11.
 */
public class StartInit implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
