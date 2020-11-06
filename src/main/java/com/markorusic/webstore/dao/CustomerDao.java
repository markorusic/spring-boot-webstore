package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
