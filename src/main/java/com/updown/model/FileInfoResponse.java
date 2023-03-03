package com.updown.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoResponse {
    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
    private LocalDateTime uploadDate;
}
