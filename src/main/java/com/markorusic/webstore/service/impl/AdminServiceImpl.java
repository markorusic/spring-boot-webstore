package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.AdminActionDao;
import com.markorusic.webstore.dao.AdminDao;
import com.markorusic.webstore.domain.*;
import com.markorusic.webstore.dto.admin.AdminActionDto;
import com.markorusic.webstore.dto.admin.AdminDto;
import com.markorusic.webstore.dto.customer.CustomerActionDto;
import com.markorusic.webstore.security.AuthService;
import com.markorusic.webstore.security.domain.AuthRequestDto;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.markorusic.webstore.security.domain.AuthRole;
import com.markorusic.webstore.security.domain.AuthUser;
import com.markorusic.webstore.service.AdminService;
import com.markorusic.webstore.util.exception.BadRequestException;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminActionDao adminActionDao;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    private Admin findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        return adminDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Admin with identifier %s not found!", id)));
    }

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

    @Override
    public Admin getAuthenticatedAdmin() {
        var id = authService.getUser().getId();
        var admin = adminDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Admin with identifier %s not found!", id.toString())));
        return admin;
    }

    @Override
    public AdminAction track(String actionType) {
        var admin = findById(authService.getUser().getId());
        var adminAction = AdminAction.builder()
                .actionType(actionType)
                .admin(admin)
                .createdAt(LocalDateTime.now())
                .build();
        adminActionDao.save(adminAction);
        return adminAction;
    }

    @Override
    public Page<AdminAction> findActions(Predicate predicate, Pageable pageable) {
        var customer = getAuthenticatedAdmin();
        var customerExpression = QAdminAction.adminAction.admin.id.eq(customer.getId());
        return adminActionDao.findAll(new BooleanBuilder().and(predicate).and(customerExpression), pageable);
    }
}
