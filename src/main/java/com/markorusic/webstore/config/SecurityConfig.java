package com.markorusic.webstore.config;

import com.markorusic.webstore.security.AuthFilter;
import com.markorusic.webstore.security.domain.AuthRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@Order(1)
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthFilter authFilter;

    private final Map<AuthRole, String[]> PROTECTED_ROUTES_MAP = Map.ofEntries(
            Map.entry(AuthRole.Admin, new String[] {
                    "/admins/me",
                    "/admins/me/actions",
                    "/products/save",
                    "/products/update",
                    "/products/delete",
                    "/categories/save",
                    "/categories/update",
                    "/categories/delete",
                    "/orders/findAll",
                    "/orders/findById",
                    "/orders/ship",
                    "/file-upload"
            }),
            Map.entry(AuthRole.Customer, new String[] {
                    "/customers/update",
                    "/customers/me",
                    "/customers/me/actions",
                    "/orders/me",
                    "/orders/save",
                    "/orders/cancel",
                    "/product-reviews/save",
                    "/product-reviews/update",
                    "/product-reviews/delete"
            })
    );

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(PROTECTED_ROUTES_MAP.get(AuthRole.Customer))
                .hasAuthority(AuthRole.Customer.toString())
            .antMatchers(PROTECTED_ROUTES_MAP.get(AuthRole.Admin))
                .hasAuthority(AuthRole.Admin.toString())
            .antMatchers("/**")
                .permitAll();


    }
}