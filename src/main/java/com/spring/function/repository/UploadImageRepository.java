package com.spring.function.repository;

import com.spring.function.domain.UploadImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadImageRepository extends JpaRepository<UploadImage, Long> {

}
