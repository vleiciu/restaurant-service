package com.org.rs.RestaurantService.service;

import com.org.ma.enums.MessageType;
import com.org.ma.enums.Subject;
import com.org.ma.model.RestaurantUpdate;
import com.org.ma.utils.Constants;
import com.org.rs.RestaurantService.persistance.Items;
import com.org.rs.RestaurantService.persistance.Restaurants;
import com.org.rs.RestaurantService.repository.ItemsRepository;
import com.org.rs.RestaurantService.repository.RestaurantsRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.org.ma.enums.Header.REQUEST;
import static com.org.ma.utils.Constants.*;

@AllArgsConstructor
@Service
public class RestaurantService {

    private KafkaProducer<String, RestaurantUpdate> producer;

    private RestaurantsRepository restaurantsRepository;

    private ItemsRepository itemsRepository;

    public Restaurants getById(Integer id) {
        return restaurantsRepository.findById(id).get();
    }

    public boolean createRestaurant(RestaurantUpdate restaurantCreate) {
        if (restaurantsRepository.findById(restaurantCreate.getRestaurantId()).isPresent()) {
            return false;
        }
        Restaurants rest = Restaurants.builder()
                .address(restaurantCreate.getAddress())
                .restaurantName(restaurantCreate.getRestaurantName())
                .paymentInfo(restaurantCreate.getPaymentInfo())
                .build();
        List<Items> items = restaurantCreate.getItems().stream().map(item -> Items.builder()
                        .itemName(item.getItemName())
                        .price(item.getPrice())
                        .restaurants(rest)
                        .build())
                .toList();
        restaurantsRepository.save(rest);
        items.forEach(item -> itemsRepository.save(item));
        notifyOrchestrator(restaurantCreate);
        return true;
    }

    public boolean updateRestaurant(RestaurantUpdate restaurantUpdate) {
        if (!restaurantsRepository.existsById(restaurantUpdate.getRestaurantId())) return false;
        Restaurants restaurant = restaurantsRepository.findById(restaurantUpdate.getRestaurantId()).get();
        restaurant.setAddress(restaurantUpdate.getAddress());
        restaurant.setPaymentInfo(restaurantUpdate.getPaymentInfo());
        restaurantUpdate.getItems().forEach(item -> {
            Items items = Items.builder()
                    .itemName(item.getItemName())
                    .restaurants(restaurant)
                    .price(item.getPrice())
                    .build();
            if (itemsRepository.existsById(item.getItemId())) items.setItemId(item.getItemId());
            itemsRepository.save(items);
        });
        notifyOrchestrator(restaurantUpdate);
        return true;
    }

    public List<Items> getItemsByRestaurantId(Integer id) {
        return restaurantsRepository.findById(id).get().getRestaurantItems();
    }

    private void notifyOrchestrator(RestaurantUpdate restaurantUpdate) {
        ProducerRecord<String, RestaurantUpdate> record = new ProducerRecord<>(ORDER_CHANNEL, MESSAGE, restaurantUpdate);
        record.headers().add(SUBJECT, "%s_%s".formatted(Subject.RESTAURANT.name(), REQUEST.name()).getBytes());
        record.headers().add(Constants.MESSAGE_TYPE, MessageType.INFO.name().getBytes());
        producer.send(record);
    }
}
