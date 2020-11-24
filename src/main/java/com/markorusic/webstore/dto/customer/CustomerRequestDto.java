package com.markorusic.webstore.dto.customer;

import com.markorusic.webstore.util.ValidEmail;
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
    @ValidEmail
    private String email;
}
