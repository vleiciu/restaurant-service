package com.org.rs.RestaurantService.rest;

import com.org.ma.model.RestaurantUpdate;
import com.org.rs.RestaurantService.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantsController {

    private RestaurantService restaurantService;

    @PostMapping("/create")
    public ResponseEntity<String> createRestaurant(RestaurantUpdate restaurants) {
        boolean isRestaurantCreated = restaurantService.createRestaurant(restaurants);
        return isRestaurantCreated ?
                ResponseEntity.ok("Restaurant successfully created") :
                new ResponseEntity<>("Restaurant already exists", HttpStatusCode.valueOf(500));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateRestaurant(RestaurantUpdate restaurants) {
        boolean isRestaurantUpdated = restaurantService.updateRestaurant(restaurants);
        return isRestaurantUpdated ?
                ResponseEntity.ok("Restaurant successfully updated") :
                new ResponseEntity<>("Restaurant not found", HttpStatusCode.valueOf(500));
    }
}
