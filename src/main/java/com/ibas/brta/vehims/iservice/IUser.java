package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.common.payload.response.PagedResponse;
import com.ibas.brta.vehims.userManagement.payload.response.UserResponse;

public interface IUser {
    public PagedResponse<UserResponse> getAll(int page, int size, String searchby);
}