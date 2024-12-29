package com.ibas.brta.vehims.configurations.payload.response;

import java.io.File;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FileResponse {
    private Long id;
    private Long mediaId;
    private String attachmentFile;
    private String fileName;
    private String fileUrl;
}
