package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.Admin;
import com.markorusic.webstore.domain.AdminAction;
import com.markorusic.webstore.security.domain.AuthRequestDto;
import com.markorusic.webstore.security.domain.AuthResponseDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    AuthResponseDto login(AuthRequestDto authRequestDto);

    Admin getAuthenticatedAdmin();

    AdminAction track(String actionType);

    Page<AdminAction> findActions(Predicate predicate, Pageable pageable);
}
