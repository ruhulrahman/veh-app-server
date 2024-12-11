package com.ibas.brta.vehims.common.repository.audit;

import com.ibas.brta.vehims.common.model.audit.MobileAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileAuditRepository extends JpaRepository<MobileAudit, Long> {
}
