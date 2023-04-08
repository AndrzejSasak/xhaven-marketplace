package com.xhaven.xhavenserver.controller;

import com.xhaven.xhavenserver.dto.UserDto;
import com.xhaven.xhavenserver.model.entity.User;
import com.xhaven.xhavenserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/current")
    public UserDto getCurrentlyLoggedInUser() {
        return modelMapper.map(userService.getCurrentUser(), UserDto.class);
    }

}
