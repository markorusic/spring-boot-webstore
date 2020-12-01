package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.CustomerActionDao;
import com.markorusic.webstore.dao.CustomerDao;
import com.markorusic.webstore.domain.Customer;
import com.markorusic.webstore.domain.CustomerAction;
import com.markorusic.webstore.domain.QCustomerAction;
import com.markorusic.webstore.dto.customer.*;
import com.markorusic.webstore.security.domain.AuthRequestDto;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.markorusic.webstore.security.domain.AuthRole;
import com.markorusic.webstore.security.AuthService;
import com.markorusic.webstore.security.domain.AuthUser;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.util.MappingUtils;
import com.markorusic.webstore.util.exception.BadRequestException;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.markorusic.webstore.util.exception.SafeModeException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerActionDao customerActionDao;

    @Autowired
    private MappingUtils mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Customer findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        return customerDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Customer with identifier %s not found!", id)));
    }

    @Override
    public Customer update(CustomerRequestDto customerRequestDto) {
        var customer = findById(authService.getUser().getId())
                .withEmail(customerRequestDto.getEmail())
                .withFirstName(customerRequestDto.getFirstName())
                .withLastName(customerRequestDto.getLastName());
        customerDao.save(customer);
        return customer;
    }

    @Override
    public CustomerAction track(String actionType) {
        var customer = findById(authService.getUser().getId());
        var customerAction = CustomerAction.builder()
                .actionType(actionType)
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .build();
        customerActionDao.save(customerAction);
        return customerAction;
    }

    @Override
    public Page<CustomerAction> findActions(Predicate predicate, Pageable pageable) {
        var customer = getAuthenticatedCustomer();
        var customerExpression = QCustomerAction.customerAction.customer.id.eq(customer.getId());
        return customerActionDao.findAll(new BooleanBuilder().and(predicate).and(customerExpression), pageable);
    }

    @Override
    public Customer register(CustomerRegistrationDto customerRegistrationDto) {
        var existingCustomer = customerDao.findByEmail(customerRegistrationDto.getEmail());
        if (existingCustomer != null) {
            throw new SafeModeException(String.format("Customer with %s email already exists!", customerRegistrationDto.getEmail()));
        }
        var customer = Customer.builder()
                .email(customerRegistrationDto.getEmail())
                .firstName(customerRegistrationDto.getFirstName())
                .lastName(customerRegistrationDto.getLastName())
                .password(passwordEncoder.encode(customerRegistrationDto.getPassword()))
                .build();
        customerDao.save(customer);
        return customer;
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        var customer = customerDao.findByEmail(authRequestDto.getEmail());
        if (customer == null || !passwordEncoder.matches(authRequestDto.getPassword(), customer.getPassword())) {
            throw new BadRequestException("Wrong credentials");
        }
        return authService.authorize(
            AuthUser.builder().id(customer.getId()).role(AuthRole.Customer).build(),
            mapper.map(customer, CustomerDto.class)
        );
    }

    @Override
    public Customer getAuthenticatedCustomer() {
        return findById(authService.getUser().getId());
    }
}
