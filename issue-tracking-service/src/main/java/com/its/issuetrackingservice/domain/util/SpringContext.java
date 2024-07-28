package com.its.issuetrackingservice.domain.util;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@NonNullApi
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    public static <T>ObjectProvider<T> getBeanProvider(Class<T> beanClass) {
        return applicationContext.getBeanProvider(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    private static synchronized void setContext(ApplicationContext applicationContext) {
        SpringContext.applicationContext = applicationContext;
    }
}
