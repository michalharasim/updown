package com.updown;

import com.updown.model.FileInfo;
import com.updown.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
                FileInfo fileInfo = new FileInfo(fileName,
                        file.getContentType(), file.getBytes());
                return fileRepository.save(fileInfo);
            } catch (Exception e) {
                throw new Exception("File cant be saved.");
            }
    }

    public FileInfo downloadFile(String id) throws Exception {
        try {
            return fileRepository.findById(id).get();
        } catch (Exception e) {
            throw new Exception("No such file!");
        }
    }
}
