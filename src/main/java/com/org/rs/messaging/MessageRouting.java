package com.org.rs.messaging;

import com.org.rs.integration.MessageHandler;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static com.org.ma.utils.Constants.RESTAURANT_CHANNEL;

@Component
@AllArgsConstructor
public class MessageRouting extends RouteBuilder {

    private MessageHandler handler;

    @Override
    public void configure() {
        from("kafka:%s?brokers=localhost:9092".formatted(RESTAURANT_CHANNEL))
                .routeId("restaurant-channel")
                .process(handler);
    }
}
