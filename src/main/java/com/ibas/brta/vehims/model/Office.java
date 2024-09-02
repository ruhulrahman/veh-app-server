package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.listener.UserListener;
import com.ibas.brta.vehims.model.audit.RecordAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 *
 */
@Entity
@Table(name = "c_organizations")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper=false)
@AttributeOverride(name="id", column=@Column(name="org_id"))
public class Office extends RecordAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name ="name_en")
    private String nameInEnglish;
    @Column (name ="name_bn")
    private String nameInBangla;
    @Column (name ="office_type_id")
    private Long officeTypeId;
    @Column (name = "parent_org_id")
    private Long parentId;
    @Column (name = "location_id")
    private Long locationId;
    @Column (name = "post_code")
    private String postCode;
    @Column (name = "address_en")
    private String addressInEnglish;
    @Column (name ="address_bn" )
    private String addressInBangla;
}
