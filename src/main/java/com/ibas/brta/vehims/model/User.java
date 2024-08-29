package com.ibas.brta.vehims.model;

import com.ibas.brta.vehims.listener.UserListener;
import com.ibas.brta.vehims.model.audit.DateAudit;
import com.ibas.brta.vehims.model.rbac.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
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
@EqualsAndHashCode(callSuper=false)

public class User extends DateAudit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column( name = "name_en", nullable = false)
    private String name;

    @NotBlank
    @Size(max = 40)
    @Column(name = "name_bn", nullable = true)
    private String altName;

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

    @Column (name ="designation_id", nullable = false)
    private Long designationId;

    @Column (name ="profile_completed", nullable = false)
    private Boolean isProfile;

    @Column (name ="is_active", nullable = false)
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "x_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User (String name, String altName, String username, String email, String mobile, String password, Long designationId, Boolean isProfile, Boolean isActive){
        this.name = name;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.designationId = designationId;
        this.isProfile = isProfile;
        this.isActive = isActive;
        this.altName = altName;

    }
    public void setEmail(String email) {
        if (!Objects .equals(this.email, email)) {
            this.previousEmail = this.email; // Store the old email before changing
            this.email = email;
        }
    }
    public void setMobile(String mobile) {
        if (!Objects .equals(this.mobile, mobile)) {
            this.previousMobile = this.mobile; // Store the old Mobile before changing.
            this.mobile = mobile;
        }
    }
}