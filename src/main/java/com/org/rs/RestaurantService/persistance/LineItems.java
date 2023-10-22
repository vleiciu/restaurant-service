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

    @OneToOne(mappedBy = "cart", fetch = FetchType.EAGER)
    private Items items;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lineOrder")
    private Orders orders;
}
