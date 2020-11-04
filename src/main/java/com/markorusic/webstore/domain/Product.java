package com.markorusic.webstore.domain;

import lombok.*;
import javax.persistence.*;

@Data
@Entity
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float price;

    private String photo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

}
