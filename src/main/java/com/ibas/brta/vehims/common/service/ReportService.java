package com.ibas.brta.vehims.common.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import com.ibas.brta.vehims.drivingLicense.payload.response.DrivingLicenseApplicationDto;

import jakarta.servlet.http.HttpServletResponse;

// import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    public byte[] generateReport2(String reportName, Map<String, Object> parameters,
            List<DrivingLicenseApplicationDto> dataSource) {
        try {
            InputStream jrxmlStream = getClass().getResourceAsStream("/reports/Example.jrxml");
            if (jrxmlStream == null) {
                throw new IllegalArgumentException("Report file not found: /reports/Example.jrxml");
            }

            // Compile the JRXML file
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            // Prepare the data source
            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dataSource);

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
                    jrBeanCollectionDataSource);

            // Export to byte array
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate report: " + e.getMessage(), e);
        }
    }

}