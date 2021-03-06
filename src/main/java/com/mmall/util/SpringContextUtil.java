package com.mmall.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 可以方便的获取想要的spring初始化的bean
 *
 * Created by luzy on 2017/11/22.
 */
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        System.out.println("init SpringContextUtil ... yelldo");
        SpringContextUtil.context = context;
    }
    public static ApplicationContext getContext(){
        return context;
    }

    public static Object getBean(String beanName) {
        return getContext().getBean(beanName);
    }
}
