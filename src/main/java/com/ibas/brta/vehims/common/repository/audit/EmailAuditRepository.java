package com.ibas.brta.vehims.common.repository.audit;

import com.ibas.brta.vehims.common.model.audit.EmailAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailAuditRepository extends JpaRepository<EmailAudit, Long> {
}
