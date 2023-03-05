package com.updown.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

    @Id
    private String id;
    private String fileName;
    private String fileType;
    private int downloadCount;
    @Lob
    private byte[] data;

    public FileInfo(String id, String fileName, String fileType, byte[] data, long size, LocalDateTime now) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.downloadCount = 0;
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
