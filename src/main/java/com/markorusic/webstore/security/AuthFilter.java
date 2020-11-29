package com.markorusic.webstore.security;

import com.markorusic.webstore.security.domain.AuthRole;
import com.markorusic.webstore.security.domain.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class AuthFilter implements Filter {

    private final List<String> CUSTOMER_ROUTES = Arrays.asList("customers/findActions", "customers/update", "customers/me");

    private final List<String> ADMIN_ROUTES = Arrays.asList("products/save", "admins/me");

    @Value("${jwt.header}")
    private String AUTH_HEADER;

    private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private AuthService authService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        var route = request.getRequestURI();

        var isCustomerRoute = CUSTOMER_ROUTES.stream().anyMatch(route::contains);
        var isAdminRoute = ADMIN_ROUTES.stream().anyMatch(route::contains);

        if (isCustomerRoute || isAdminRoute) {
            try {
                String token = request.getHeader(AUTH_HEADER);
                authService.init(token);
                var role = authService.getUser().getRole();
                logger.info("userID" + authService.getUser().getId().toString());
                if (
                    (isAdminRoute && role != AuthRole.Admin) ||
                    (isCustomerRoute && role != AuthRole.Customer)
                ) {
                    response.setStatus(403);
                    return;
                }
            } catch (Exception e) {
                logger.info("auth init failed");
                response.setStatus(401);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
