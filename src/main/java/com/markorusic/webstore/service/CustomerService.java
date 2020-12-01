package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.Customer;
import com.markorusic.webstore.domain.CustomerAction;
import com.markorusic.webstore.dto.customer.*;
import com.markorusic.webstore.security.domain.AuthRequestDto;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Customer update(CustomerRequestDto customerRequestDto);

    CustomerAction track(String actionType);

    Page<CustomerAction> findActions(Predicate predicate, Pageable pageable);

    Customer register(CustomerRegistrationDto customerRegistrationDto);

    AuthResponseDto login(AuthRequestDto authRequestDto);

    Customer getAuthenticatedCustomer();
}
