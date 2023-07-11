package com.spring.function.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImagePageController {

    @GetMapping("/")
    public String imagePage() {
        return "image";
    }
}
