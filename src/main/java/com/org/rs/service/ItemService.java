package com.org.rs.service;

import com.org.rs.persistance.Items;
import com.org.rs.repository.ItemsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemsRepository itemsRepository;

    public Items findItemById(Integer id) {
        return itemsRepository.findById(id).get();
    }
}
