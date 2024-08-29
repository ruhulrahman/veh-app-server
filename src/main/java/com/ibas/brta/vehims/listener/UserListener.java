package com.ibas.brta.vehims.listener;

import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.model.audit.EmailAudit;
import com.ibas.brta.vehims.model.audit.MobileAudit;
import com.ibas.brta.vehims.repository.audit.EmailAuditRepository;
import com.ibas.brta.vehims.repository.audit.MobileAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class UserListener {

    @Autowired
    private EmailAuditRepository emailAuditRepository;
    @Autowired
    private MobileAuditRepository mobileAuditRepository;

    @PreUpdate
    public void preUpdate(User user) {
        if (user.getPreviousEmail() != null && !user.getPreviousEmail().equals(user.getEmail())) {
            EmailAudit emailAudit = new EmailAudit();
            emailAudit.setUserId(user.getId());
            emailAudit.setOldEmail(user.getPreviousEmail());
            emailAudit.setNewEmail(user.getEmail());
            emailAudit.setChangedAt(LocalDateTime.now());
            emailAudit.setChangedBy(user.getUsername());
            emailAuditRepository.save(emailAudit);
        }
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
