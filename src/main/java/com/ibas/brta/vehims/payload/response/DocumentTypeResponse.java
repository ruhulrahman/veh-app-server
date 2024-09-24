package com.ibas.brta.vehims.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DocumentTypeResponse {
    private Long id;
    private String nameEn;
    private String nameBn;
    private String documentFormat;
    private Integer documentSize;
    private Boolean isActive;
}
