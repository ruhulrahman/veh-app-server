package com.ibas.brta.vehims.userManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NidModel {
    @JsonProperty("operationResult")
    private OperationResult operationResult;
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("serviceId")
    private String serviceId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nameEn")
    private String nameEn;
    @JsonProperty("nid")
    private String nid;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("permanentAddress")
    private String permanentAddress;
    @JsonProperty("presentAddress")
    private String presentAddress;
    @JsonProperty("photo")
    private String photo;
    @JsonProperty("spouse")
    private String spouse;
    @JsonProperty("father")
    private String father;
    @JsonProperty("mother")
    private String mother;
    @JsonProperty("religion")
    private String religion;
    @JsonProperty("sm")
    private Sm sm;

    @JsonProperty("username")
    private String username;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("confirmPassword")
    private String confirmPassword;
    @JsonProperty("otp")
    private String otp;

    // Getters and setters

    public static class OperationResult {
        @JsonProperty("success")
        private String success;

        // Getters and setters
    }

    public static class Sm {
        @JsonProperty("return")
        private ReturnInfo returnInfo;

        public ReturnInfo getReturnInfo() {
            return returnInfo;
        }

        public void setReturnInfo(ReturnInfo returnInfo) {
            this.returnInfo = returnInfo;
        }

        public static class ReturnInfo {
            @JsonProperty("operationResult")
            private OperationResult operationResult;
            @JsonProperty("requestId")
            private String requestId;
            @JsonProperty("serviceId")
            private String serviceId;
            @JsonProperty("nid")
            private String nid;
            @JsonProperty("pin")
            private String pin;

            // Getters and setters
        }
    }
}
