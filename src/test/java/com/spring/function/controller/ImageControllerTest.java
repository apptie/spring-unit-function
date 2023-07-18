package com.spring.function.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.spring.function.service.UploadImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = ImageController.class)
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
class ImageControllerTest {

    @MockBean
    UploadImageService uploadImageService;

    @Autowired
    ImageController imageController;

    @Autowired
    RestDocumentationResultHandler restDocs;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(@Autowired RestDocumentationContextProvider provider) {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(print())
                .alwaysDo(restDocs)
                .build();
    }

    @Test
    void downloadImageTest() throws Exception {
        final byte[] imageBytes = "이것은 이미지 파일의 바이트 코드입니다.".getBytes();
        final Resource mockResource = new ByteArrayResource(imageBytes);

        when(uploadImageService.findImageById(anyLong())).thenReturn(mockResource);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/images/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(imageBytes))
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("이미지 ID")
                                ),
                                responseHeaders(
                                        headerWithName("Content-Type").description("이미지 Content-Type")
                                )
                        )
                );
    }
}
