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
    @GeneratedValue
    private Long id;

    private String name;

    private float price;

}
