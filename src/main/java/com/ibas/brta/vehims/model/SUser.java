package com.ibas.brta.vehims.model;

import java.util.Objects;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibas.brta.vehims.model.audit.DateAudit;
import com.ibas.brta.vehims.security.UserPrincipal;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
// @EntityListeners(UserListener.class)
@EqualsAndHashCode(callSuper = false)

@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))

public class SUser extends DateAudit {
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

        @NaturalId
        @NotBlank(message = "Username cannot be blank")
        @Size(max = 15)
        @Column(nullable = false)
        private String username;

        @NotBlank(message = "Mobile cannot be blank")
        @Size(min = 11, max = 11, message = "Mobile number must be exactly 11 digits")
        @Column(nullable = false)
        private String mobile;

        @NotBlank(message = "Email cannot be blank")
        @Size(max = 60)
        @Column(nullable = false)
        private String email;

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

        public SUser(String nameEn, String nameBn, String username, String email, String mobile,
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

        public SUser(String nameEn, String nameBn, String username, String email, String mobile, String password,
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

        @Transient
        private String previousEmail;

        @Transient
        private String previousMobile;

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