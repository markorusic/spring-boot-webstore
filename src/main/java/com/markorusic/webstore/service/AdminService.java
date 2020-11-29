package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.Admin;
import com.markorusic.webstore.security.domain.AuthRequestDto;
import com.markorusic.webstore.security.domain.AuthResponseDto;

public interface AdminService {
    AuthResponseDto login(AuthRequestDto authRequestDto);

    Admin getAuthenticatedAdmin();
}
