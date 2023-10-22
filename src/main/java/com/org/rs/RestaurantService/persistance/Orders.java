package com.org.rs.RestaurantService.persistance;

import com.org.rs.RestaurantService.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Builder
@Data
public class Orders {

    @Id
    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Column(name = "PLACED_AT")
    private LocalDateTime placedAt;

    @Column(name = "CORRELATION_ID")
    private String correlationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orders")
    private Restaurants restaurants;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<LineItems> lineOrder;
}
