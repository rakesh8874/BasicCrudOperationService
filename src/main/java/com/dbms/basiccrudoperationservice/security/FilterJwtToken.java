package com.dbms.basiccrudoperationservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


public class FilterJwtToken extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        ServletOutputStream sos = httpServletResponse.getOutputStream();
        String authHeader = httpServletRequest.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            sos.print("Invalid or Missing Token");
            sos.close();
        }else{
            String token = authHeader.substring(7);
            Claims claims = Jwts.parser().setSigningKey("user-key").parseClaimsJws(token).getBody();
            httpServletRequest.setAttribute("claims", claims);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
