package com.updown;

import com.updown.model.FileInfo;
import com.updown.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
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
                String sizeConverted = convertSize(file.getSize());
                FileInfo fileInfo = new FileInfo(generateId() ,fileName,
                        file.getContentType(), file.getBytes(), sizeConverted);
                return fileRepository.save(fileInfo);
            } catch (Exception e) {
                throw new Exception("File cant be saved.");
            }
    }

    public FileInfo findFileById(String id) {
        return fileRepository.findById(id).get();
    }

    public FileInfo downloadFile(String id) throws Exception {
        try {
            FileInfo fileInfo = fileRepository.findById(id).get();
            fileInfo.increaseDownloads();
            fileRepository.save(fileInfo);
            return fileRepository.findById(id).get();
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView();

            modelAndView.setViewName("errorview.html");
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

    public String convertSize(long fileSize) {
        int i = 0;
        double size = (double) fileSize;
        String[] sizes = {"B", "KB", "MB", "GB"};
        while (size >= 1024 && i < sizes.length - 1) {
            size /= 1024;
            i++;
        }
        return String.format("%.2f", size) + sizes[i];
    }
}
