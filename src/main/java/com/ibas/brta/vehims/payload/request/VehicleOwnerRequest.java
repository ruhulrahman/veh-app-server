package com.ibas.brta.vehims.payload.request;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.util.Date;

@Data
public class VehicleOwnerRequest {

    @NotNull(message = "Vehicle Info ID is required")
    private Long vehicleInfoId;

    @NotNull(message = "Service Request ID is required")
    private Long serviceRequestId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name can be up to 100 characters long")
    private String name;

    @NotBlank(message = "Father or Husband Name is required")
    @Size(max = 100, message = "Father or Husband Name can be up to 100 characters long")
    private String fatherOrHusbandName;

    @NotBlank(message = "Mother Name is required")
    @Size(max = 100, message = "Mother Name can be up to 100 characters long")
    private String motherName;

    @NotNull(message = "Gender ID is required")
    private Long genderId;

    @NotNull(message = "Nationality ID is required")
    private Long nationalityId;

    @Size(max = 100, message = "Guardian Name can be up to 100 characters long")
    private String guardianName;

    @Size(max = 100, message = "Passport No can be up to 100 characters long")
    private String passportNo;

    @Size(max = 100, message = "Birth Certificate No can be up to 100 characters long")
    private String birthCertificateNo;

    @NotNull(message = "Address ID is required")
    private Long addressId;

    @NotNull(message = "Is Recondition is required")
    private Boolean isRecondition = false;

    @NotNull(message = "Is Joint Owner is required")
    private Boolean isJointOwner = false;

    @NotNull(message = "Owner Type ID is required")
    private Long ownerTypeId;

    private Long ministryId;
    private Long departmentId;
    private Long subOfficeUnitGroupId;
    private Long unitOrActivityId;

    private Boolean inUse = true;
    private Long usedById;
    private Boolean withinOrganogram;
    private Long acquisitionProcessId;
    private String acquisitionOffice;
    private Long acquisitionPrice;

    private Date dateOfReceipt;
    private String remarks;

    @NotNull(message = "Is Primary Owner is required")
    private Boolean isPrimaryOwner = true;

}
