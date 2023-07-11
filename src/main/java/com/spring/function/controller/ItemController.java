package com.spring.function.controller;

import com.spring.function.domain.UploadImage;
import com.spring.function.dto.CreateItemRequest;
import com.spring.function.service.ItemService;
import com.spring.function.service.UploadImageService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final UploadImageService uploadImageService;

    public ItemController(ItemService itemService, UploadImageService uploadImageService) {
        this.itemService = itemService;
        this.uploadImageService = uploadImageService;
    }

    @PostMapping
    public ResponseEntity<Void> createItem(@ModelAttribute CreateItemRequest request) {
        final List<UploadImage> uploadImages = uploadImageService.storeFiles(request.getImages());
        itemService.createItem(request.getName(), uploadImages);

        return ResponseEntity.noContent()
                .build();
    }
}
