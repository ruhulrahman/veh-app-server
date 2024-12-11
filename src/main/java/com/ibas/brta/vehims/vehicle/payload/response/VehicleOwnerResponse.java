package com.ibas.brta.vehims.vehicle.payload.response;

import java.util.Date;

import lombok.Data;

@Data
public class VehicleOwnerResponse {
    private Long id;
    private Long vehicleInfoId;
    private Long serviceRequestId;
    private String name;
    private String fatherOrHusbandName;
    private String motherName;
    private Long genderId;
    private Long nationalityId;
    private String guardianName;
    private String passportNo;
    private String birthCertificateNo;
    private Long addressId;
    private Boolean isRecondition;
    private Boolean isJointOwner;
    private Long ownerTypeId;
    private Long ministryId;
    private Long departmentId;
    private Long subOfficeUnitGroupId;
    private Long unitOrActivityId;
    private Boolean inUse;
    private Long usedById;
    private Boolean withinOrganogram;
    private Long acquisitionProcessId;
    private String acquisitionOffice;
    private Long acquisitionPrice;
    private Date dateOfReceipt;
    private String remarks;
    private Boolean isPrimaryOwner;
}
