package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.Customer;
import com.markorusic.webstore.dto.customer.*;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Customer findById(Long id);

    CustomerDto update(CustomerRequestDto customerRequestDto);

    CustomerActionDto track(String actionType);

    Page<CustomerActionDto> findActions(Predicate predicate, Pageable pageable);

    CustomerDto register(CustomerRegistrationDto customerRegistrationDto);

    AuthResponseDto login(CustomerLoginDto customerLoginDto);
}
