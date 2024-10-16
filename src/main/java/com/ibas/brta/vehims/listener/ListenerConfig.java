package com.ibas.brta.vehims.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import com.ibas.brta.vehims.service.UserAuditService;

@Configuration
public class ListenerConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UserListener.setUserAuditService(applicationContext.getBean(UserAuditService.class));
    }
}