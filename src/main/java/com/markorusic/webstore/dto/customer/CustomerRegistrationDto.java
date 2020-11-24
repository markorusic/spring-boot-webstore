package com.markorusic.webstore.dto.customer;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class CustomerRegistrationDto extends CustomerRequestDto {
    @NotNull
    @Size(min = 6, max = 20)
    private String password;
}
