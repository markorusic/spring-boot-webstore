package com.markorusic.webstore.controller;

import com.markorusic.webstore.dao.CustomerActionDao;
import com.markorusic.webstore.domain.CustomerAction;
import com.markorusic.webstore.dto.customer.CustomerActionDto;
import com.markorusic.webstore.dto.customer.CustomerDto;
import com.markorusic.webstore.dto.customer.CustomerRegistrationDto;
import com.markorusic.webstore.dto.customer.CustomerRequestDto;
import com.markorusic.webstore.security.domain.AuthRequestDto;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.util.MappingUtils;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(value = "Customer Api")
@RequestMapping(path = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final MappingUtils mapper;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "Method registering new customer user")
    public CustomerDto register(@Validated @RequestBody CustomerRegistrationDto customerRegistrationDto) {
        var customer = customerService.register(customerRegistrationDto);
        return mapper.map(customer, CustomerDto.class);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "Method authenticating customer user")
    public AuthResponseDto login(@Validated @RequestBody AuthRequestDto authRequestDto) {
        return customerService.login(authRequestDto);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ApiOperation(value = "Method for getting currently authenticated customer")
    public CustomerDto login() {
        var customer = customerService.getAuthenticatedCustomer();
        return mapper.map(customer, CustomerDto.class);
    }

    @RequestMapping(value = "/me/actions", method = RequestMethod.GET)
    @ApiOperation(value = "Method for getting currently authenticated customer's actions with pagination and search support")
    public Page<CustomerActionDto> findAll(@QuerydslPredicate(root = CustomerAction.class, bindings = CustomerActionDao.class) Predicate predicate, Pageable pageable) {
        var actions = customerService.findActions(predicate, pageable);
        return mapper.mapPage(actions, CustomerActionDto.class, pageable);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Method for updating currently authenticated customer")
    public CustomerDto update(@Validated @RequestBody CustomerRequestDto customerRequestDto) {
        var customer = customerService.update(customerRequestDto);
        customerService.track("Updated profile info");
        return mapper.map(customer, CustomerDto.class);
    }
}
