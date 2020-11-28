package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
}
