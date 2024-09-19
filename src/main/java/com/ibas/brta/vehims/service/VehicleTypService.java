package com.ibas.brta.vehims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.model.VehicleType;
import com.ibas.brta.vehims.payload.request.VehicleTypeDTO;
import com.ibas.brta.vehims.repository.VehicleTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VehicleTypService {
    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    // Create or Insert operation
    public VehicleType createVehilcleType(VehicleTypeDTO vehicleTypeDTO) {
        VehicleType vehicleType = new VehicleType();
        BeanUtils.copyProperties(vehicleTypeDTO, vehicleType);
        return vehicleTypeRepository.save(vehicleType);
    }

    // Update operation
    public VehicleType updateVehilcleType(Long id, VehicleTypeDTO vehicleTypeDTO) {
        Optional<VehicleType> existingData = vehicleTypeRepository.findById(id);
        if (existingData.isPresent()) {
            VehicleType vehicleType = existingData.get();
            BeanUtils.copyProperties(vehicleTypeDTO, vehicleType, "id", "versionNo"); // Exclude ID
            return vehicleTypeRepository.save(vehicleType);
        } else {
            throw new EntityNotFoundException("Status Group not found with id: " + id);
        }
    }

    // Delete operation
    public void deleteVehilcleType(Long id) {
        if (vehicleTypeRepository.existsById(id)) {
            vehicleTypeRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Status Group not found with id: " + id);
        }
    }

    // List all records
    public List<VehicleType> listAllVehilcleTypes() {
        return vehicleTypeRepository.findAll();
    }

    // Find a single record by ID
    public VehicleType getVehilcleTypeById(Long id) {
        return vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status Group not found with id: " + id));
    }
}
