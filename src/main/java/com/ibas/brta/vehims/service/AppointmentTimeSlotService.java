package com.ibas.brta.vehims.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.model.AppointmentTimeSlot;
import com.ibas.brta.vehims.model.NotificationTemplate;
import com.ibas.brta.vehims.model.ServiceEntity;
import com.ibas.brta.vehims.payload.request.AppointmentTimeSlotRequest;
import com.ibas.brta.vehims.payload.request.AppointmentTimeSlotRequest;
import com.ibas.brta.vehims.payload.response.AppointmentTimeSlotResponse;
import com.ibas.brta.vehims.payload.response.AppointmentTimeSlotResponse;
import com.ibas.brta.vehims.payload.response.PagedResponse;
import com.ibas.brta.vehims.payload.response.ServiceEntityResponse;
import com.ibas.brta.vehims.repository.AppointmentTimeSlotRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppointmentTimeSlotService {

    @Autowired
    AppointmentTimeSlotRepository appointmentTimeSlotRepository;

    // Create or Insert operation
    public AppointmentTimeSlotResponse createData(AppointmentTimeSlotRequest request) {

        AppointmentTimeSlot requestObject = new AppointmentTimeSlot();
        BeanUtils.copyProperties(request, requestObject);
        AppointmentTimeSlot savedData = appointmentTimeSlotRepository.save(requestObject);

        AppointmentTimeSlotResponse response = new AppointmentTimeSlotResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Update operation
    public AppointmentTimeSlotResponse updateData(Long id, AppointmentTimeSlotRequest request) {

        Optional<AppointmentTimeSlot> existingData = appointmentTimeSlotRepository.findById(id);

        if (existingData.isPresent()) {
            AppointmentTimeSlot requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            AppointmentTimeSlot updatedData = appointmentTimeSlotRepository.save(requestObject);

            AppointmentTimeSlotResponse response = new AppointmentTimeSlotResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteData(Long id) {
        if (appointmentTimeSlotRepository.existsById(id)) {
            appointmentTimeSlotRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    // List all records
    public PagedResponse<AppointmentTimeSlotResponse> findAllBySearch(String slotNameEn, Boolean isActive, int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);
        // Retrieve all records from the database
        Page<AppointmentTimeSlot> records = appointmentTimeSlotRepository.findListWithPaginationBySearch(slotNameEn,
                isActive,
                pageable);
        if (records.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), records.getNumber(), records.getSize(),
                    records.getTotalElements(), records.getTotalPages(), records.isLast());
        }

        // Map Responses with all information
        List<AppointmentTimeSlotResponse> responseData = records.map(record -> {
            AppointmentTimeSlotResponse response = new AppointmentTimeSlotResponse();
            BeanUtils.copyProperties(record, response);
            return response;
        }).getContent();

        return new PagedResponse<>(responseData, records.getNumber(),
                records.getSize(), records.getTotalElements(), records.getTotalPages(), records.isLast());
    }

    // Find a single record by ID
    public AppointmentTimeSlotResponse getDataById(Long id) {

        AppointmentTimeSlot existingData = appointmentTimeSlotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        AppointmentTimeSlotResponse response = new AppointmentTimeSlotResponse();
        BeanUtils.copyProperties(existingData, response);

        return response;
    }

    public List<?> getActiveList() {
        List<AppointmentTimeSlot> listData = appointmentTimeSlotRepository.findByIsActiveTrueOrderBySlotNameEnAsc();

        List<Map<String, Object>> customArray = new ArrayList<>();

        listData.forEach(serviceEntity -> {
            // Access and process each entity's fields
            Map<String, Object> object = new HashMap<>();
            object.put("id", serviceEntity.getId());
            object.put("slotNameEn", serviceEntity.getSlotNameEn());
            object.put("slotNameBn", serviceEntity.getSlotNameBn());
            object.put("startTime", serviceEntity.getSlotStartTime());
            object.put("endTime", serviceEntity.getSlotEndTime());
            customArray.add(object);
        });

        return customArray;
    }
}
