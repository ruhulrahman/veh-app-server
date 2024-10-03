package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.listener.UserListener;
import com.ibas.brta.vehims.model.audit.RecordAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "s_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        }),
        @UniqueConstraint(columnNames = {
                "mobile"
        })
})

@Data
@NoArgsConstructor
@EntityListeners(UserListener.class)
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))

public class SUser extends RecordAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name in English cannot be blank")
    @Size(max = 60)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank(message = "Name in Bangla cannot be blank")
    @Size(max = 60)
    @Column(name = "name_bn", nullable = true)
    private String nameBn;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 15)
    @Column(nullable = false)
    private String username;

    @NaturalId
    @NotBlank(message = "Mobile cannot be blank")
    @Size(max = 13)
    @Column(nullable = false)
    private String mobile;

    @NaturalId
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 60)
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 100)
    @Column(nullable = false)
    private String password;

    @NotNull(message = "User Type cannot be null")
    @Column(name = "user_type_id", nullable = false)
    private Long userTypeId;

    @NotNull(message = "Designation cannot be null")
    @Column(name = "designation_id", nullable = false)
    private Long designationId;

    @Column(name = "profile_completed", nullable = false)
    private Boolean isProfileCompleted;

    // @Transient
    // private String previousEmail;

    // @Transient
    // private String previousMobile;

    // public void setEmail(String email) {
    // if (!Objects.equals(this.email, email)) {
    // this.previousEmail = this.email; // Store the old email before changing
    // this.email = email;
    // }
    // }

    // public void setMobile(String mobile) {
    // if (!Objects.equals(this.mobile, mobile)) {
    // this.previousMobile = this.mobile; // Store the old Mobile before changing.
    // this.mobile = mobile;
    // }
    // }

}