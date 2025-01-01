package com.ibas.brta.vehims.configurations.model;

import java.util.Date;

import com.ibas.brta.vehims.common.model.audit.RecordAudit;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "c_element_data")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

@AttributeOverride(name = "id", column = @Column(name = "element_data_id"))

public class ElementData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_segment_id")
    private Integer subSegmentId;

    @Column(name = "element_data_code")
    private String code;

    @Column(name = "element_data_block_code")
    private String blockCode;

    @Column(name = "element_data_name_en")
    private String nameEn;

    @Column(name = "element_data_name_bn")
    private String nameBn;

    @Column(name = "element_data_desc")
    private String description;

    @Column(name = "reference_status_id")
    private Integer referenceStatusId;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_user_id")
    private Long updateUserId;

    // @Column(name = "created_date")
    // private Date createdDate;

    // @Column(name = "updated_date")
    // private Date updatedDate;
}
