package com.budgety.api.security;

import com.budgety.api.payload.user.UserDto;
import com.budgety.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;


public class GetUserFilter  extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getUserPrincipal()!=null) {
            Principal principal = request.getUserPrincipal();
            boolean userExists = userService.userExists(principal.getName());
            if (!userExists) {
                UserDto userDto = new UserDto();
                userDto.setSub(principal.getName());
                userService.createUser(userDto);
            }
        }
        filterChain.doFilter(request, response);
    }
}
