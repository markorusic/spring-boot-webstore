package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.AdminAction;
import com.markorusic.webstore.domain.QAdminAction;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface AdminActionDao extends JpaRepository<AdminAction, Long>, QuerydslPredicateExecutor<AdminAction>, QuerydslBinderCustomizer<QAdminAction> {

    @Override
    default void customize(QuerydslBindings bindings, QAdminAction root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
