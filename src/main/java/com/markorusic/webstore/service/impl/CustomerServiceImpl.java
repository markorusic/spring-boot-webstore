package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.CustomerActionDao;
import com.markorusic.webstore.dao.CustomerDao;
import com.markorusic.webstore.domain.Customer;
import com.markorusic.webstore.domain.CustomerAction;
import com.markorusic.webstore.domain.QCustomerAction;
import com.markorusic.webstore.dto.CustomerActionDto;
import com.markorusic.webstore.dto.customer.CustomerDto;
import com.markorusic.webstore.dto.customer.CustomerRegistrationDto;
import com.markorusic.webstore.dto.customer.CustomerRequestDto;
import com.markorusic.webstore.service.AuthService;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.util.exception.SafeModeException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerActionDao customerActionDao;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CustomerDto update(CustomerRequestDto customerRequestDto) {
        var customer = authService.getCustomer()
                .withEmail(customerRequestDto.getEmail())
                .withFirstName(customerRequestDto.getFirstName())
                .withLastName(customerRequestDto.getLastName());
        customerDao.save(customer);
        track("Updated profile info");
        return mapper.map(customer, CustomerDto.class);
    }

    @Override
    public CustomerActionDto track(String actionType) {
        var customer = authService.getCustomer();
        var customerAction = CustomerAction.builder()
                .actionType(actionType)
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .build();
        customerActionDao.save(customerAction);
        return mapper.map(customerAction, CustomerActionDto.class);
    }

    @Override
    public Page<CustomerActionDto> findActions(Predicate predicate, Pageable pageable) {
        var customer = authService.getCustomer();
        var customerExpression = QCustomerAction.customerAction.customer.id.eq(customer.getId());
        var customerActions = customerActionDao.findAll(new BooleanBuilder().and(predicate).and(customerExpression), pageable);
        return new PageImpl<>(customerActions.stream()
                .map(customerAction -> mapper.map(customerAction, CustomerActionDto.class))
                .collect(Collectors.toList()), pageable, customerActions.getTotalElements());
    }

    @Override
    public CustomerDto register(CustomerRegistrationDto customerRegistrationDto) {
        var existingCustomer = customerDao.findByEmail(customerRegistrationDto.getEmail());
        if (existingCustomer != null) {
            throw new SafeModeException(String.format("Customer with %s email already exists!", customerRegistrationDto.getEmail()));
        }
        var customer = Customer.builder()
                .email(customerRegistrationDto.getEmail())
                .firstName(customerRegistrationDto.getFirstName())
                .lastName(customerRegistrationDto.getLastName())
                .password(customerRegistrationDto.getPassword())
                .build();
        customerDao.save(customer);
        return mapper.map(customer, CustomerDto.class);
    }
}
