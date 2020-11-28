package com.markorusic.webstore.security.domain;

import com.markorusic.webstore.util.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    private String password;
}
