package com.markorusic.webstore.controller;

import com.markorusic.webstore.dto.admin.AdminDto;
import com.markorusic.webstore.security.domain.AuthRequestDto;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.markorusic.webstore.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(value = "Admin Api")
@RequestMapping(path = "/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ModelMapper mapper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "Method for authenticating admin user")
    public AuthResponseDto login(@Validated @RequestBody AuthRequestDto authRequestDto) {
        return adminService.login(authRequestDto);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ApiOperation(value = "Method for getting currently authenticated admin")
    public AdminDto login() {
        var admin = adminService.getAuthenticatedAdmin();
        return mapper.map(admin, AdminDto.class);
    }
}
