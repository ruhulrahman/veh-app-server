package com.ibas.brta.vehims.payload.request;

import com.ibas.brta.vehims.model.audit.RecordAudit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FDrivingRelatedServiceFeeRequest {
    private Long id;

    private Long serviceId;

    private Boolean isYearlyFee = false;

    private Integer mainFee;

    private Integer lateFee;

    private Short vatPercentage;

    private Short sdPercentage;

    private LocalDateTime effectiveStartDate;

    private LocalDateTime effectiveEndDate;

    private Boolean isActive = false;

    private Long createUserId;

    private Long updateUserId;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private LocalDateTime deletedDate;

    private Integer versionNo;

}