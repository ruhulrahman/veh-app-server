package com.ibas.brta.vehims.drivingLicense.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DLSearchRequest {
    String serviceRequestNo;
    String nid;
    String learnerNo;
    String mobile;
    Long applicantId;
    Long orgId;
    Long inspectorId;
    Date applicationDate;
    Long applicationStatusId;
    Pageable pageable;

}