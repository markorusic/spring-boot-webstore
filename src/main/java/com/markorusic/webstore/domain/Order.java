package com.markorusic.webstore.domain;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shippingAddress;

    private String note;

    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne(optional = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderDetail> orderDetails;
}
