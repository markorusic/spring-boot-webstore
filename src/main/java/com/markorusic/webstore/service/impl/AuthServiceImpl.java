package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.CustomerDao;
import com.markorusic.webstore.domain.Customer;
import com.markorusic.webstore.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer getCustomer() {
        return customerDao.getOne(1L);
    }
}
