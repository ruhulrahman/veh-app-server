package com.ibas.brta.vehims.userManagement.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OtpService {

    // Twilio credentials
    private static final String ACCOUNT_SID = "YOUR_ACCOUNT_SID";
    private static final String AUTH_TOKEN = "YOUR_AUTH_TOKEN";
    private static final String FROM_PHONE_NUMBER = "YOUR_TWILIO_PHONE_NUMBER";

    private Map<String, String> otpData = new HashMap<>();

    String otp = "4150";
    String phoneNumber = "01771752053";

    // public OtpService() {
    // Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    // }

    public String generateOtp(String phoneNumber) {
        // String otp = String.format("%04d", new Random().nextInt(10000)); // Generate
        // a 4-digit OTP
        otpData.put(phoneNumber, otp);

        // Message.creator(
        // new com.twilio.type.PhoneNumber(phoneNumber),
        // new com.twilio.type.PhoneNumber(FROM_PHONE_NUMBER),
        // "Your OTP is: " + otp).create();

        return otp;
    }

    public boolean validateOtp(String phoneNumber, String otp) {
        String savedOtp = otpData.get(phoneNumber);
        if (savedOtp != null && savedOtp.equals(otp)) {
            otpData.remove(phoneNumber); // OTP is valid, remove it from storage
            return true;
        }
        return false;
    }

}
