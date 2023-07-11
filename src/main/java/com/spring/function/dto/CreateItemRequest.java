package com.spring.function.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateItemRequest {

    private String name;
    private List<MultipartFile> images;
}
