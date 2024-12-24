package com.ibas.brta.vehims.configurations.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ibas.brta.vehims.common.payload.request.MediaRequest;
import com.ibas.brta.vehims.common.service.MediaService;
import com.ibas.brta.vehims.util.Utils;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final String UPLOAD_DIR = "uploads/";

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.max-size}")
    private long maxFileSize;

    @Autowired
    MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            // Validate file size
            if (file.getSize() > maxFileSize) {
                throw new IllegalStateException(
                        "File size exceeds the maximum allowed size of " + (maxFileSize / 1024 / 1024) + " MB");
            }

            // Validate file type
            String contentType = file.getContentType();
            if (!Utils.isAllowedFileType(contentType)) {
                throw new IllegalStateException("Unsupported file type: " + contentType);
            }

            // Save the file
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath.toFile());

            mediaService.uploadMedia(null);

            // Return file URL
            String fileUrl = Utils.getFileUrl(request, fileName);
            return ResponseEntity.ok(fileUrl);

        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not upload file: " + e.getMessage());
        }
    }

    // Uploading a file
    @RequestMapping(value = "/upload2", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        // Setting up the path of the file
        String filePath = System.getProperty("user.dir") + "/uploads" + File.separator + file.getOriginalFilename();
        String fileUploadStatus;

        // Try block to check exceptions
        try {

            // Creating an object of FileOutputStream class
            FileOutputStream fout = new FileOutputStream(filePath);
            fout.write(file.getBytes());

            // Closing the connection
            fout.close();

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String mimeType = file.getContentType();

            MediaRequest requestObject = new MediaRequest();
            // requestObject.setFileName(fileName);
            requestObject.setFolderPath(filePath);
            // requestObject.setExtension(file.getOriginalFilename());
            requestObject.setType(mimeType);

            mediaService.uploadMedia(requestObject);

            fileUploadStatus = "File Uploaded Successfully";

        }

        // Catch block to handle exceptions
        catch (Exception e) {
            e.printStackTrace();
            fileUploadStatus = "Error in uploading file: " + e;
        }
        return fileUploadStatus;
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                String mimeType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mimeType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private final String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    @GetMapping("/get-file-by-name/{fileName}")
    public ResponseEntity<?> getFileByName(@PathVariable String fileName) {
        try {
            // Build the path to the file
            Path filePath = Paths.get(uploadDirectory).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Get file type
            String mimeType = Files.probeContentType(filePath);
            String fileExtension = StringUtils.getFilenameExtension(fileName).toLowerCase();

            if (mimeType != null && mimeType.startsWith("image")) {
                // Return image as Base64 string
                byte[] fileBytes = Files.readAllBytes(filePath);
                // String base64String = Base64Utils.encodeToString(fileBytes);
                String base64String = java.util.Base64.getEncoder().encodeToString(fileBytes);

                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(base64String);
            } else if ("pdf".equalsIgnoreCase(fileExtension)) {
                // Return PDF as binary
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(Files.readAllBytes(filePath));
            } else {
                // Unsupported file type
                return ResponseEntity.badRequest().body("Unsupported file type");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error retrieving file");
        }
    }

    @DeleteMapping("/delete-dl-service-document/{mediaId}")
    public ResponseEntity<?> deleteFile(@PathVariable Long mediaId) {
        return mediaService.deleteDlServiceMediaId(mediaId);
    }

    // Get File URL
    @GetMapping("/url/{fileName}")
    public ResponseEntity<String> getFileUrl(@PathVariable String fileName, HttpServletRequest request) {
        // String fileUrl = Utils.getFileUrl(request, fileName);
        String fileUrl = "/api/files/download/" + fileName;
        // String fileUrl = "/uploads/" + fileName;
        return ResponseEntity.ok(fileUrl);
    }

    @GetMapping("/full-url/{filename}")
    public ResponseEntity<Resource> getFileFullUrl(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new FileNotFoundException("File not found: " + filename);
            }

            // Return the file as a response
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    private String getFullFileUrl(HttpServletRequest request, String fileName) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl + "/api/files/" + fileName;
    }

    @GetMapping("/get-full-url/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                // Set headers for proper file viewing or download
                return ResponseEntity.ok()
                        .header("Content-Disposition", "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // File Download
    @GetMapping("/download2/{fileName}")
    public ResponseEntity<Resource> downloadFile2(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Getting list of filenames that have been uploaded
    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public String[] getFiles() {
        String folderPath = System.getProperty("user.dir") + "/uploads";

        // Creating a new File instance
        File directory = new File(folderPath);

        // list() method returns an array of strings
        // naming the files and directories
        // in the directory denoted by this abstract pathname
        String[] filenames = directory.list();

        // returning the list of filenames
        return filenames;

    }

    // Downloading a file
    @RequestMapping(value = "/download/{path:.+}", method = RequestMethod.GET)
    public ResponseEntity downloadFile(@PathVariable("path") String filename) throws FileNotFoundException {

        // Checking whether the file requested for download exists or not
        String fileUploadpath = System.getProperty("user.dir") + "/uploads";
        String[] filenames = this.getFiles();
        boolean contains = Arrays.asList(filenames).contains(filename);
        if (!contains) {
            return new ResponseEntity("FIle Not Found", HttpStatus.NOT_FOUND);
        }

        // Setting up the filepath
        String filePath = fileUploadpath + File.separator + filename;

        // Creating new file instance
        File file = new File(filePath);

        // Creating a new InputStreamResource object
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        // Creating a new instance of HttpHeaders Object
        HttpHeaders headers = new HttpHeaders();

        // Setting up values for contentType and headerValue
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }

}
