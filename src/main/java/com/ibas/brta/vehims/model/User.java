package com.ibas.brta.vehims.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibas.brta.vehims.listener.UserListener;
import com.ibas.brta.vehims.model.audit.DateAudit;
import com.ibas.brta.vehims.model.rbac.Role;
import com.ibas.brta.vehims.security.UserPrincipal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
// @AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "user_id") // Use user_id in the database, but expose as id in
    // Java
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotBlank
    @Size(max = 40)
    @Column(name = "name_bn", nullable = true)
    private String nameBn;

    @NaturalId
    @NotBlank
    @Size(max = 15)
    @Column(nullable = false)
    private String username;

    @NotBlank
    @Size(max = 13)
    @Column(nullable = false)
    private String mobile;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false)
    private String email;

    @Transient
    private String previousEmail;

    @Transient
    private String previousMobile;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 100)
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @NotNull(message = "User Type cannot be null")
    @Column(name = "user_type_id", nullable = false)
    private Long userTypeId;

    @NotNull(message = "Designation cannot be null")
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

    public User(String nameEn, String nameBn, String username, String email, String mobile,
            Long userTypeId,
            Long designationId, Boolean isActive) {
        this.nameEn = nameEn;
        this.nameBn = nameBn;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.userTypeId = userTypeId;
        this.designationId = designationId;
        this.isActive = isActive;
    }

    public User(String nameEn, String nameBn, String username, String email, String mobile, String password,
            Long userTypeId,
            Long designationId, Boolean isProfileCompleted, Boolean isActive) {
        this.nameEn = nameEn;
        this.nameBn = nameBn;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.userTypeId = userTypeId;
        this.designationId = designationId;
        this.isProfileCompleted = isProfileCompleted;
        this.isActive = isActive;
    }

    public User(String nameEn, String nameBn, String username, String email, String mobile, String password,
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

    @CreatedBy
    @Column(name = "create_user_id")
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "update_user_id")
    private Long updatedBy;

    @PrePersist
    public void prePersist() {
        // setCreatedBy(getLoggedinUserId());
        Long createdUserId = getLoggedinUserId();
        this.createdBy = createdUserId;
    }

    @PreUpdate
    public void preUpdate() {
        // setUpdatedBy(getLoggedinUserId());
        Long updateUserId = getLoggedinUserId();
        this.updatedBy = updateUserId;
    }

    private Long getLoggedinUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getId();
        }
        return null;
    }
}