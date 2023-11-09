package com.org.rs.service;

import com.org.rs.persistance.LineItems;
import com.org.rs.repository.LineItemsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LineItemsService {

    private LineItemsRepository lineItemsRepository;

    public void saveListItems(List<LineItems> lineItems) {
        lineItems.forEach(item -> lineItemsRepository.save(item));
    }
}
