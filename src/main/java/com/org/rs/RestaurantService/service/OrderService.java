package com.org.rs.RestaurantService.service;

import com.org.ma.enums.Subject;
import com.org.ma.utils.Constants;
import com.org.rs.RestaurantService.enums.OrderStatus;
import com.org.rs.RestaurantService.persistance.Orders;
import com.org.rs.RestaurantService.repository.OrdersRepository;
import com.org.rs.RestaurantService.repository.RestaurantsRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.org.ma.enums.Header.RESPONSE;
import static com.org.ma.utils.Constants.*;

@Service
@AllArgsConstructor
public class OrderService {

    private KafkaTemplate<String, String> producer;

    private RestaurantsRepository restaurantsRepository;

    private OrdersRepository ordersRepository;

    public void saveOrder(Orders order) {
        ordersRepository.save(order);
    }

    public void setOrderStatus(String correlationId, OrderStatus status) {
        Orders orders = ordersRepository.findByCorrelationId(correlationId).get();
        orders.setOrderStatus(status);
        ordersRepository.save(orders);
        sendOrder(orders);
    }

    public Orders setOrderStatus(Integer id, OrderStatus status) {
        Orders orders = ordersRepository.findById(id).get();
        orders.setOrderStatus(status);
        ordersRepository.save(orders);
        sendOrder(orders);
        return orders;
    }

    public List<Orders> findAllActiveOrders(Integer id) {
        return restaurantsRepository.findById(id).get()
                .getOrders().stream()
                .filter(orders -> orders.getOrderStatus().equals(OrderStatus.APPROVAL_PENDING))
                .toList();
    }

    public List<Orders> findAllNonActiveOrders(Integer id) {
        return restaurantsRepository.findById(id).get()
                .getOrders().stream()
                .filter(orders -> !orders.getOrderStatus().equals(OrderStatus.APPROVAL_PENDING))
                .toList();
    }

    private void sendOrder(Orders order) {
        ProducerRecord<String, String> record = new ProducerRecord<>(RESTAURANT_CHANNEL, order.getCorrelationId());
        record.headers().add(SUBJECT, "%s_%s".formatted(Subject.RESTAURANT.name(), RESPONSE.name()).getBytes());
        record.headers().add(Constants.MESSAGE_TYPE, order.getOrderStatus().getMessageType().name().getBytes());
        producer.send(record);
    }
}
