package com.org.rs.persistance;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurants restaurants;

    @OneToMany(mappedBy = "items", fetch = FetchType.LAZY)
    private List<LineItems> cart;
}
