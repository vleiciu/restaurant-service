package com.org.rs.rest;

import com.org.rs.persistance.Items;
import com.org.rs.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemsController {

    private RestaurantService restaurantService;

    @GetMapping("/get/{restaurantId}")
    public ResponseEntity<List<Items>> getItemsList (@PathVariable("id") Integer id) {
        return ResponseEntity.ok(restaurantService.getItemsByRestaurantId(id));
    }
}
