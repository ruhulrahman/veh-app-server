package com.ibas.brta.vehims.userManagement.service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InMemoryOtpService {

    private static final long OTP_EXPIRATION_TIME_MS = 2 * 60 * 1000; // 2 minutes in milliseconds
    private static final int OTP_LENGTH = 6;

    // Stores OTP with key and expiration time
    private final Map<String, OtpDetails> otpCache = new ConcurrentHashMap<>();
    private final Random random = new Random();

    // Method to generate a new OTP and save it in memory
    public String generateOtp(String mobile) {
        String otp = String.format("%06d", random.nextInt((int) Math.pow(10, OTP_LENGTH)));
        otpCache.put(mobile, new OtpDetails(otp, Instant.now().plusMillis(OTP_EXPIRATION_TIME_MS)));
        return otp;
    }

    // Method to verify OTP
    public boolean verifyOtp(String mobile, String otp) {
        OtpDetails otpDetails = otpCache.get(mobile);

        if (otpDetails != null && otpDetails.getOtp().equals(otp)) {
            if (Instant.now().isBefore(otpDetails.getExpiryTime())) {
                otpCache.remove(mobile); // Remove OTP after successful verification
                return true;
            } else {
                otpCache.remove(mobile); // Remove expired OTP
            }
        }
        return false;
    }

    // Inner class to store OTP and expiration details
    private static class OtpDetails {
        private final String otp;
        private final Instant expiryTime;

        public OtpDetails(String otp, Instant expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public Instant getExpiryTime() {
            return expiryTime;
        }
    }
}
