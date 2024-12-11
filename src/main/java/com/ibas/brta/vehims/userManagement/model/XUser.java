package com.ibas.brta.vehims.userManagement.model;

import com.ibas.brta.vehims.listener.UserListener;
import com.ibas.brta.vehims.common.model.audit.DateAudit;
import com.ibas.brta.vehims.common.model.rbac.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "x_users", uniqueConstraints = {
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
// @AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class XUser extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank
    @Size(max = 40)
    @Column(name = "name_bn", nullable = true)
    private String nameBn;

    @NotBlank
    @Size(max = 15)
    @Column(nullable = false)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 13)
    @Column(nullable = false)
    private String mobile;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Column(nullable = false)
    private String email;

    @Transient
    private String previousEmail;

    @Transient
    private String previousMobile;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String password;

    @Column(name = "designation_id", nullable = false)
    private Long designationId;

    @Column(name = "profile_completed", nullable = false)
    private Boolean isProfileCompleted;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "x_user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "x_user_roles", joinColumns = @JoinColumn(name = "id",
    // referencedColumnName = "user_id"))
    // private Set<Role> roles = new HashSet<>();

    public XUser(String nameEn, String nameBn, String username, String email, String mobile, String password,
            Long designationId, Boolean isProfileCompleted, Boolean isActive) {
        this.nameEn = nameEn;
        this.nameBn = nameBn;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.designationId = designationId;
        this.isProfileCompleted = isProfileCompleted;
        this.isActive = isActive;

    }

    public void setEmail(String email) {
        if (!Objects.equals(this.email, email)) {
            this.previousEmail = this.email; // Store the old email before changing
            this.email = email;
        }
    }

    public void setMobile(String mobile) {
        if (!Objects.equals(this.mobile, mobile)) {
            this.previousMobile = this.mobile; // Store the old Mobile before changing.
            this.mobile = mobile;
        }
    }
}