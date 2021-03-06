package com.markorusic.webstore.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerActionDto {
    private Long id;

    private String actionType;

    private LocalDateTime createdAt;
}
