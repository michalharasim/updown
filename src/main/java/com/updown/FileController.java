package com.updown;


import com.updown.model.FileInfo;
import com.updown.model.FileInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@AllArgsConstructor
public class FileController {

    private FileUploadService fileUploadService;
    @PostMapping("/upload")
    public FileInfoResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        FileInfo fileInfo = null;
        String downloadURL = "";
        fileInfo = fileUploadService.uploadFile(file);
        downloadURL = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(fileInfo.getId())
                .toUriString();
        return new FileInfoResponse(fileInfo.getFileName(), downloadURL,
                file.getContentType(), file.getSize(), LocalDateTime.now());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) throws Exception {
        FileInfo fileInfo = fileUploadService.downloadFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "fileinfo; filename=" + fileInfo.getFileName());
        headers.add("Location", "http://localhost:8080/");
        return ResponseEntity.ok().
                contentType(MediaType.parseMediaType(fileInfo.getFileType()))
                .headers(headers)
                .body(new ByteArrayResource(fileInfo.getData()));
    }
}