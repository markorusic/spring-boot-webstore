package com.markorusic.webstore.controller;

import com.markorusic.webstore.dao.CustomerDao;
import com.markorusic.webstore.domain.Customer;
import com.markorusic.webstore.dto.customer.AdminCustomerRequestDto;
import com.markorusic.webstore.dto.customer.CustomerDto;
import com.markorusic.webstore.service.AdminService;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.util.MappingUtils;
import com.markorusic.webstore.util.validation.ValidationGroup;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Admin Customer Api")
@RequestMapping(path = "/admin/customers")
public class AdminCustomerController {

    private final CustomerService customerService;

    private final AdminService adminService;

    private final MappingUtils mapper;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ApiOperation(value = "Method for getting all customers with pagination and search support")
    public Page<CustomerDto> findAll(@QuerydslPredicate(root = Customer.class, bindings = CustomerDao.class) Predicate predicate, Pageable pageable) {
        var customers = customerService.findAll(predicate, pageable);
        return mapper.mapPage(customers, CustomerDto.class, pageable);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ApiOperation(value = "Method for finding single customer with all details by id")
    public CustomerDto findById(@RequestParam Long id) {
        var customer = customerService.findById(id);
        return mapper.map(customer, CustomerDto.class);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Method for creating new customer")
    public CustomerDto save(@Validated(ValidationGroup.Save.class) @RequestBody AdminCustomerRequestDto adminCustomerRequestDto) {
        var customer = customerService.save(adminCustomerRequestDto);
        adminService.track("Created customer with id " + customer.getId());
        return mapper.map(customer, CustomerDto.class);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Method for updating existing customer")
    public CustomerDto update(@Validated(ValidationGroup.Update.class) @RequestBody AdminCustomerRequestDto adminCustomerRequestDto) {
        var customer = customerService.adminUpdate(adminCustomerRequestDto);
        adminService.track("Updated customer with id " + customer.getId());
        return mapper.map(customer, CustomerDto.class);
    }
}
