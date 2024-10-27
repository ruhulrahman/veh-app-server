package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.RegistrationApplicationResponse;
import com.ibas.brta.vehims.projection.RegistrationApplications;

import java.util.List;

/**
 * @author ashshakur.rahaman
 */
public interface IRegistrationApplication {
   public PagedResponse<RegistrationApplicationResponse> searchVehRegApplications(int page, int size, String serviceRequestNo, String chassisNumber, String engineNumber, String nid, String mobile , String applicationDate);
}


