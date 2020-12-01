package com.markorusic.webstore.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminActionDto {
    private Long id;

    private String actionType;

    private LocalDateTime createdAt;
}
