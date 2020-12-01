package com.markorusic.webstore.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder
@Entity
@Table(name = "admin_actions")
public class AdminAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String actionType;

    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    private Admin admin;
}