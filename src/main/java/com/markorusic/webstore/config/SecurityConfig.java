package com.markorusic.webstore.config;

import com.markorusic.webstore.security.AuthFilter;
import com.markorusic.webstore.security.domain.AuthRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Order(1)
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthFilter authFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore( authFilter, UsernamePasswordAuthenticationFilter.class);
        http
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("customers/update",
                    "/customers/me",
                    "/customers/me/actions",
                    "/categories/save",
                    "/orders/me",
                    "/orders/save",
                    "/orders/cancel",
                    "/product-reviews/save",
                    "/product-reviews/update",
                    "/product-reviews/delete",
                    "/categories/save"
            ).hasAuthority(AuthRole.Customer.toString())
            .antMatchers(
                    "/admins/me",
                    "/admins/me/actions",
                    "/products/save",
                    "/products/update",
                    "/products/delete",

                    "/categories/update",
                    "/categories/delete",
                    "/orders/findAll",
                    "/orders/findById",
                    "/orders/ship",
                    "/file-upload"
            ).hasAuthority(AuthRole.Admin.toString())
            .antMatchers("/**").permitAll();


    }
}