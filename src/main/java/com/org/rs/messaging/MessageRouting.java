package com.org.rs.messaging;

import com.org.rs.integration.MessageHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.org.ma.utils.Constants.RESTAURANT_CHANNEL;

@Component
@RequiredArgsConstructor
public class MessageRouting extends RouteBuilder {

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String brokerAddress;

    private MessageHandler handler;

    @Override
    public void configure() {
        from("kafka:%s?brokers=%s".formatted(RESTAURANT_CHANNEL, brokerAddress))
                .routeId("restaurant-channel")
                .process(handler);
    }
}
