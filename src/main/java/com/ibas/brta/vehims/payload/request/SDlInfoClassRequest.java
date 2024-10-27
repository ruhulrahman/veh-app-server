package com.ibas.brta.vehims.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SDlInfoClassRequest   {
    private Long id;

    private Long dlInfoId;

    private Long dlClass;

    private LocalDateTime createdDate;

}