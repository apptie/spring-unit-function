package com.spring.function.service;

import com.spring.function.domain.UploadImage;
import com.spring.function.repository.UploadImageRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class UploadImageService {

    @Value("${file.dir}")
    private String fileDir;

    private final UploadImageRepository uploadImageRepository;

    public UploadImageService(UploadImageRepository uploadImageRepository) {
        this.uploadImageRepository = uploadImageRepository;
    }

    public List<UploadImage> storeFiles(List<MultipartFile> multipartFiles) {
        try {
            List<UploadImage> storeFileResult = new ArrayList<>();

            for (MultipartFile multipartFile : multipartFiles) {
                if (!multipartFile.isEmpty()) {
                    storeFileResult.add(storeFile(multipartFile));
                }
            }

            return storeFileResult;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private UploadImage storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        final String fullPath = findFullPath(storeFileName);
        multipartFile.transferTo(new File(fullPath));

        return new UploadImage(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int position = originalFilename.lastIndexOf(".");
        return originalFilename.substring(position + 1);
    }

    public String findFullPathById(Long id) {
        final UploadImage uploadImage = uploadImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 이미지입니다."));

        return findFullPath(uploadImage.getStoreFileName());
    }

    private String findFullPath(String filename) {
        return fileDir + filename;
    }
}
