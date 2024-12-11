package com.ibas.brta.vehims.userManagement.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibas.brta.vehims.exception.FieldValidationException;
import com.ibas.brta.vehims.userManagement.model.NidModel;
import com.ibas.brta.vehims.userManagement.model.UserNidInfo;
import com.ibas.brta.vehims.userManagement.repository.UserNidInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NidService {

    @Autowired
    private UserNidInfoRepository userNidInfoRepository;

    public boolean checkNidExist(String nationalId, String dob) {

        UserNidInfo nidInfo = userNidInfoRepository.findByNidNumber(nationalId);

        // Map<String, String> errors = new HashMap<>();
        // errors.put("message", "NID number already exists");
        // throw new FieldValidationException(errors);

        if (nidInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public NidModel getPersonDetailsFromNIDTest(String nationalId, String dob) {

        String jsonResponse = "{\n" +
                "    \"operationResult\": { \"success\": \"true\" },\n" +
                "    \"requestId\": \"1f0195bc-a295-4f8d-a645-0dd509201c42\",\n" +
                "    \"serviceId\": null,\n" +
                "    \"name\": \"মোঃ শফিকুল ইসলাম\",\n" +
                "    \"nameEn\": \"Md. Shafiqul Islam\",\n" +
                "    \"nid\": \"1471752053\",\n" +
                "    \"dob\": \"1992-07-26\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"mobile\": \"01771752053\",\n" +
                "    \"permanentAddress\": \"বাসা/হোল্ডিং: ৬৩, গ্রাম/রাস্তা: নাসির উদ্দিন সর্দার লেন, ডাকঘর: ঢাকা সদর-১১০০, উপজেলা: সুত্রাপুর, জেলা: ঢাকা, বিভাগ: ঢাকা\",\n"
                +
                "    \"presentAddress\": \"বাসা/হোল্ডিং: ৬৩, গ্রাম/রাস্তা: নাসির উদ্দিন সর্দার লেন, ডাকঘর: ঢাকা সদর-১১০০, উপজেলা: সুত্রাপুর, জেলা: ঢাকা, বিভাগ: ঢাকা\",\n"
                +
                "    \"photo\": null,\n" +
                "    \"spouse\": null,\n" +
                "    \"father\": \"মোঃ হারুন উর রশিদ\",\n" +
                "    \"mother\": \"*\",\n" +
                "    \"religion\": \"muslim\",\n" +
                "    \"username\": \"\",\n" +
                "    \"email\": \"\",\n" +
                "    \"password\": \"\",\n" +
                "    \"confirmPassword\": \"\",\n" +
                "    \"otp\": \"\",\n" +
                "    \"sm\": {\n" +
                "        \"return\": {\n" +
                "            \"operationResult\": { \"success\": \"true\" },\n" +
                "            \"requestId\": \"1f0195bc-a295-4f8d-a645-0dd509201c42\",\n" +
                "            \"serviceId\": null,\n" +
                "            \"nid\": \"1471752053\",\n" +
                "            \"pin\": \"19922698842000221\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("Response from NID: {}", jsonResponse);
            log.info("Response from NID test: {}", objectMapper.readValue(jsonResponse, NidModel.class));
            return objectMapper.readValue(jsonResponse, NidModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
