package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.domain.QProduct;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface ProductDao extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product>, QuerydslBinderCustomizer<QProduct> {

    @Override
    default void customize(QuerydslBindings bindings, QProduct root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
