package com.ibas.brta.vehims.payload.response;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StatusGroupResponse implements Serializable {
    private Long id;
    private String statusGroupCode;
    private String nameEn;
    private String nameBn;
    private Boolean isActive;
    // private List<StatusResponse> statuses;
}
