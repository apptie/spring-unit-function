package com.spring.function.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UploadImage> images = new ArrayList<>();

    protected Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public void addImage(UploadImage image) {
        images.add(image);
        image.initItem(this);
    }
}
