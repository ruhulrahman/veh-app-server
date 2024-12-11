package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.vehicle.payload.response.RegistrationApplicationResponse;

import java.util.Date;

/**
 * @author ashshakur.rahaman
 */
public interface IRegistrationApplication {
   public PagedResponse<RegistrationApplicationResponse> searchVehRegApplications(int page, int size, String serviceRequestNo,
                                                                                  String chassisNumber, String engineNumber, String nid,
                                                                                  String mobile , Date applicationDate, Long userId);
}


