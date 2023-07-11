package com.spring.function.controller;

import com.spring.function.service.UploadImageService;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final UploadImageService uploadImageService;

    public ImageController(UploadImageService uploadImageService) {
        this.uploadImageService = uploadImageService;
    }

    @GetMapping("/{id}")
    public Resource downloadImage(@PathVariable Long id) throws MalformedURLException {
        final String fullPath = uploadImageService.findFullPathById(id);

        return new UrlResource("file:" + fullPath);
    }
}
