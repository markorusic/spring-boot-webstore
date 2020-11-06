package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.CustomerAction;
import com.markorusic.webstore.domain.QCustomerAction;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface CustomerActionDao extends JpaRepository<CustomerAction, Long>, QuerydslPredicateExecutor<CustomerAction>, QuerydslBinderCustomizer<QCustomerAction> {

    @Override
    default void customize(QuerydslBindings bindings, QCustomerAction root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
