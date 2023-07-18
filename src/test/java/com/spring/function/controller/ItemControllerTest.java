package com.spring.function.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.function.service.ItemService;
import com.spring.function.service.UploadImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class ItemControllerTest {

    @MockBean
    ItemService itemService;

    @MockBean
    UploadImageService uploadImageService;

    @Autowired
    ItemController itemController;

    @Autowired
    RestDocumentationResultHandler restDocs;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(@Autowired RestDocumentationContextProvider provider) {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(print())
                .alwaysDo(restDocs)
                .build();
    }

    @Test
    void createItemTest() throws Exception {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "images",
                "test2.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[]{1}
        );

        mockMvc.perform(multipart("/items")
                        .file(mockMultipartFile)
                        .file(mockMultipartFile)
                        .param("name", "경매상품 1번"))
                .andExpect(status().isNoContent());
    }
}
