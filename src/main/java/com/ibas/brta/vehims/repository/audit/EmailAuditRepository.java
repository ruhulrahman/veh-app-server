package com.ibas.brta.vehims.repository.audit;

import com.ibas.brta.vehims.model.audit.EmailAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAuditRepository extends JpaRepository<EmailAudit, Long> {
}
