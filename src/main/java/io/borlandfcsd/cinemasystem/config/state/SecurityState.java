package io.borlandfcsd.cinemasystem.config.state;

import io.borlandfcsd.cinemasystem.entity.hibernateEntity.user.User;
import io.borlandfcsd.cinemasystem.service.impl.userServices.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public final class SecurityState {
    public static final String ANONYMOUS_USER = "anonymousUser";

    public static User getAuthorizedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if (ANONYMOUS_USER.equals(authentication.getPrincipal())) {
            return new User();
        }
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        user.setEmail(customUser.getUsername());
        user.setId(customUser.getId());
        return user;
    }
}
