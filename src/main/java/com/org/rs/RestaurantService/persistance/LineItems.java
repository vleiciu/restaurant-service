package com.org.rs.RestaurantService.persistance;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "LINE_ITEMS")
@Builder
@Data
public class LineItems {

    @Id
    @Column(name = "LINE_ID")
    private Integer lineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Items items;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders orders;
}
