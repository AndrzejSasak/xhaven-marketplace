package com.xhaven.xhavenserver.service;

import com.xhaven.xhavenserver.config.security.CustomUserDetails;
import com.xhaven.xhavenserver.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getCurrentUser() {
        CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return principal.getCurrentUser();
    }
}
