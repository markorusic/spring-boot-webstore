package com.markorusic.webstore.controller;

import com.markorusic.webstore.service.StorageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequiredArgsConstructor
@Api(value = "File upload Api")
@RequestMapping(path = "/file-upload")
public class FileUploadController {

    private final StorageService storageService;

    @PostMapping("")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        return storageService.store(file);
    }

}
