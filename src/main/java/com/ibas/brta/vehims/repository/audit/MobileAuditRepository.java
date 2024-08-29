package com.ibas.brta.vehims.repository.audit;

import com.ibas.brta.vehims.model.audit.MobileAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface MobileAuditRepository extends JpaRepository<MobileAudit, Long> {
}

