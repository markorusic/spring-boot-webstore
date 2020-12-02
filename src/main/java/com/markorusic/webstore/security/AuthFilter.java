package com.markorusic.webstore.security;

import com.markorusic.webstore.security.domain.AuthRole;
import com.markorusic.webstore.security.domain.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuthFilter extends OncePerRequestFilter { // implements Filter {
    
    private final Map<AuthRole, List<String>> PROTECTED_ROUTES_MAP = Map.ofEntries(
        Map.entry(AuthRole.Admin, Arrays.asList(
            "admins/me",
            "admins/me/actions",
            "products/save",
            "products/update",
            "products/delete",
            "categories/save",
            "categories/update",
            "categories/delete",
            "orders/findAll",
            "orders/findById",
            "orders/ship",
            "file-upload"
        )),
        Map.entry(AuthRole.Customer, Arrays.asList(
            "customers/update",
            "customers/me",
            "customers/me/actions",
            "orders/me",
            "orders/save",
            "orders/cancel",
            "product-reviews/save",
            "product-reviews/update",
            "product-reviews/delete"
        ))
    );

    @Value("${jwt.header}")
    private String AUTH_HEADER;

    private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private AuthService authService;

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        var route = request.getRequestURI();
//
//        var roleRouteMatches = new HashMap<AuthRole, Boolean>();
//        PROTECTED_ROUTES_MAP.forEach((role, paths) -> {
//            roleRouteMatches.put(role, paths.stream().anyMatch(route::contains));
//        });
//
//        if (roleRouteMatches.values().stream().anyMatch(b -> b)) {
//            try {
//                String token = request.getHeader(AUTH_HEADER);
//                authService.init(token);
//                var user = authService.getUser();
//                if (!roleRouteMatches.get(user.getRole())) {
//                    response.setStatus(403);
//                    return;
//                }
//            } catch (Exception e) {
//                response.setStatus(401);
//                return;
//            }
//        }
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            if(request.getHeader(AUTH_HEADER) != null) {
                String token = request.getHeader(AUTH_HEADER);
                authService.init(token);
                var user = authService.getUser();
                var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);

//        var route = request.getRequestURI();
//
//        var roleRouteMatches = new HashMap<AuthRole, Boolean>();
//        PROTECTED_ROUTES_MAP.forEach((role, paths) -> {
//            roleRouteMatches.put(role, paths.stream().anyMatch(route::contains));
//        });
//
//        if (roleRouteMatches.values().stream().anyMatch(b -> b)) {
//            try {
//                String token = request.getHeader(AUTH_HEADER);
//                authService.init(token);
//                var user = authService.getUser();
//                if (!roleRouteMatches.get(user.getRole())) {
//                    response.setStatus(403);
//                    return;
//                }
//            } catch (Exception e) {
//                response.setStatus(401);
//                return;
//            }
////            new UsernamePasswordAuthenticationToken(new )
//        }
//        filterChain.doFilter(request, response);
    }

//    @Override
//    public void destroy() {}
}
