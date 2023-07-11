package com.spring.function.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class UploadImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uploadFileName;
    private String storeFileName;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    protected UploadImage() {
    }

    public UploadImage(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public void initItem(Item item) {
        this.item = item;
    }
}
