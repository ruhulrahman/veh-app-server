package com.ibas.brta.vehims.iservice;

import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

public interface IUser {
    public PagedResponse<UserResponse> getAll(int page, int size, String searchby);
}