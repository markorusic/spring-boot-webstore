package com.markorusic.webstore.dto.customer;

import com.markorusic.webstore.util.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginDto {
    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    private String password;
}
