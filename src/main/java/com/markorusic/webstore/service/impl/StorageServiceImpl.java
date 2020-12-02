package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${storage.filePath}")
    private String FILE_PATH;

    @Value("${storage.relativeFilePath}")
    private String RELATIVE_FILE_PATH;

    public String saveFile(String dir, String fileName, MultipartFile multipartFile) throws IOException {
        var uploadPath = Paths.get(FILE_PATH + dir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (var inputStream = multipartFile.getInputStream()) {
            var filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return RELATIVE_FILE_PATH + fileName;
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
    }

    private String getUniqueFilename(MultipartFile file) {
        var originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        var ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        return (new Date().getTime()) + UUID.randomUUID().toString() + "." + ext;
    }

    @Override
    public String store(MultipartFile file) {
        var fileName = getUniqueFilename(file);
        try {
            return saveFile("", fileName, file);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file");
        }
    }
}
