package com.spring.function.controller;

import com.spring.function.service.UploadImageService;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id) throws MalformedURLException {
        final Resource resource = uploadImageService.findImageById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
