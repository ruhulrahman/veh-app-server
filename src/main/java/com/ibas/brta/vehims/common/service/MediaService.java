package com.ibas.brta.vehims.common.service;

import java.net.MalformedURLException;
import java.util.Optional;

import com.ibas.brta.vehims.common.payload.response.MediaResult;
import com.ibas.brta.vehims.configurations.service.StatusService;
import com.ibas.brta.vehims.drivingLicense.repository.DLServiceMediaRepository;
import com.ibas.brta.vehims.util.Utils;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ibas.brta.vehims.common.model.Media;
import com.ibas.brta.vehims.common.payload.request.MediaRequest;
import com.ibas.brta.vehims.common.payload.response.MediaResponse;
import com.ibas.brta.vehims.common.repository.MediaRepository;
import com.ibas.brta.vehims.configurations.repository.CommonRepository;
import com.ibas.brta.vehims.vehicle.repository.VServiceMediaRepository;
import com.ibas.brta.vehims.vehicle.repository.VServiceRequestRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.nio.file.*;

@Slf4j
@Service
public class MediaService {

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    VServiceRequestRepository serviceRequestRepository;

    @Autowired
    CommonRepository commonRepository;

    @Autowired
    StatusService statusService;

    @Autowired
    DLServiceMediaRepository dlServiceMediaRepository;

    @Autowired
    VServiceMediaRepository vehicleServiceMediaRepository;

    private final String UPLOAD_DIR = "uploads/";
    private final String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    // Create or Insert operation
    public MediaResponse createData(MediaRequest request) {

        Media requestObject = new Media();
        BeanUtils.copyProperties(request, requestObject);
        Media savedData = mediaRepository.save(requestObject);

        MediaResponse response = new MediaResponse();
        BeanUtils.copyProperties(savedData, response);
        return response;
    }

    // Create or Insert operation
    public MediaResponse uploadMedia(MediaRequest request) {

        MultipartFile file = request.getAttachmentFile();

        // Extract original file extension
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // Generate a unique file name
        String uniqueFileName = Utils.generateUniqueFileName(originalFileName);

        // Define file path
        String filePath = System.getProperty("user.dir") + "/uploads" + File.separator + uniqueFileName;

        try {
            // Create and save the file
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(file.getBytes());
            fout.close();

            // Set metadata for the saved file
            String mimeType = file.getContentType();
            long fileSizeInBytes = file.getSize();

            MediaRequest requestObject = new MediaRequest();
            requestObject.setOriginalName(originalFileName); // Save the unique file name
            requestObject.setExtension(fileExtension);
            requestObject.setFolderPath("/uploads");
            requestObject.setFile(uniqueFileName); // Save unique name in DB
            requestObject.setSize(fileSizeInBytes);
            requestObject.setType(mimeType);

            // Call the method to save metadata in DB
            MediaResponse response = createData(requestObject);
            return response;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteMediaById(Long id) {

        Media existingData = mediaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        mediaRepository.deleteById(id);
    }

    // Update operation
    public MediaResponse updateData(Long id, MediaRequest request) {

        Optional<Media> existingData = mediaRepository.findById(id);

        if (existingData.isPresent()) {
            Media requestObject = existingData.get();
            BeanUtils.copyProperties(request, requestObject);

            Media updatedData = mediaRepository.save(requestObject);

            MediaResponse response = new MediaResponse();
            BeanUtils.copyProperties(updatedData, response);
            return response;
        } else {
            throw new EntityNotFoundException("Data not found with id: " + id);
        }
    }

    public String mediaUrl(Long id) {

        MediaResponse getDataById = getDataById(id);

        return getDataById.getFile();
    }

    public ResponseEntity<?> getMediaById(Long id) {

        MediaResponse getDataById = getDataById(id);

        try {
            // Build the path to the file
            Path filePath = Paths.get(uploadDirectory).resolve(getDataById.getFile()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Get file type
            String mimeType = Files.probeContentType(filePath);

            if (mimeType != null && mimeType.startsWith("image")) {
                // Return image as Base64 string
                byte[] fileBytes = Files.readAllBytes(filePath);
                String base64String = java.util.Base64.getEncoder().encodeToString(fileBytes);

                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(base64String);
            } else {
                // Unsupported file type
                return ResponseEntity.badRequest().body("Unsupported file type");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error retrieving file");
        }
    }



    public MediaResult getMediaByIdWithType(Long id) {
        // Fetch file information
        MediaResponse getDataById = getDataById(id);
        if (getDataById == null || getDataById.getFile() == null) {
            return new MediaResult(null, "File information not found");
        }

        // Build the path to the file
        Path filePath = Paths.get(uploadDirectory).resolve(getDataById.getFile()).normalize();

        // Check if the file exists
        if (!Files.exists(filePath)) {
            return new MediaResult(null, "File not found");
        }

        try {
            // Get file type
            String mimeType = Files.probeContentType(filePath);

            if (mimeType != null && mimeType.startsWith("image")) {
                // Read the file and return as Base64 string
                byte[] fileBytes = Files.readAllBytes(filePath);
                String base64String = java.util.Base64.getEncoder().encodeToString(fileBytes);

                return new MediaResult(base64String);
            } else {
                // Return null for unsupported file types
                return new MediaResult(null, "Unsupported file type");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new MediaResult(null, "Error processing file");
        }
    }

    // Find a single record by ID
    public MediaResponse getDataById(Long id) {

        Media existingData = mediaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with id: " + id));

        MediaResponse response = new MediaResponse();
        BeanUtils.copyProperties(existingData, response);

        // try {
        // Path filePath = Paths.get(UPLOAD_DIR + existingData.getFile()).normalize();
        // Resource resource = new UrlResource(filePath.toUri());
        // response.setResource(resource);
        // // handle resource
        // } catch (MalformedURLException e) {
        // // handle the exception
        // }

        return response;
    }

    // Find a single record by ID
    @Transactional
    public ResponseEntity<?> deleteDlServiceMediaId(Long mediaId) {

        Media existingData = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with mediaId: " + mediaId));

        try {
            // Build the path to the file
            Path filePath = Paths.get(uploadDirectory).resolve(existingData.getFile()).normalize();

            // Check if the file exists
            if (!Files.exists(filePath)) {
                return ResponseEntity.badRequest().body("File not found: " + existingData.getFile());
            }

            // Delete the file
            Files.delete(filePath);

            String message = "File deleted successfully: " + existingData.getFile();

            dlServiceMediaRepository.deleteByMediaId(mediaId);

            mediaRepository.deleteById(mediaId);

            return ResponseEntity.ok(message);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error deleting file: " + existingData.getFile());
        }
    }

    // Find a single record by ID
    @Transactional
    public ResponseEntity<?> deleteVehicleServiceMediaId(Long mediaId) {

        Media existingData = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new EntityNotFoundException("Data not found with mediaId: " + mediaId));

        try {
            // Build the path to the file
            Path filePath = Paths.get(uploadDirectory).resolve(existingData.getFile()).normalize();

            // Check if the file exists
            if (!Files.exists(filePath)) {
                return ResponseEntity.badRequest().body("File not found: " + existingData.getFile());
            }

            // Delete the file
            Files.delete(filePath);

            String message = "File deleted successfully: " + existingData.getFile();

            vehicleServiceMediaRepository.deleteByMediaId(mediaId);

            mediaRepository.deleteById(mediaId);

            return ResponseEntity.ok(message);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error deleting file: " + existingData.getFile());
        }
    }

}
