package com.markorusic.webstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotNull
    private String shippingAddress;

    private String note;

    @NotNull
    @Size(min = 1)
    private List<@Valid OrderDetailRequestDto> orderDetails;
}
