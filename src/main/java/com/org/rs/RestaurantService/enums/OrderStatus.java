package com.org.rs.RestaurantService.enums;

import com.org.ma.enums.MessageType;
import lombok.Getter;

@Getter
public enum OrderStatus {
    APPROVED(MessageType.REGULAR),
    REJECTED(MessageType.REJECT),
    APPROVAL_PENDING(MessageType.REGULAR),
    CANCELED(MessageType.CANCEL);

    private final MessageType messageType;

    OrderStatus(MessageType messageType) {
        this.messageType = messageType;
    }

}
