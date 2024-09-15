package com.ibas.brta.vehims.payload.response;

import java.util.*;
import com.ibas.brta.vehims.model.Designation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonDropdownResponse {

    public List<Designation> designationList;
    public List<Designation> countryList;
    public List<Designation> bloodList;
    public List<Designation> genderList;
    public List<Designation> documentTypeList;
    public List<Designation> fuelTypeList;
    public List<Designation> userTypeList;
    public List<Designation> serviceList;
    public List<Designation> vehicleTypeList;
    public List<Designation> vehicleColorList;
}
