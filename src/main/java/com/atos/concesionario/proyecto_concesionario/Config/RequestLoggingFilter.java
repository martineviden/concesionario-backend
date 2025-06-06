package com.atos.concesionario.proyecto_concesionario.Config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        System.out.println("🔍 [Request] " + req.getMethod() + " " + req.getRequestURI());
        System.out.println("Headers:");
        Collections.list(req.getHeaderNames())
                .forEach(name -> System.out.println("  " + name + ": " + req.getHeader(name)));

        chain.doFilter(request, response);
    }
}

