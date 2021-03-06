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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AuthService authService;

    private final CustomerDao customerDao;

    private final CustomerActionDao customerActionDao;

    private final MappingUtils mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void checkEmailAvailability(String email) {
        var existingCustomer = customerDao.findByEmail(email);
        if (existingCustomer != null) {
            throw new SafeModeException(String.format("Customer with %s email already exists!", email));
        }
    }

    @Override
    public Customer findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        return customerDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Customer with identifier %s not found!", id)));
    }

    @Override
    public Customer save(AdminCustomerRequestDto adminCustomerRequestDto) {
        checkEmailAvailability(adminCustomerRequestDto.getEmail());
        var customer = Customer.builder()
                .email(adminCustomerRequestDto.getEmail())
                .firstName(adminCustomerRequestDto.getFirstName())
                .lastName(adminCustomerRequestDto.getLastName())
                .password(adminCustomerRequestDto.getPassword())
                .build();
        customerDao.save(customer);
        return customer;
    }

    @Override
    public Customer adminUpdate(AdminCustomerRequestDto adminCustomerRequestDto) {
        var customer = findById(adminCustomerRequestDto.getId());
        if (!customer.getEmail().equals(adminCustomerRequestDto.getEmail())) {
            checkEmailAvailability(adminCustomerRequestDto.getEmail());
        }
        customer = findById(adminCustomerRequestDto.getId())
                .withEmail(adminCustomerRequestDto.getEmail())
                .withFirstName(adminCustomerRequestDto.getFirstName())
                .withLastName(adminCustomerRequestDto.getLastName());

        customerDao.save(customer);
        return customer;
    }

    @Override
    public Page<CustomerAction> findCustomerActions(Predicate predicate, Pageable pageable) {
        return customerActionDao.findAll(new BooleanBuilder().and(predicate), pageable);
    }

    @Override
    public Customer update(CustomerRequestDto customerRequestDto) {
        var customer = findById(authService.getUser().getId())
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
        checkEmailAvailability(customerRegistrationDto.getEmail());
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

    @Override
    public Page<Customer> findAll(Predicate predicate, Pageable pageable) {
        return customerDao.findAll(new BooleanBuilder().and(predicate), pageable);
    }
}
