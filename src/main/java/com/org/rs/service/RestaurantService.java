package com.org.rs.service;

import com.org.ma.enums.MessageType;
import com.org.ma.enums.Subject;
import com.org.ma.model.RestaurantUpdate;
import com.org.ma.utils.Constants;
import com.org.rs.persistance.Items;
import com.org.rs.persistance.Restaurants;
import com.org.rs.repository.ItemsRepository;
import com.org.rs.repository.RestaurantsRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.org.ma.enums.Header.REQUEST;
import static com.org.ma.utils.Constants.ORDER_CHANNEL;
import static com.org.ma.utils.Constants.SUBJECT;

@AllArgsConstructor
@Service
public class RestaurantService {

    private KafkaTemplate<String, String> producer;

    private RestaurantsRepository restaurantsRepository;

    private ItemsRepository itemsRepository;

    public Restaurants getById(Integer id) {
        return restaurantsRepository.findById(id).get();
    }

    public boolean createRestaurant(RestaurantUpdate restaurantUpdate) {
        if (restaurantsRepository.findById(restaurantUpdate.getRestaurantId()).isPresent()) {
            return false;
        }
        Restaurants rest = Restaurants.builder()
                .address(restaurantUpdate.getAddress())
                .restaurantName(restaurantUpdate.getRestaurantName())
                .paymentInfo(restaurantUpdate.getPaymentInfo())
                .build();
        List<Items> items = restaurantUpdate.getItems().stream().map(item -> Items.builder()
                        .itemName(item.getItemName())
                        .price(item.getPrice())
                        .restaurants(rest)
                        .build())
                .toList();
        restaurantsRepository.save(rest);
        items.forEach(item -> itemsRepository.save(item));
        notifyOrchestrator(restaurantUpdate);
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
        ProducerRecord<String, String> record = new ProducerRecord<>(ORDER_CHANNEL, restaurantUpdate.toString());
        record.headers().add(SUBJECT, "%s_%s".formatted(Subject.RESTAURANT.name(), REQUEST.name()).getBytes());
        record.headers().add(Constants.MESSAGE_TYPE, MessageType.INFO.name().getBytes());
        producer.send(record);
    }
}
