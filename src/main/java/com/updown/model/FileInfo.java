package com.updown.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class FileInfo {

    @Id
    private String id;
    private String fileName;
    private String fileType;
    private int downloadCount;
    @Lob
    private byte[] data;

    public FileInfo(String id, String fileName, String fileType, byte[] data) {
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
