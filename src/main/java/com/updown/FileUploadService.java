package com.updown;

import com.updown.model.FileInfo;
import com.updown.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class FileUploadService {
    private FileRepository fileRepository;

    public FileInfo uploadFile(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                if (fileName.contains("..")) {
                    throw new Exception("invalid file name" + fileName);
                }
                System.out.println(file.getContentType());
                FileInfo fileInfo = new FileInfo(generateId() ,fileName,
                        file.getContentType(), file.getBytes(), file.getSize(), LocalDateTime.now());
                return fileRepository.save(fileInfo);
            } catch (Exception e) {
                throw new Exception("File cant be saved.");
            }
    }



    public FileInfo downloadFile(String id) throws Exception {
        try {
            FileInfo fileInfo = fileRepository.findById(id).get();
            fileInfo.increaseDownloads();
            fileRepository.save(fileInfo);
            return fileRepository.findById(id).get();
        } catch (Exception e) {
            throw new Exception("No such file!");
        }
    }

    public String generateId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return stringBuilder.toString();
    }
}
