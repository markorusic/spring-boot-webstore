package com.markorusic.webstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;
}
