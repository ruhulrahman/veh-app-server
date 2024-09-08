package com.ibas.brta.vehims.payload;

/**
 * To encapsulate a summary of a user's information
 *
 * @author ashshakur.rahaman
 * @version 1.0 Initial Version
 */
public class UserSummary {
    private Long id;
    private String username;
    private String nameEn;
    private String nameBn;
    private String email;

    public UserSummary(Long id, String username, String nameEn, String nameBn, String email) {
        this.id = id;
        this.username = username;
        this.nameEn = nameEn;
        this.nameBn = nameBn;
        this.email = email;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameBn() {
        return nameBn;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setNameBn(String nameBn) {
        this.nameBn = nameBn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
