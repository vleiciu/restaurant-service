package com.org.rs.RestaurantService.persistance;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "ITEMS")
@Builder
@Data
public class Items {

    @Id
    @Column(name = "ITEM_ID")
    private Integer itemId;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "PRICE")
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurantItems")
    private Restaurants restaurants;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "items")
    private LineItems cart;
}
