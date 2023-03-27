package com.updown;


import com.updown.model.FileInfo;
import com.updown.model.FileInfoResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

@RestController
@AllArgsConstructor
public class FileController {

    private FileUploadService fileUploadService;
    @PostMapping("/upload")
    public FileInfoResponse uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        FileInfo fileInfo = null;
        String downloadURL = "";
        String fileURL = "";
        fileInfo = fileUploadService.uploadFile(file);
        downloadURL = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/file/")
                .path(fileInfo.getId())
                .path("/download")
                .toUriString();
        fileURL = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/file/")
                .path(fileInfo.getId())
                .toUriString();
        return new FileInfoResponse(fileInfo.getFileName(), downloadURL, fileURL,
                file.getContentType(), file.getSize());
    }

    @RequestMapping(value="file/{id}")
    public ModelAndView IDView(@PathVariable String id) throws Exception {
        try {
            FileInfo fileInfo = fileUploadService.findFileById(id);
            if (fileInfo != null) {
                return fileView();
            }
        } catch (Exception e) {
            return errorView();
        }
        return null;
    }





    @RequestMapping(value="/")
    public ModelAndView mainView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mainview.html");
        return modelAndView;
    }

    public ModelAndView fileView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fileview.html");
        return modelAndView;
    }

    public ModelAndView errorView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errorview.html");
        return modelAndView;
    }

    @GetMapping("file/{id}/download")
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