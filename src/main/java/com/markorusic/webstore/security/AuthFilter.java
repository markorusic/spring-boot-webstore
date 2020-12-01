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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthFilter implements Filter {
    
    private final Map<AuthRole, List<String>> PRIVATE_ROUTE_PREFIX_MAP = Map.ofEntries(
        Map.entry(AuthRole.Admin, Arrays.asList("products/save", "admins/me")),
        Map.entry(AuthRole.Customer, Arrays.asList("customers/findActions", "customers/update", "customers/me"))
    );

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

        var roleRouteMatches = new HashMap<AuthRole, Boolean>();
        PRIVATE_ROUTE_PREFIX_MAP.forEach((role, paths) -> {
            roleRouteMatches.put(role, paths.stream().anyMatch(route::contains));
        });

        if (roleRouteMatches.values().stream().anyMatch(b -> b)) {
            try {
                String token = request.getHeader(AUTH_HEADER);
                authService.init(token);
                var role = authService.getUser().getRole();
                if (!roleRouteMatches.get(role)) {
                    response.setStatus(403);
                    return;
                }
            } catch (Exception e) {
                response.setStatus(401);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
