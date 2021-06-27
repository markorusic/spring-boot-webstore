package com.markorusic.webstore.dto.customer;

import com.markorusic.webstore.util.validation.ValidEmail;
import com.markorusic.webstore.util.validation.ValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCustomerRequestDto {

    @NotNull(groups = ValidationGroup.Update.class, message = "ID cannot be null")
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull(groups = ValidationGroup.Save.class, message = "password cannot be null")
    @Size(min = 6, max = 20)
    private String password;

    @NotNull
    @ValidEmail
    private String email;
}
