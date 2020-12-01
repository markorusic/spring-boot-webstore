package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.Order;
import com.markorusic.webstore.domain.QOrder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Long>, QuerydslPredicateExecutor<Order>, QuerydslBinderCustomizer<QOrder> {
    List<Order> findByCustomerId(Long customerId);

    @Override
    default void customize(QuerydslBindings bindings, QOrder root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
