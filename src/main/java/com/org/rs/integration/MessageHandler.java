package com.org.rs.integration;

import com.org.ma.enums.MessageType;
import com.org.ma.model.OrderCommand;
import com.org.rs.enums.OrderStatus;
import com.org.rs.persistance.LineItems;
import com.org.rs.persistance.Orders;
import com.org.rs.service.ItemService;
import com.org.rs.service.LineItemsService;
import com.org.rs.service.OrderService;
import com.org.rs.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.org.ma.enums.Header.REQUEST;
import static com.org.ma.utils.Constants.MESSAGE_TYPE;
import static com.org.ma.utils.Constants.SUBJECT;

@Component
@AllArgsConstructor
public class MessageHandler implements Processor {

    private OrderService orderService;

    private RestaurantService restaurantService;

    private ItemService itemService;

    private LineItemsService lineItemsService;

    @Override
    public void process(Exchange exchange) {
        if (exchange.getIn().getHeader(SUBJECT, String.class).endsWith(REQUEST.name())) {
            OrderCommand command = exchange.getMessage(OrderCommand.class);
            MessageType messageType = exchange.getIn().getHeader(MESSAGE_TYPE, MessageType.class);
            if (messageType.equals(MessageType.CANCEL)) {
                orderService.setOrderStatus(command.getCorrelationId(), OrderStatus.CANCELED);
            } else if (messageType.equals(MessageType.REGULAR)) {
                Orders order = Orders.builder()
                        .correlationId(command.getCorrelationId())
                        .orderStatus(OrderStatus.APPROVAL_PENDING)
                        .placedAt(command.getPlacedAt())
                        .restaurants(restaurantService.getById(command.getRestaurantId()))
                        .build();
                List<LineItems> lineItems = command.getItemsList().stream().map(lineItem -> LineItems.builder()
                        .quantity(lineItem.getQuantity())
                        .items(itemService.findItemById(lineItem.getItem().getItemId()))
                        .orders(order)
                        .build()).toList();
                orderService.saveOrder(order);
                lineItemsService.saveListItems(lineItems);
            }
        }
    }
}
