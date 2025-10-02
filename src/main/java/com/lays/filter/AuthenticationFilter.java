package com.lays.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "Authentication")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (session == null &&
                !requestURI.contains("login") &&
                !requestURI.contains("signUp") &&
                !requestURI.endsWith(".ftl") &&
                !requestURI.equals("/")) {
            ((HttpServletResponse) response).sendRedirect("/login");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
