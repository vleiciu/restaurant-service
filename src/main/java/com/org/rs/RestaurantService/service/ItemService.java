package com.org.rs.RestaurantService.service;

import com.org.rs.RestaurantService.persistance.Items;
import com.org.rs.RestaurantService.repository.ItemsRepository;
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
