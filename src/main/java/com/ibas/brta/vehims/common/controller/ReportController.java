package com.ibas.brta.vehims.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.ibas.brta.vehims.common.service.ReportService;
import com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto;

import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(value = "/{reportName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public void generateReport2(@PathVariable String reportName,
            @RequestBody Map<String, Object> parameters,
            HttpServletResponse response) {
        try {
            List<DrivingLicenseApplicationDto> dataSource = fetchDrivingLicenseData();
            byte[] reportData = reportService.generateReport2(reportName, parameters, dataSource);

            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-Disposition", "inline; filename=" + reportName + ".pdf");

            OutputStream out = response.getOutputStream();
            out.write(reportData);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private List<DrivingLicenseApplicationDto> fetchDrivingLicenseData() {
        // Fetch data from your database or mock for testing
        return List.of(
                new DrivingLicenseApplicationDto(
                        1L, "REQ-001", "John Doe", "Individual", "Learner",
                        "Authority A", "1234567890", null, 1L, "Submitted", "SUB", "Green"),
                new DrivingLicenseApplicationDto(
                        2L, "REQ-002", "Jane Smith", "Corporate", "Permanent",
                        "Authority B", "0987654321", null, 2L, "Approved", "APP", "Blue"));
    }
}
