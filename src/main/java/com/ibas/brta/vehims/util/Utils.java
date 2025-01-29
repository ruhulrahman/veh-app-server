package com.ibas.brta.vehims.util;

import com.ibas.brta.vehims.exception.BadRequestException;
import com.ibas.brta.vehims.security.UserPrincipal;
import com.ibas.brta.vehims.userManagement.model.SUser;
import com.ibas.brta.vehims.userManagement.model.User;
import com.ibas.brta.vehims.userManagement.payload.response.UserFullResponse;
import com.ibas.brta.vehims.userManagement.repository.SUserRepository;
import com.ibas.brta.vehims.userManagement.repository.UserRepository;
import com.ibas.brta.vehims.userManagement.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Utils {

    @Autowired
    private static UserService userService;

    private static final long MIN_FILE_SIZE = 1024; // Minimum file size in bytes

    @Autowired
    static UserRepository userRepository;

    private static SUserRepository sUserRepository;

    // Constructor injection
    // Constructor injection
    @Autowired
    public Utils(SUserRepository sUserRepository) {
        Utils.sUserRepository = sUserRepository;  // Set the static field
    }

    public static Date getDateFromString(String date, String format) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.parse(date);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    public static String datetoReportString(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public static String generateServiceRequestNo() {
        // This ensures the generated service_request_no will look like 2024051427339123
        // (2024 year, 05 month, 14 day, 27 hour, 33 minute, 39 second, and 123
        // milliseconds).
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the date and time as 'yyyyMMddHHmmssSSS'
        // yyyy for the year (e.g., 2024)
        // MM for the month (e.g., 05 for May)
        // dd for the day (e.g., 14 for the 14th day of the month)
        // HH for the hour (24-hour format)
        // mm for the minute
        // ss for the second
        // SSS for milliseconds (to ensure uniqueness)
        // (YearMonthDayHourMinuteSecondMilliseconds)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);

        // Return the formatted date-time as the service request number
        return formattedDateTime;
    }

    public static String generateLearnerNumber() {
        // This ensures the generated service_request_no will look like 2024051427339123
        // (2024 year, 05 month, 14 day, 27 hour, 33 minute, 39 second, and 123
        // milliseconds).
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the date and time as 'yyyyMMddHHmmssSSS'
        // yyyy for the year (e.g., 2024)
        // MM for the month (e.g., 05 for May)
        // dd for the day (e.g., 14 for the 14th day of the month)
        // HH for the hour (24-hour format)
        // mm for the minute
        // ss for the second
        // SSS for milliseconds (to ensure uniqueness)
        // (YearMonthDayHourMinuteSecondMilliseconds)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);

        // Return the formatted date-time as the service request number
        return formattedDateTime;
    }

    public static String generateDLNumber() {
        // This ensures the generated service_request_no will look like 2024051427339123
        // (2024 year, 05 month, 14 day, 27 hour, 33 minute, 39 second, and 123
        // milliseconds).
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the date and time as 'yyyyMMddHHmmssSSS'
        // yyyy for the year (e.g., 2024)
        // MM for the month (e.g., 05 for May)
        // dd for the day (e.g., 14 for the 14th day of the month)
        // HH for the hour (24-hour format)
        // mm for the minute
        // ss for the second
        // SSS for milliseconds (to ensure uniqueness)
        // (YearMonthDayHourMinuteSecondMilliseconds)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedDateTime = now.format(formatter);

        // Return the formatted date-time as the service request number
        return formattedDateTime;
    }

    public static String generateUniqueFileName(String originalFileName) {
        // Extract file extension
        String fileExtension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < originalFileName.length() - 1) {
            fileExtension = originalFileName.substring(dotIndex); // Includes the dot (e.g., ".jpg")
        }

        // Generate unique file name using UUID
        String uniqueFileName = UUID.randomUUID().toString();

        // Return the unique file name with the extension
        return uniqueFileName + fileExtension;
    }

    public static Long getLoggedinUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getId();
        }
        return null;
    }

    public static Long getLoggedInOrgId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            Optional<SUser> user = sUserRepository.findById(userPrincipal.getId());
            if (user.isPresent()) {
                return user.get().getLoggedInOrgId();
            }
        }
        return null;
    }

    public static UserFullResponse getLoggedinUser() {
        UserFullResponse response = userService.getDataById(getLoggedinUserId());
        return response;
    }

    public static List<Long> convertPgArrayToList(PGobject pgArray) {
        try {
            String value = pgArray.getValue();
            System.out.println("Array Value: " + value); // Log the value
            if (value == null || value.isEmpty()) {
                return new ArrayList<>();
            }

            return Arrays.stream(value.replaceAll("[{}]", "").split(","))
                    .map(String::trim)
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Log the exception or handle it according to your needs
            throw new RuntimeException("Error parsing PostgreSQL array", e);
        }
    }

    // public static boolean isFileSizeValid(File file) {
    // if (file.isEmpty()) {
    // return false;
    // }
    // long fileSize = file.length();
    // return fileSize >= MIN_FILE_SIZE;
    // }

    public static boolean isAllowedFileType(String contentType) {
        // Allowed MIME types
        List<String> allowedTypes = Arrays.asList("image/jpeg", "image/png", "application/pdf");
        return allowedTypes.contains(contentType);
    }

    public static boolean isFileTypeImage(MultipartFile attachment) {
        // Get the file's MIME type
        String mimeType = attachment.getContentType();

        // Validate the MIME type against allowed types
        return mimeType != null &&
                (mimeType.equals("image/jpeg") || mimeType.equals("image/png"));
    }

    public static boolean isValidFileType(MultipartFile attachment) {
        // Get the file's MIME type
        String mimeType = attachment.getContentType();

        // Validate the MIME type against allowed types
        return mimeType != null &&
                (mimeType.equals("image/jpeg") || mimeType.equals("image/png") || mimeType.equals("application/pdf"));
    }

    public static boolean isFileTypePdf(MultipartFile attachment) {
        // Get the file's MIME type
        String mimeType = attachment.getContentType();

        // Validate the MIME type against allowed types
        return mimeType != null && mimeType.equals("application/pdf");
    }

    public static boolean isAllowedFileExtension(String fileName) {
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "pdf");
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return allowedExtensions.contains(fileExtension);
    }

    public static String getFileUrl(HttpServletRequest request, String fileName) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/api/files/" + fileName;
    }

    public static boolean notNullOrEmpty(Integer value) {
        if (value == null || value == 0) {
            return false;
        }

        return true;

    }

    public static boolean notNullOrEmpty(Long value) {
        if (value == null || value == 0) {
            return false;
        }

        return true;

    }

    public static boolean notNullOrEmpty(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }

        return true;

    }

}
