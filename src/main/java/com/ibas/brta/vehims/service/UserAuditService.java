package com.ibas.brta.vehims.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.model.userManagement.User;
import com.ibas.brta.vehims.model.audit.EmailAudit;
import com.ibas.brta.vehims.model.audit.MobileAudit;
import com.ibas.brta.vehims.repository.audit.EmailAuditRepository;
import com.ibas.brta.vehims.repository.audit.MobileAuditRepository;

@Service
public class UserAuditService {

    @Autowired
    private EmailAuditRepository emailAuditRepository;

    @Autowired
    private MobileAuditRepository mobileAuditRepository;

    public void handleEmailAudit(User user) {
        if (user.getPreviousEmail() != null && !user.getPreviousEmail().equals(user.getEmail())) {
            EmailAudit emailAudit = new EmailAudit();
            emailAudit.setUserId(user.getId());
            emailAudit.setOldEmail(user.getPreviousEmail());
            emailAudit.setNewEmail(user.getEmail());
            emailAudit.setChangedAt(LocalDateTime.now());
            emailAudit.setChangedBy(user.getUsername());
            emailAuditRepository.save(emailAudit);
        }
    }

    public void handleMobileAudit(User user) {
        if (user.getPreviousMobile() != null && !user.getPreviousMobile().equals(user.getMobile())) {
            MobileAudit mobileAudit = new MobileAudit();
            mobileAudit.setUserId(user.getId());
            mobileAudit.setOldMobile(user.getPreviousMobile());
            mobileAudit.setNewMobile(user.getMobile());
            mobileAudit.setChangedAt(LocalDateTime.now());
            mobileAudit.setChangedBy(user.getUsername());
            mobileAuditRepository.save(mobileAudit);
        }
    }
}
