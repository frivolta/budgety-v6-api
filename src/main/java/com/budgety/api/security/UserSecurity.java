package com.budgety.api.security;

import com.budgety.api.entity.User;
import com.budgety.api.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Log4j2
@Component("userSecurity")
public class UserSecurity {

    private UserService userService;

    public UserSecurity(UserService userService) {
        this.userService = userService;
    }

    public boolean hasUserId(Authentication authentication, Long userId){
        log.info("[UserSecurity]: Checking user id: ", userId);
        String sub = authentication.getName();
        User user = userService.getUserEntity(sub);
        Long idFromEntity = user.getId();
        log.info("[UserSecurity]: Check if user is accessing own resources path: "+userId+" requesting user: "+idFromEntity);
        return idFromEntity==userId;
    }
}
