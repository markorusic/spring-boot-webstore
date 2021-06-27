package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.Customer;
import com.markorusic.webstore.domain.QCustomer;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface CustomerDao extends JpaRepository<Customer, Long>, QuerydslPredicateExecutor<Customer>, QuerydslBinderCustomizer<QCustomer> {
    Customer findByEmail(String email);

    @Override
    default void customize(QuerydslBindings bindings, QCustomer root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
