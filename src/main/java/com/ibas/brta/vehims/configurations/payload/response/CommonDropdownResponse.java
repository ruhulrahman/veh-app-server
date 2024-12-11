package com.ibas.brta.vehims.configurations.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonDropdownResponse {

    public List<?> designationList;
    public List<?> countryList;
    public List<?> bloodList;
    public List<?> genderList;
    public List<?> documentTypeList;
    public List<?> fuelTypeList;
    public List<?> userTypeList;
    public List<?> serviceList;
    public List<?> vehicleTypeList;
    public List<?> vehicleColorList;
    public List<?> statusGroupList;
    public List<?> statusList;
    public List<?> fiscalYearList;
    public List<?> appointmentTimeSlotList;
    public List<?> locationTypeList;
    public List<?> routePermitTypes;
    public List<?> paymentStatusList;
    public List<?> organizationList;
    public List<?> officeTypeList;
    public List<?> divisionList;
    public List<?> locationList;
    public List<?> userTypes;
    public List<?> orgList;
    public List<?> ownerTypeList;
    public List<?> userList;
    public List<?> revenueCheckStatusList;
    public List<?> inspectionStatusList;
    public List<?> vehicleApplicationCheckStatusList;
}
