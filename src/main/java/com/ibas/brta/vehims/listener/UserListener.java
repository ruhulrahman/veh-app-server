package com.ibas.brta.vehims.listener;

import com.ibas.brta.vehims.model.userManagement.User;
import com.ibas.brta.vehims.service.UserAuditService;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserListener {

    private static UserAuditService userAuditService;

    @Autowired
    public static void setUserAuditService(UserAuditService userAuditService) {
        UserListener.userAuditService = userAuditService;
    }

    @PreUpdate
    public void preUpdate(User user) {
        userAuditService.handleEmailAudit(user);
        userAuditService.handleMobileAudit(user);
    }
}
