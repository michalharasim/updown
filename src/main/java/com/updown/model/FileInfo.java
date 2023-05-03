package com.updown.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@Getter
public class FileInfo {

    @Id
    private String id;
    private String fileName;
    private String fileType;
    private int downloadCount;
    private String fileSize;
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime uploadDate;

    @Lob
    private byte[] data;

    public FileInfo(String id, String fileName, String fileType, byte[] data, String fileSize) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.uploadDate = ZonedDateTime.now();
        this.downloadCount = 0;
        this.fileSize = fileSize;
    }

    public int getDownloadCount() {
        return downloadCount;
    }
    public void increaseDownloads() {
        this.downloadCount++;
    }

    public String getId() {
        return this.id;
    }
}
