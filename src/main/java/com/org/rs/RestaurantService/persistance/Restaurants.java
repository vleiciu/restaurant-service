package com.org.rs.RestaurantService.persistance;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "RESTAURANTS")
@Builder
@Data
public class Restaurants {

    @Id
    @Column(name = "RESTAURANT_ID")
    private Integer restaurantId;

    @Column(name = "RESTAURANT_NAME")
    private String restaurantName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PAYMENT_INFO")
    private String paymentInfo;

    @OneToMany(mappedBy = "restaurant")
    private List<Items> restaurantItems;

    @OneToMany(mappedBy = "restaurants", fetch = FetchType.EAGER)
    private List<Orders> orders;
}
