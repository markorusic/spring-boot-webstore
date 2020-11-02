package com.markorusic.webstore.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    private List<Product> products;
}
