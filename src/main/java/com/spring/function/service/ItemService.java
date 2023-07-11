package com.spring.function.service;

import com.spring.function.domain.Item;
import com.spring.function.domain.UploadImage;
import com.spring.function.repository.ItemRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void createItem(String name, List<UploadImage> uploadImages) {
        final Item item = new Item(name);

        for (UploadImage uploadImage : uploadImages) {
            item.addImage(uploadImage);
        }

        itemRepository.save(item);
    }
}
