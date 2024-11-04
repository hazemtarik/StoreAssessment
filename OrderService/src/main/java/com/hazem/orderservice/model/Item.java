package com.hazem.orderservice.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String itemId;
    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;
    private int quantity;

}
