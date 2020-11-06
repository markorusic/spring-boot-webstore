package com.markorusic.webstore.service;

import com.markorusic.webstore.dto.CustomerActionDto;
import com.markorusic.webstore.dto.CustomerDto;
import com.markorusic.webstore.dto.CustomerRequestDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerDto update(CustomerRequestDto customerRequestDto);

    CustomerActionDto track(String actionType);

    Page<CustomerActionDto> findActions(Predicate predicate, Pageable pageable);
}
