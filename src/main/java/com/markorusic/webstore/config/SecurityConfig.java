package com.markorusic.webstore.config;

import com.markorusic.webstore.security.AuthFilter;
import com.markorusic.webstore.security.domain.AuthRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
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
                    "/orders/changeStatus",
                    "/product-reviews/findAll",
                    "/admin/customers/findAll",
                    "/admin/customers/findById",
                    "/admin/customers/update",
                    "/admin/customers/save",
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
                    "/product-reviews/delete",
                    "/product-reviews/me"
            })
    );

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        var config = http
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and().cors().and()
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests();

        PROTECTED_ROUTES_MAP.forEach((role, paths) -> {
            config.antMatchers(paths).hasAuthority(role.toString());
        });

        config.antMatchers("/**").permitAll();
    }
}