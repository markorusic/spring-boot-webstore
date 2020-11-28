package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.AdminDao;
import com.markorusic.webstore.dto.admin.AdminDto;
import com.markorusic.webstore.security.AuthService;
import com.markorusic.webstore.security.domain.AuthRequestDto;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.markorusic.webstore.security.domain.AuthRole;
import com.markorusic.webstore.security.domain.AuthUser;
import com.markorusic.webstore.service.AdminService;
import com.markorusic.webstore.util.exception.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        var admin = adminDao.findByEmail(authRequestDto.getEmail());
        if (admin == null || !passwordEncoder.matches(authRequestDto.getPassword(), admin.getPassword())) {
            throw new BadRequestException("Wrong credentials");
        }
        return authService.authorize(
            AuthUser.builder().id(admin.getId()).role(AuthRole.Admin).build(),
            mapper.map(admin, AdminDto.class)
        );
    }
}
