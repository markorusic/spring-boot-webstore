package com.markorusic.webstore.controller;

import com.markorusic.webstore.dao.CustomerActionDao;
import com.markorusic.webstore.domain.CustomerAction;
import com.markorusic.webstore.dto.*;
import com.markorusic.webstore.service.CustomerService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Customer Api")
@RequestMapping(path = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/findActions", method = RequestMethod.GET)
    @ApiOperation(value = "Method for getting currently authenticated customer's actions with pagination and search support")
    public Page<CustomerActionDto> findAll(@QuerydslPredicate(root = CustomerAction.class, bindings = CustomerActionDao.class) Predicate predicate, Pageable pageable) {
        return customerService.findActions(predicate, pageable);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Method for updating currently authenticated customer")
    public CustomerDto update(@Validated @RequestBody CustomerRequestDto customerRequestDto) {
        return customerService.update(customerRequestDto);
    }
}