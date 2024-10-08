package com.ibas.brta.vehims.listener;

import com.ibas.brta.vehims.model.SUser;
import com.ibas.brta.vehims.model.User;
import com.ibas.brta.vehims.model.audit.EmailAudit;
import com.ibas.brta.vehims.model.audit.MobileAudit;
import com.ibas.brta.vehims.repository.audit.EmailAuditRepository;
import com.ibas.brta.vehims.repository.audit.MobileAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UserListener {

    @Autowired
    private EmailAuditRepository emailAuditRepository;
    @Autowired
    private MobileAuditRepository mobileAuditRepository;

    // @Autowired
    // public UserListener(EmailAuditRepository emailAuditRepository) {
    // this.emailAuditRepository = emailAuditRepository;
    // }

    // @Autowired
    // public UserListener(MobileAuditRepository mobileAuditRepository) {
    // this.mobileAuditRepository = mobileAuditRepository;
    // }

    @PreUpdate
    public void preUpdate(SUser user) {

        log.info("user.getPreviousEmail() ========= {}", user.getPreviousEmail());
        log.info("user.getPreviousMobile() ========= {}", user.getPreviousMobile());

        if (user.getPreviousEmail() != null && !user.getPreviousEmail().equals(user.getEmail())) {
            EmailAudit emailAudit = new EmailAudit();
            emailAudit.setUserId(user.getId());
            emailAudit.setOldEmail(user.getPreviousEmail());
            emailAudit.setNewEmail(user.getEmail());
            emailAudit.setChangedAt(LocalDateTime.now());
            emailAudit.setChangedBy(user.getUsername());
            log.info("emailAudit ========= {}", emailAudit);
            emailAuditRepository.save(emailAudit);

            // if (this.emailAuditRepository != null) {
            // this.emailAuditRepository.save(emailAudit);
            // } else {
            // log.error("EmailAuditRepository is null!");
            // }
        }

        if (user.getPreviousMobile() != null && !user.getPreviousMobile().equals(user.getMobile())) {

            MobileAudit mobileAudit = new MobileAudit();
            mobileAudit.setUserId(user.getId());
            mobileAudit.setOldMobile(user.getPreviousMobile());
            mobileAudit.setNewMobile(user.getMobile());
            mobileAudit.setChangedAt(LocalDateTime.now());
            mobileAudit.setChangedBy(user.getUsername());

            log.info("mobileAudit ========= {}", mobileAudit);
            mobileAuditRepository.save(mobileAudit);

            // if (this.mobileAuditRepository != null) {
            // this.mobileAuditRepository.save(mobileAudit);
            // } else {
            // log.error("EmailAuditRepository is null!");
            // }
        }
    }
}
