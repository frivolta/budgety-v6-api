package com.budgety.api.security;

import com.budgety.api.payload.user.UserDto;
import com.budgety.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.Map;


public class GetUserFilter  extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String METADATA_URL = "https://dev-iv8n6772.us.auth0.com/userinfo";

    private String getUserEmailFromAuth0(HttpServletRequest request)throws Exception{
        String token = request.getHeader(AUTHORIZATION_HEADER);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest metadataRequest = HttpRequest.newBuilder()
                .uri(URI.create(METADATA_URL))
                .header("Authorization", token)
                .build();
        HttpResponse<String> response = client.send(metadataRequest, HttpResponse.BodyHandlers.ofString());
        Map metadata = new GsonJsonParser().parseMap(response.body());
        return metadata.get("email").toString();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getUserPrincipal()!=null) {
            Principal principal = request.getUserPrincipal();
            boolean userExists = userService.userExists(principal.getName());
            if (!userExists) {
                try {
                    UserDto userDto = new UserDto();
                    userDto.setSub(principal.getName());
                    userDto.setEmail(getUserEmailFromAuth0(request));
                    userService.createUser(userDto);
                } catch (Exception e) {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
            }
            else{
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        }
        filterChain.doFilter(request, response);
    }
}
